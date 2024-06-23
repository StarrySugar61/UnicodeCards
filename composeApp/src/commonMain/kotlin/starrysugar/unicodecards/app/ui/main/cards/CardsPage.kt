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

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import org.jetbrains.compose.resources.stringResource
import starrysugar.unicodecards.Res
import starrysugar.unicodecards.app.nav.Screen
import starrysugar.unicodecards.app.ui.common.game.UnicodeCardDeck
import starrysugar.unicodecards.app.ui.common.paging.AppLazyPagingVerticalGrid
import starrysugar.unicodecards.app.ui.common.paging.collectAsLazyPagingItems
import starrysugar.unicodecards.appdata.database.table.QueryBlockPagingWithCollected
import starrysugar.unicodecards.cards_hint_unlock
import starrysugar.unicodecards.confirm
import starrysugar.unicodecards.info

/**
 * @author StarrySugar61
 * @create 2024/6/17
 */
@Composable
fun CardsPage(
    navController: NavHostController,
    viewModel: CardsViewModel = viewModel(),
) {
    var isShowingDeckNotUnlockedDialog by remember { mutableStateOf(false) }

    // Dialogs！
    if (isShowingDeckNotUnlockedDialog) {
        DeckNotUnlockedDialog(
            onConfirmed = {
                isShowingDeckNotUnlockedDialog = false
            }
        )
    }

    AppLazyPagingVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.FixedSize(
            size = 180.dp,
        ),
        lazyPagingItems = viewModel.deckPagerFlow.collectAsLazyPagingItems(),
        contentPadding = PaddingValues(all = 4.dp),
        horizontalArrangement = Arrangement.Center,
        itemContent = { _, item ->
            DeckItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        if (item.collected == 0L) {
                            isShowingDeckNotUnlockedDialog = true
                        } else {
                            navController.navigate(
                                route = Screen.MainDeck.buildRoute(
                                    startCodePoint = item.code_point_start.toInt(),
                                ),
                            ) {
                                launchSingleTop = true
                            }
                        }
                    },
                item = item,
            )
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun DeckItem(
    modifier: Modifier,
    item: QueryBlockPagingWithCollected,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Collect at least one card to unlock deck!
        val isDeckUnlocked = item.collected > 0
        // Card deck!
        UnicodeCardDeck(
            modifier = Modifier
                .scale(0.7F),
            codePoint = if (isDeckUnlocked) {
                item.example_char.toInt()
            } else {
                -1
            },
            category = item.category,
            valueCover = item.cover,
        )
        // Collecting progress!
        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 8.dp,
                    end = 8.dp,
                    bottom = 4.dp,
                ),
            progress = {
                item.collected * 1F / item.char_count
            }
        )
        // Block name!
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 4.dp,
                )
                .basicMarquee(
                    iterations = Int.MAX_VALUE,
                ),
            text = if (isDeckUnlocked) {
                item.block_name
            } else {
                "???"
            },
            maxLines = 1,
            textAlign = TextAlign.Center,
        )
        // Cards collected
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = if (isDeckUnlocked) {
                "${item.collected} / ${item.char_count}"
            } else {
                ""
            },
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun DeckNotUnlockedDialog(
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
                    resource = Res.string.cards_hint_unlock,
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