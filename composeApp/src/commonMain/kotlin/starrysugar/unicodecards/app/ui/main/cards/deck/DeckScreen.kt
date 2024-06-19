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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import starrysugar.unicodecards.app.ui.base.AppScaffold
import starrysugar.unicodecards.app.ui.base.appViewModelFactory
import starrysugar.unicodecards.app.ui.common.game.UnicodeCard
import starrysugar.unicodecards.app.ui.common.game.UnicodeCardPlaceholder
import starrysugar.unicodecards.app.ui.common.paging.AppLazyPagingVerticalGrid
import starrysugar.unicodecards.app.ui.common.paging.collectAsLazyPagingItems

/**
 * Overview page for cards in a deck!
 *
 * @author StarrySugar61
 * @create 2024/6/19
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeckScreen(
    startCodePoint: Int,
    navController: NavHostController,
    viewModel: DeckViewModel = viewModel(
        factory = appViewModelFactory,
        extras = MutableCreationExtras().apply {
            this[DeckViewModel.StartCodePointKey] = startCodePoint
        },
    ),
) {
    AppScaffold(
        modifier = Modifier.fillMaxSize(),
        viewModel = viewModel,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = viewModel.block?.block_name ?: "",
                    )
                },
            )
        },
    ) { paddingValues ->
        val cardsPagingFlow = viewModel.cardsPagingFlow ?: return@AppScaffold
        AppLazyPagingVerticalGrid(
            modifier = Modifier.padding(paddingValues = paddingValues),
            columns = GridCells.FixedSize(
                size = 98.dp,
            ),
            lazyPagingItems = cardsPagingFlow.collectAsLazyPagingItems(),
            horizontalArrangement = Arrangement.Center,
            itemContent = { _, item ->
                if (item.card_count == 0L) {
                    UnicodeCardPlaceholder(
                        modifier = Modifier
                            .padding(all = 4.dp)
                            .alpha(0.4F),
                        scale = 0.6F,
                        codePoint = item.code_point.toInt(),
                    )
                } else {
                    UnicodeCard(
                        modifier = Modifier
                            .padding(all = 4.dp),
                        scale = 0.6F,
                        codePoint = item.code_point.toInt(),
                        category = item.category,
                    )
                }
            }
        )
    }
}