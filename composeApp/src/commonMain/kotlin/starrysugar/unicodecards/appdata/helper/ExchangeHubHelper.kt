/*
 * Copyright (C) 2024 StarrySugar
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see <https://www.gnu.org/licenses/>.
 */
package starrysugar.unicodecards.appdata.helper

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.firstOrNull
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import starrysugar.unicodecards.appdata.configs.AppConfigs
import starrysugar.unicodecards.appdata.database.table.FakeExchangeRequestsQueries
import starrysugar.unicodecards.appdata.database.table.FakeUsersQueries
import starrysugar.unicodecards.appdata.database.table.UnicodeDataQueries
import starrysugar.unicodecards.appdata.database.table.UserCardsQueries
import starrysugar.unicodecards.appdata.datastore.AppDataStoreKeys
import starrysugar.unicodecards.arch.utils.TimeUtils
import kotlin.random.Random
import kotlin.random.nextInt
import kotlin.random.nextLong

/**
 * Exchange hub helper!
 *
 * @author StarrySugar61
 * @create 2024/6/28
 */
object ExchangeHubHelper : KoinComponent {

    private val _userCardsQueries: UserCardsQueries by inject()

    private val _unicodeDataQueries: UnicodeDataQueries by inject()

    private val _fakeUsersQueries: FakeUsersQueries by inject()

    private val _fakeExchangeRequestsQueries: FakeExchangeRequestsQueries by inject()

    private val dataStore: DataStore<Preferences> by inject()

    /**
     * Total different cards user obtained!
     */
    private val _totalCards by lazy {
        _unicodeDataQueries.count().executeAsOne()
    }

    /**
     * Total fake users in the username list!
     */
    private val _totalFakeUsers by lazy {
        _fakeUsersQueries.count().executeAsOne()
    }

    /**
     * Update exchange hub!
     */
    suspend fun onUpdate() {
        val now = TimeUtils.currentTimeMillis()
        // Clear all expired requests!
        _fakeExchangeRequestsQueries.clearAllExpired(now)

        val prefs = dataStore.data.firstOrNull()
        var nextRequest = prefs?.get(AppDataStoreKeys.KET_MARKET_EH_NEXT_FAKE_REQUEST)
            ?: (now - AppConfigs.EXCHANGE_HUB_EXPIRED_TIME)
        // Loop creating new requests!
        while (nextRequest < now) {
            val sentTime = nextRequest
            val expiredTime = sentTime + if (Random.nextBoolean()) {
                AppConfigs.EXCHANGE_HUB_EXPIRED_TIME
            } else {
                Random.nextLong(AppConfigs.EXCHANGE_HUB_EXPIRED_TIME)
            }
            // Do not insert already expired requests!
            if (sentTime + expiredTime > now) {
                generateFakeRequest(
                    sentTime = sentTime,
                    expiredTime = expiredTime,
                )
            }
            nextRequest += Random.nextLong(
                range = AppConfigs.EXCHANGE_HUB_NEW_REQUEST_LEAST_TIME..
                        AppConfigs.EXCHANGE_HUB_NEW_REQUEST_MOST_TIME,
            )
        }
        // Save next request time!
        dataStore.edit {
            it[AppDataStoreKeys.KET_MARKET_EH_NEXT_FAKE_REQUEST] = nextRequest
        }
    }

    /**
     * Generate a single fake request!
     */
    private fun generateFakeRequest(
        sentTime: Long,
        expiredTime: Long,
    ) {
        // Pick a random username from fake users!
        val username = _fakeUsersQueries
            .queryUserByIndex(index = Random.nextLong(_totalFakeUsers))
            .executeAsOne()
        val cardWantedIndex = Random.nextLong(
            until = (_userCardsQueries.cardsCollected().executeAsOneOrNull() ?: 0) + 0x100,
        )
        val cardWanted = _unicodeDataQueries
            .queryCodePointByIndex(cardWantedIndex)
            .executeAsOne()
        val cardsAvailable = ((cardWantedIndex - 0x200)..(cardWantedIndex + 0x200))
            .asSequence()
            .filter {
                it in 0..<_totalCards
            }
            .filter {
                it != cardWantedIndex
            }
            .shuffled()
            .take(
                Random.nextInt(1..5)
            )
            .map {
                _unicodeDataQueries.queryCodePointByIndex(it).executeAsOne()
            }
            .toList()
        if (cardsAvailable.isEmpty()) {
            return
        }
        _fakeExchangeRequestsQueries.insert(
            username = username,
            card_wanted = cardWanted,
            card_available_1 = cardsAvailable.getOrElse(0) { -1 },
            card_available_2 = cardsAvailable.getOrElse(1) { -1 },
            card_available_3 = cardsAvailable.getOrElse(2) { -1 },
            card_available_4 = cardsAvailable.getOrElse(3) { -1 },
            card_available_5 = cardsAvailable.getOrElse(4) { -1 },
            sent_time = sentTime,
            expired_time = expiredTime,
        )
    }

}