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
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
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
import starrysugar.unicodecards.appdata.database.table.QueryDataByCodeWithUserData
import starrysugar.unicodecards.appdata.database.table.Uc_unicode_iso15924
import starrysugar.unicodecards.arch.utils.UnicodeUtils
import starrysugar.unicodecards.cards_details
import starrysugar.unicodecards.unicode_bidirectional
import starrysugar.unicodecards.unicode_block
import starrysugar.unicodecards.unicode_category
import starrysugar.unicodecards.unicode_category_format
import starrysugar.unicodecards.unicode_combining
import starrysugar.unicodecards.unicode_plane
import starrysugar.unicodecards.unicode_script

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
                        .requiredHeight(276.dp),
                    charData = charData,
                )
            }
            item {
                CodePointInfoItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .requiredHeight(276.dp),
                    charData = charData,
                    blockName = viewModel.blockName,
                    scriptData = viewModel.scriptData,
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CodePointUnicodeCardItem(
    modifier: Modifier = Modifier,
    charData: QueryDataByCodeWithUserData,
) {
    Box(
        modifier = modifier,
    ) {
        UnicodeCard(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(
                    y = (-28).dp,
                ),
            codePoint = charData.code_point.toInt(),
            category = charData.category,
            valueCover = charData.cover,
            count = charData.card_count.toInt(),
        )
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
                )
                .align(Alignment.BottomCenter),
            text = charData.name,
            fontSize = 24.sp,
            maxLines = 1,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun CodePointInfoItem(
    modifier: Modifier = Modifier,
    charData: QueryDataByCodeWithUserData,
    blockName: String,
    scriptData: Uc_unicode_iso15924?,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row {
            CardProperties(
                key = "UTF-8",
                value = UnicodeUtils
                    .charToUtf8(charData.code_point.toInt())
                    .joinToString(
                        separator = " "
                    ) {
                        it.toString(radix = 16)
                            .padStart(length = 2, padChar = '0')
                            .uppercase()
                    },
            )
            CardProperties(
                key = "UTF-16",
                value = UnicodeUtils
                    .charToUtf16(charData.code_point.toInt())
                    .joinToString(
                        separator = " "
                    ) {
                        it.toString(radix = 16)
                            .padStart(length = 4, padChar = '0')
                            .uppercase()
                    },
            )
            CardProperties(
                key = "UTF-32",
                value = charData.code_point.toInt()
                    .toString(radix = 16)
                    .padStart(length = 8, padChar = '0')
                    .uppercase(),
            )
        }
        Row {
            CardProperties(
                key = stringResource(
                    resource = Res.string.unicode_plane,
                ),
                value = (charData.code_point / 0x10000).toString(),
            )
            CardProperties(
                key = stringResource(
                    resource = Res.string.unicode_block,
                ),
                value = blockName,
            )
            CardProperties(
                key = stringResource(
                    resource = Res.string.unicode_script,
                ),
                value = scriptData?.let {
                    "${it.name.replace('_', ' ')} (${it.code})"
                } ?: "",
            )
        }
        Row {
            ClickableCardProperties(
                onClick = {

                },
                key = stringResource(
                    resource = Res.string.unicode_category,
                ),
                value = stringResource(
                    resource = Res.string.unicode_category_format,
                    stringResource(
                        resource = charData.category.getInfoStringRes(),
                    ),
                    charData.category.toString(),
                ),
            )
            ClickableCardProperties(
                onClick = {

                },
                key = stringResource(
                    resource = Res.string.unicode_bidirectional,
                ),
                value = charData.bidi_class.toString(),
            )
            ClickableCardProperties(
                onClick = {

                },
                key = stringResource(
                    resource = Res.string.unicode_combining,
                ),
                value = charData.combining.toString(),
            )
        }
    }
}

@Composable
private fun RowScope.CardProperties(
    key: String,
    value: String,
) {
    OutlinedCard(
        modifier = Modifier
            .padding(all = 4.dp)
            .requiredHeight(56.dp)
            .weight(1F),
    ) {
        CardPropertiesInner(
            key = key,
            value = value,
        )
    }
}

@Composable
private fun RowScope.ClickableCardProperties(
    onClick: () -> Unit,
    key: String,
    value: String,
) {
    OutlinedCard(
        modifier = Modifier
            .padding(all = 4.dp)
            .requiredHeight(56.dp)
            .weight(1F),
        onClick = onClick,
    ) {
        CardPropertiesInner(
            key = key,
            value = value,
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CardPropertiesInner(
    key: String,
    value: String,
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .basicMarquee(
                iterations = Int.MAX_VALUE,
            )
            .padding(
                horizontal = 4.dp,
            ),
        text = key,
        textAlign = TextAlign.Center,
        fontSize = 12.sp,
    )
    HorizontalDivider()
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .basicMarquee(
                iterations = Int.MAX_VALUE,
            )
            .padding(
                horizontal = 4.dp,
                vertical = 2.dp,
            ),
        text = value,
        textAlign = TextAlign.Center,
        fontSize = 16.sp,
    )
}