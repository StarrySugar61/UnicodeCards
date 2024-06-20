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
package starrysugar.unicodecards.appdata.models.pack

import org.koin.core.component.inject
import starrysugar.unicodecards.appdata.database.table.UnicodeDataQueries
import starrysugar.unicodecards.appdata.database.table.UserCardsQueries
import kotlin.random.Random

/**
 * @author StarrySugar61
 * @create 2024/6/19
 */
data class CommonCardPack(
    override val name: String,
    override val sampleCodePoint: Int,
) : CardPack {

    private val _unicodeDataQueries: UnicodeDataQueries by inject()

    private val _userCardsQueries: UserCardsQueries by inject()

    override fun collectCards(count: Int): List<CardPack.CardResult> {
        val results = ArrayList<CardPack.CardResult>()
        val totalCount = _unicodeDataQueries.count().executeAsOneOrNull() ?: 0
        val collected = _userCardsQueries.cardsCollected().executeAsOneOrNull() ?: 0
        // User can collect the first 96 plus types of currently owned cards
        val cardsAvailable = (collected + 96).coerceAtMost(totalCount)
        repeat(count) {
            val newCard = _unicodeDataQueries.queryDataByIndexWithUserData(
                index = Random.nextLong(cardsAvailable)
            ).executeAsOneOrNull() ?: return@repeat
            results.add(
                CardPack.CardResult(
                    codePoint = newCard.code_point.toInt(),
                    isNew = newCard.card_count == 0L,
                ),
            )
            _userCardsQueries.receivedCardFor(newCard.code_point)
        }
        return results
    }
}