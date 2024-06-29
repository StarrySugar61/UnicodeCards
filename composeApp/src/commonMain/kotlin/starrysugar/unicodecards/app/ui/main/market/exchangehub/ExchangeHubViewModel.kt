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
package starrysugar.unicodecards.app.ui.main.market.exchangehub

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import app.cash.sqldelight.paging3.QueryPagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.component.inject
import starrysugar.unicodecards.app.ui.base.BaseViewModel
import starrysugar.unicodecards.appdata.database.table.FakeExchangeRequestsQueries
import starrysugar.unicodecards.appdata.database.table.UnicodeDataQueries

/**
 * @author StarrySugar61
 * @create 2024/6/28
 */
class ExchangeHubViewModel : BaseViewModel() {

    private val _fakeExchangeRequestsQueries: FakeExchangeRequestsQueries by inject()

    private val _unicodeDataQueries: UnicodeDataQueries by inject()

    val requestFlow = Pager(
        config = PagingConfig(
            pageSize = 10,
        )
    ) {
        QueryPagingSource(
            countQuery = _fakeExchangeRequestsQueries.count(),
            transacter = _fakeExchangeRequestsQueries,
            context = Dispatchers.IO,
            queryProvider = _fakeExchangeRequestsQueries::queryDataPaging,
        )
    }.flow.cachedIn(viewModelScope)

    fun getCodePointData(
        codePoint: Long
    ) = _unicodeDataQueries
        .queryDataByCodeWithUserData(codePoint)
        .executeAsOne()

}