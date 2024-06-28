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

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import starrysugar.unicodecards.app.ui.base.BaseViewModel
import starrysugar.unicodecards.appdata.common.AppInitializer
import starrysugar.unicodecards.appdata.database.Database
import starrysugar.unicodecards.appdata.helper.ExchangeHubHelper

/**
 * @author StarrySugar61
 * @create 2024/6/15
 */
class MainViewModel : BaseViewModel() {

    private val database: Database by inject()

    var selectedTabIndex by mutableStateOf(0)

    var currentStep by mutableStateOf(0)
        private set
    var totalSteps by mutableStateOf(0)
        private set
    var progress by mutableStateOf(1)
        private set
    var maxProgress by mutableStateOf(1)
        private set

    init {
        viewModelScope.launch(Dispatchers.IO) {
            // Test
            database.userCardsQueries.setCardFontFor(0, emptySet())
            // Initialize unicode data
            AppInitializer.importAppDataTo(
                database,
                onProgress = { i1, i2, i3, i4 ->
                    currentStep = i1
                    totalSteps = i2
                    progress = i3
                    maxProgress = i4
                }
            )
            delay(1000)
            // Looped job!
            loopedJob()
        }
    }

    private suspend fun loopedJob() {
        // Updates!
        ExchangeHubHelper.onUpdate()

        // Delay for next job!
        delay(60_000)
        loopedJob()
    }

}