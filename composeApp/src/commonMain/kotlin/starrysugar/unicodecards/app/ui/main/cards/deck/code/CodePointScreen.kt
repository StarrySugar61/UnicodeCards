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

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import starrysugar.unicodecards.app.ui.base.AppScaffold
import starrysugar.unicodecards.app.ui.base.appViewModelFactory

/**
 * Card details!
 *
 * @author StarrySugar61
 * @create 2024/6/19
 */
@Composable
fun CodePointScreen(
    codePoint: Int,
    navController: NavHostController,
    viewModel: CodePointViewModel = viewModel(
        factory = appViewModelFactory,
        extras = MutableCreationExtras().apply {
            this[CodePointViewModel.CodePointKey] = codePoint
        },
    ),
) {
    AppScaffold(
        modifier = Modifier.fillMaxSize(),
        viewModel = viewModel,
    ) {
    }
}