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
import androidx.lifecycle.viewmodel.CreationExtras
import org.koin.core.component.inject
import starrysugar.unicodecards.app.ui.base.BaseViewModel
import starrysugar.unicodecards.appdata.database.table.QueryDataByCodeWithUserData
import starrysugar.unicodecards.appdata.database.table.UnicodeDataQueries

/**
 * @author StarrySugar61
 * @create 2024/6/19
 */
class CodePointViewModel(
    val codePoint: Int,
) : BaseViewModel() {

    private val _unicodeDataQueries: UnicodeDataQueries by inject()

    var charData by mutableStateOf<QueryDataByCodeWithUserData?>(null)
        private set

    init {
        _unicodeDataQueries.queryDataByCodeWithUserData(codePoint.toLong())
            .executeAsOneOrNull()
            ?.let {
                charData = it
            }
    }

    object CodePointKey : CreationExtras.Key<Int>
}