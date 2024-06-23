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

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import org.jetbrains.compose.resources.stringResource
import starrysugar.unicodecards.Res
import starrysugar.unicodecards.app.ui.base.AppNavBackTopBar
import starrysugar.unicodecards.app.ui.base.AppScaffold
import starrysugar.unicodecards.app.ui.base.appViewModelFactory
import starrysugar.unicodecards.app.ui.common.game.UnicodeCard
import starrysugar.unicodecards.app.ui.common.game.UnicodeCardPlaceholder
import starrysugar.unicodecards.appdata.database.table.QueryDataByCodeWithUserData
import starrysugar.unicodecards.arch.utils.UnicodeUtils
import starrysugar.unicodecards.cards_details

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
        topBar = {
            AppNavBackTopBar(
                title = {
                    Text(
                        text = stringResource(
                            resource = Res.string.cards_details,
                        ),
                    )
                },
                onBack = {
                    navController.navigateUp()
                },
            )
        }
    ) { paddingValues ->
        val charData = viewModel.charData ?: return@AppScaffold
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues),
            columns = GridCells.Adaptive(
                minSize = 300.dp,
            ),
            horizontalArrangement = Arrangement.Center,
        ) {
            item {
                CodePointUnicodeCardItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .requiredHeight(220.dp),
                    charData = charData,
                )
            }
            item {
                CodePointInfoItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .requiredHeight(220.dp),
                    charData = charData,
                )
            }
        }
    }
}

@Composable
private fun CodePointUnicodeCardItem(
    modifier: Modifier = Modifier,
    charData: QueryDataByCodeWithUserData,
) {
    Box(
        modifier = modifier,
    ) {
        if (charData.card_count == 0L) {
            UnicodeCardPlaceholder(
                modifier = Modifier
                    .alpha(0.4F)
                    .align(Alignment.Center),
                codePoint = charData.code_point.toInt(),
            )
        } else {
            UnicodeCard(
                modifier = Modifier
                    .align(Alignment.Center),
                codePoint = charData.code_point.toInt(),
                category = charData.category,
                valueCover = charData.cover,
                count = charData.card_count.toInt(),
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CodePointInfoItem(
    modifier: Modifier = Modifier,
    charData: QueryDataByCodeWithUserData,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .requiredHeight(48.dp)
                .padding(
                    horizontal = 8.dp,
                    vertical = 4.dp,
                )
                .basicMarquee(
                    iterations = Int.MAX_VALUE,
                ),
            text = charData.name,
            fontSize = 24.sp,
            maxLines = 1,
            textAlign = TextAlign.Center,
        )
        Row {
            OutlinedCard(
                modifier = Modifier
                    .padding(all = 4.dp)
                    .requiredHeight(56.dp)
                    .weight(1F),
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "UTF-8",
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp,
                )
                HorizontalDivider()
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp),
                    text = UnicodeUtils
                        .charToUtf8(charData.code_point.toInt())
                        .joinToString(
                            separator = " "
                        ) {
                            it.toString(radix = 16)
                                .padStart(length = 2, padChar = '0')
                                .uppercase()
                        },
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                )
            }
            OutlinedCard(
                modifier = Modifier
                    .padding(all = 4.dp)
                    .requiredHeight(56.dp)
                    .weight(1F),
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "UTF-16",
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp,
                )
                HorizontalDivider()
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp),
                    text = UnicodeUtils
                        .charToUtf16(charData.code_point.toInt())
                        .joinToString(
                            separator = " "
                        ) {
                            it.toString(radix = 16)
                                .padStart(length = 4, padChar = '0')
                                .uppercase()
                        },
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                )
            }
            OutlinedCard(
                modifier = Modifier
                    .padding(all = 4.dp)
                    .requiredHeight(56.dp)
                    .weight(1F),
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "UTF-32",
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp,
                )
                HorizontalDivider()
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp),
                    text = charData.code_point.toInt()
                        .toString(radix = 16)
                        .padStart(length = 8, padChar = '0')
                        .uppercase(),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                )
            }
        }
        Row {
            OutlinedCard(
                modifier = Modifier
                    .padding(all = 4.dp)
                    .requiredHeight(56.dp)
                    .weight(1F),
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "Category",
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp,
                )
                HorizontalDivider()
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp),
                    text = charData.category.toString(),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                )
            }
            OutlinedCard(
                modifier = Modifier
                    .padding(all = 4.dp)
                    .requiredHeight(56.dp)
                    .weight(1F),
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "BidiClass",
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp,
                )
                HorizontalDivider()
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp),
                    text = charData.bidi_class.toString(),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                )
            }
            OutlinedCard(
                modifier = Modifier
                    .padding(all = 4.dp)
                    .requiredHeight(56.dp)
                    .weight(1F),
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "Combining",
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp,
                )
                HorizontalDivider()
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp),
                    text = charData.combining.toString(),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                )
            }
        }
    }
}