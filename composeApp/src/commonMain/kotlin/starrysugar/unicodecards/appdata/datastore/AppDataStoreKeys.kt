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
package starrysugar.unicodecards.appdata.datastore

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey

/**
 * @author StarrySugar61
 * @create 2024/6/17
 */
object AppDataStoreKeys {
    val KEY_DATA_SCRIPT_VERSION = intPreferencesKey("KEY_DATA_SCRIPT_VERSION")
    val KEY_DATA_CHAR_VERSION = intPreferencesKey("KEY_DATA_CHAR_VERSION")
    val KEY_DATA_BLOCK_VERSION = intPreferencesKey("KEY_DATA_BLOCK_VERSION")

    val KEY_DATA_USERNAME_VERSION = intPreferencesKey("KEY_DATA_BLOCK_USERNAME")

    val KEY_DATA_TOTAL_CARDS = intPreferencesKey("KEY_DATA_TOTAL_CARDS")

    val KEY_USER_CC = longPreferencesKey("KEY_USER_CC")
    val KEY_USER_BG = longPreferencesKey("KEY_USER_BG")
    val KEY_USER_RS = longPreferencesKey("KEY_USER_RS")
    val KEY_USER_PS = longPreferencesKey("KEY_USER_PS")

    val KEY_MARKET_WELCOME_PACK_COLLECTED =
        booleanPreferencesKey("KEY_MARKET_WELCOME_PACK_COLLECTED")
    val KEY_MARKET_FREE_PACK_FULL_TIME = longPreferencesKey("KEY_MARKET_FREE_PACK_FULL_TIME")

    val KET_MARKET_EH_NEXT_FAKE_REQUEST = longPreferencesKey("KET_MARKET_EH_NEXT_FAKE_REQUEST")
    val KET_MARKET_EH_NEXT_USER_REQUEST_FULL =
        longPreferencesKey("KET_MARKET_EH_NEXT_USER_REQUEST_FULL")

    val KEY_SETTINGS_APPEARANCE_SYSTEM_FONT =
        booleanPreferencesKey("KEY_SETTINGS_APPEARANCE_SYSTEM_FONT")
    val KEY_SETTINGS_APPEARANCE_SERIF = booleanPreferencesKey("KEY_SETTINGS_APPEARANCE_SERIF")

    val KEY_STATISTIC_CC_OBTAINED = longPreferencesKey("KEY_STATISTIC_CC_OBTAINED")
    val KEY_STATISTIC_BG_OBTAINED = longPreferencesKey("KEY_STATISTIC_BG_OBTAINED")
    val KEY_STATISTIC_RS_OBTAINED = longPreferencesKey("KEY_STATISTIC_RS_OBTAINED")
    val KEY_STATISTIC_PS_OBTAINED = longPreferencesKey("KEY_STATISTIC_PS_OBTAINED")
    val KEY_STATISTIC_CARDS_OBTAINED = longPreferencesKey("KEY_STATISTIC_CARDS_OBTAINED")
    val KEY_STATISTIC_CARDS_OBTAINED_FROM_PACK =
        longPreferencesKey("KEY_STATISTIC_CARDS_OBTAINED_FROM_PACK")
    val KEY_STATISTIC_FREE_PACKS_OPENED = intPreferencesKey("KEY_STATISTIC_FREE_PACKS_OPENED")
    val KEY_STATISTIC_PACKS_OPENED = intPreferencesKey("KEY_STATISTIC_PACKS_OPENED")
}