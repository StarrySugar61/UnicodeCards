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
package starrysugar.unicodecards.app.ui.main

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import starrysugar.unicodecards.app.ui.base.BaseViewModel
import starrysugar.unicodecards.appdata.database.Database
import starrysugar.unicodecards.appdata.unicode.UnicodeInitializer

/**
 * @author StarrySugar61
 * @create 2024/6/15
 */
class MainViewModel : BaseViewModel() {

    private val database: Database by inject()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            // Test
            database.userCardsQueries.setCardFontFor(0, emptySet())
            // Initialize unicode data
            UnicodeInitializer.importUnicodeDataTo(database)
        }
    }

}