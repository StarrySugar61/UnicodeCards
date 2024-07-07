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
package starrysugar.unicodecards.app.ui.main.cards.deck.code

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.flow.map
import org.koin.core.component.inject
import starrysugar.unicodecards.app.ui.base.BaseViewModel
import starrysugar.unicodecards.appdata.database.table.QueryDataByCodeWithUserData
import starrysugar.unicodecards.appdata.database.table.Uc_unicode_iso15924
import starrysugar.unicodecards.appdata.database.table.UnicodeBlocksQueries
import starrysugar.unicodecards.appdata.database.table.UnicodeDataQueries
import starrysugar.unicodecards.appdata.database.table.UnicodeIso15924Queries
import starrysugar.unicodecards.appdata.datastore.AppDataStoreKeys

/**
 * @author StarrySugar61
 * @create 2024/6/19
 */
class CodePointViewModel(
    val codePoint: Int,
) : BaseViewModel() {

    private val _unicodeBlockQueries: UnicodeBlocksQueries by inject()

    private val _unicodeDataQueries: UnicodeDataQueries by inject()

    private val _unicodeIso15924Queries: UnicodeIso15924Queries by inject()

    private val _dataStore: DataStore<Preferences> by inject()

    val isPlatformFontFlow = _dataStore.data
        .map { it[AppDataStoreKeys.KEY_SETTINGS_APPEARANCE_SYSTEM_FONT] ?: false }

    val isSerifFlow = _dataStore.data
        .map { it[AppDataStoreKeys.KEY_SETTINGS_APPEARANCE_SERIF] ?: false }

    var charData by mutableStateOf<QueryDataByCodeWithUserData?>(null)
        private set

    var blockName by mutableStateOf("")
        private set

    var scriptData by mutableStateOf<Uc_unicode_iso15924?>(null)
        private set

    init {
        _unicodeDataQueries.queryDataByCodeWithUserData(codePoint.toLong())
            .executeAsOneOrNull()
            ?.let {
                charData = it
            }
        _unicodeBlockQueries.queryBlockForCodePoint(codePoint.toLong())
            .executeAsOneOrNull()
            ?.let {
                blockName = it.block_name
            }
        _unicodeIso15924Queries.queryScriptForCodePoint(codePoint.toLong())
            .executeAsOneOrNull()
            ?.let {
                scriptData = it
            }
    }

    object CodePointKey : CreationExtras.Key<Int>
}