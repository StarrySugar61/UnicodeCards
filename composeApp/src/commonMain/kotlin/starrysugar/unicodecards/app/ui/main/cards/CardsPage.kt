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

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import starrysugar.unicodecards.app.ui.common.paging.AppLazyPagingVerticalGrid
import starrysugar.unicodecards.app.ui.common.paging.collectAsLazyPagingItems

/**
 * @author StarrySugar61
 * @create 2024/6/17
 */
@Composable
fun CardsPage(
    navController: NavHostController,
    viewModel: CardsViewModel = viewModel(),
) {
    AppLazyPagingVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(3),
        lazyPagingItems = viewModel.deckPagerFlow.collectAsLazyPagingItems(),
        itemContent = { index, item ->
            Text(
                "${item.block_name}: ${item.collected} / ${item.char_count}"
            )
        }
    )
}

@Composable
private fun DeckItem() {

}