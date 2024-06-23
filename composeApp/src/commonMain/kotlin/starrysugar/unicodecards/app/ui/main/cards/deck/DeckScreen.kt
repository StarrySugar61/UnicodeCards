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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import org.jetbrains.compose.resources.stringResource
import starrysugar.unicodecards.Res
import starrysugar.unicodecards.app.nav.Screen
import starrysugar.unicodecards.app.ui.base.AppNavBackTopBar
import starrysugar.unicodecards.app.ui.base.AppScaffold
import starrysugar.unicodecards.app.ui.base.appViewModelFactory
import starrysugar.unicodecards.app.ui.common.game.UnicodeCard
import starrysugar.unicodecards.app.ui.common.game.UnicodeCardPlaceholder
import starrysugar.unicodecards.app.ui.common.paging.AppLazyPagingVerticalGrid
import starrysugar.unicodecards.app.ui.common.paging.collectAsLazyPagingItems
import starrysugar.unicodecards.cards_hint_not_obtained
import starrysugar.unicodecards.confirm
import starrysugar.unicodecards.info

/**
 * Overview page for cards in a deck!
 *
 * @author StarrySugar61
 * @create 2024/6/19
 */
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
    var isShowingCardNotUnlockedDialog by remember { mutableStateOf(false) }

    // Dialogs！
    if (isShowingCardNotUnlockedDialog) {
        CardNotUnlockedDialog(
            onConfirmed = {
                isShowingCardNotUnlockedDialog = false
            }
        )
    }

    AppScaffold(
        modifier = Modifier.fillMaxSize(),
        viewModel = viewModel,
        topBar = {
            AppNavBackTopBar(
                title = {
                    Text(
                        text = viewModel.block?.block_name ?: "",
                    )
                },
                onBack = {
                    navController.navigateUp()
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
                            .alpha(0.4F)
                            .clickable {
                                isShowingCardNotUnlockedDialog = true
                            },
                        scale = 0.6F,
                        codePoint = item.code_point.toInt(),
                    )
                } else {
                    UnicodeCard(
                        modifier = Modifier
                            .padding(all = 4.dp)
                            .clickable {
                                navController.navigate(
                                    route = Screen.MainDeckCodePoint
                                        .buildRoute(item.code_point.toInt())
                                )
                            },
                        scale = 0.6F,
                        codePoint = item.code_point.toInt(),
                        category = item.category,
                        valueCover = item.cover,
                        count = item.card_count.toInt(),
                    )
                }
            }
        )
    }
}


@Composable
private fun CardNotUnlockedDialog(
    onConfirmed: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onConfirmed,
        title = {
            Text(
                text = stringResource(
                    resource = Res.string.info,
                ),
            )
        },
        text = {
            Text(
                text = stringResource(
                    resource = Res.string.cards_hint_not_obtained,
                ),
            )
        },
        confirmButton = {
            TextButton(
                onClick = onConfirmed,
            ) {
                Text(
                    text = stringResource(
                        resource = Res.string.confirm,
                    ),
                )
            }
        }
    )
}