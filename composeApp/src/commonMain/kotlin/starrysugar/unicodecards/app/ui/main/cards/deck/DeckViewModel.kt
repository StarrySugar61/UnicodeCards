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
package starrysugar.unicodecards.app.ui.main.cards.deck

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import app.cash.sqldelight.paging3.QueryPagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.component.inject
import starrysugar.unicodecards.app.ui.base.BaseViewModel
import starrysugar.unicodecards.appdata.database.table.QueryDataPagingByBlockWithUserData
import starrysugar.unicodecards.appdata.database.table.Uc_unicode_blocks
import starrysugar.unicodecards.appdata.database.table.UnicodeBlocksQueries
import starrysugar.unicodecards.appdata.database.table.UnicodeDataQueries
import starrysugar.unicodecards.appdata.datastore.AppDataStoreKeys

/**
 * @author StarrySugar61
 * @create 2024/6/19
 */
class DeckViewModel(
    val startCodePoint: Int,
) : BaseViewModel() {

    private val _unicodeBlocksQueries: UnicodeBlocksQueries by inject()

    private val _unicodeDataQueries: UnicodeDataQueries by inject()

    private val _dataStore: DataStore<Preferences> by inject()

    val isPlatformFontFlow = _dataStore.data
        .map { it[AppDataStoreKeys.KEY_SETTINGS_APPEARANCE_SYSTEM_FONT] ?: false }

    val isSerifFlow = _dataStore.data
        .map { it[AppDataStoreKeys.KEY_SETTINGS_APPEARANCE_SERIF] ?: false }

    var block by mutableStateOf<Uc_unicode_blocks?>(null)
        private set

    var cardsPagingFlow: Flow<PagingData<QueryDataPagingByBlockWithUserData>>? = null

    init {
        _unicodeBlocksQueries
            .queryBlockForStartCodePoint(startCodePoint.toLong())
            .executeAsOneOrNull()
            ?.let {
                block = it
                cardsPagingFlow = Pager(
                    config = PagingConfig(
                        pageSize = 30,
                    )
                ) {
                    QueryPagingSource(
                        countQuery = _unicodeBlocksQueries.queryBlockCharCountForStartCodePoint(
                            it.code_point_start
                        ),
                        transacter = _unicodeDataQueries,
                        context = Dispatchers.IO,
                        queryProvider = { limit, offset ->
                            _unicodeDataQueries.queryDataPagingByBlockWithUserData(
                                it.code_point_start,
                                it.code_point_end,
                                limit,
                                offset,
                            )
                        },
                    )
                }.flow.cachedIn(viewModelScope)
            }
    }

    object StartCodePointKey : CreationExtras.Key<Int>
}