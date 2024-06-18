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
package starrysugar.unicodecards.app.ui.main.cards

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import app.cash.sqldelight.paging3.QueryPagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.component.inject
import starrysugar.unicodecards.app.ui.base.BaseViewModel
import starrysugar.unicodecards.appdata.database.table.UnicodeBlocksQueries

/**
 * @author StarrySugar61
 * @create 2024/6/17
 */
class CardsViewModel : BaseViewModel() {

    private val _unicodeBlocksQueries: UnicodeBlocksQueries by inject()

    val deckPagerFlow = Pager(
        config = PagingConfig(
            pageSize = 30,
        )
    ) {
        QueryPagingSource(
            countQuery = _unicodeBlocksQueries.count(),
            transacter = _unicodeBlocksQueries,
            context = Dispatchers.IO,
            queryProvider = _unicodeBlocksQueries::queryBlockPagingWithCollected,
        )
    }.flow.cachedIn(viewModelScope)

}