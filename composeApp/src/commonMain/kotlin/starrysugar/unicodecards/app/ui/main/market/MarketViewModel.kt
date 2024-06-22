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
package starrysugar.unicodecards.app.ui.main.market

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import starrysugar.unicodecards.app.ui.base.BaseViewModel
import starrysugar.unicodecards.appdata.configs.AppConfigs
import starrysugar.unicodecards.appdata.datastore.AppDataStoreKeys
import starrysugar.unicodecards.arch.utils.TimeUtils

/**
 * @author StarrySugar61
 * @create 2024/6/17
 */
class MarketViewModel : BaseViewModel() {

    private val dataStore: DataStore<Preferences> by inject()

    var isWelcomePackClaimed by mutableStateOf(true)

    var freePackFullTime by mutableStateOf(0L)

    init {
        isLoading = true
        viewModelScope.launch {
            val prefs = dataStore.data.firstOrNull() ?: run {
                isLoading = false
                return@launch
            }
            // Welcome pack
            isWelcomePackClaimed =
                prefs[AppDataStoreKeys.KEY_MARKET_WELCOME_PACK_COLLECTED] ?: false
            // Free pack
            freePackFullTime =
                prefs[AppDataStoreKeys.KEY_MARKET_FREE_PACK_FULL_TIME] ?: 0L
            if (freePackFullTime == 0L) {
                val newFreePackFullTime = TimeUtils.currentTimeMillis() +
                        (AppConfigs.FREE_PACK_MAXIMUM_STORAGE -
                                AppConfigs.FREE_PACK_INITIAL_STORAGE) *
                        AppConfigs.FREE_PACK_REFILL_TIME
                dataStore.edit {
                    it[AppDataStoreKeys.KEY_MARKET_FREE_PACK_FULL_TIME] = newFreePackFullTime
                }
                freePackFullTime = newFreePackFullTime
            }
            isLoading = false
        }
    }

    fun claimWelcomePack(
        onClaimed: () -> Unit,
    ) {
        isLoading = true
        viewModelScope.launch {
            dataStore.edit {
                it[AppDataStoreKeys.KEY_MARKET_WELCOME_PACK_COLLECTED] = true
                isWelcomePackClaimed = true
            }
            isLoading = false
            onClaimed()
        }
    }

    fun claimFreePack(
        onClaimed: () -> Unit,
    ) {
        isLoading = true
        viewModelScope.launch {
            val newFreePackFullTime =
                freePackFullTime.coerceAtLeast(TimeUtils.currentTimeMillis()) +
                        AppConfigs.FREE_PACK_REFILL_TIME
            dataStore.edit {
                it[AppDataStoreKeys.KEY_MARKET_FREE_PACK_FULL_TIME] = newFreePackFullTime
                freePackFullTime = newFreePackFullTime
            }
            isLoading = false
            onClaimed()
        }
    }

}