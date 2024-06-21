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
package starrysugar.unicodecards.app.ui.main.market.pack.openpack

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import org.jetbrains.compose.resources.stringResource
import starrysugar.unicodecards.Res
import starrysugar.unicodecards.app.ui.base.AppScaffold
import starrysugar.unicodecards.app.ui.base.BlockBackPressed
import starrysugar.unicodecards.app.ui.base.appViewModelFactory
import starrysugar.unicodecards.app.ui.common.game.UnicodeCard
import starrysugar.unicodecards.app.ui.common.game.UnicodeCardBack
import starrysugar.unicodecards.app.ui.common.game.UnicodeCardPack
import starrysugar.unicodecards.app.ui.common.game.UnicodeCardPackEndPart
import starrysugar.unicodecards.appdata.database.table.QueryDataByIndexWithUserData
import starrysugar.unicodecards.confirm
import starrysugar.unicodecards.market_click_me
import starrysugar.unicodecards.market_pack_obtained
import starrysugar.unicodecards.skip

/**
 * When opened this page, a pack for certain id and count will be opened!
 *
 * @author StarrySugar61
 * @create 2024/6/20
 */
@Composable
fun OpenPackScreen(
    packID: Int,
    count: Int,
    navController: NavHostController,
    viewModel: OpenPackViewModel = viewModel(
        factory = appViewModelFactory,
        extras = MutableCreationExtras().apply {
            this[OpenPackViewModel.PackIDKey] = packID
            this[OpenPackViewModel.CountKey] = count
        }
    )
) {
    // Block back button
    BlockBackPressed()
    AppScaffold(
        modifier = Modifier.fillMaxSize(),
        viewModel = viewModel,
    ) { paddingValues ->
        val cardPack = viewModel.cardPack ?: return@AppScaffold
        // Animators for the pack animation!
        val cpEndYOffset by animateDpAsState(
            when {
                viewModel.animatorStep < 1 -> {
                    0.dp
                }

                else -> {
                    270.dp
                }
            },
            spring(
                visibilityThreshold = Dp.VisibilityThreshold,
                stiffness = Spring.StiffnessVeryLow,
            ),
        )
        val cpEndAlpha by animateFloatAsState(
            targetValue = when {
                viewModel.animatorStep < 1 -> 1F
                else -> 0F
            },
            animationSpec = spring(
                stiffness = Spring.StiffnessLow,
            ),
        )
        val cpStartXOffset by animateDpAsState(
            targetValue = when {
                viewModel.animatorStep < 2 -> 0.dp
                else -> (-190).dp
            },
            animationSpec = spring(
                visibilityThreshold = Dp.VisibilityThreshold,
                stiffness = Spring.StiffnessLow,
            ),
        )
        val cpStartAlpha by animateFloatAsState(
            targetValue = when {
                viewModel.animatorStep < 2 -> 1F
                else -> 0F
            },
            animationSpec = spring(
                stiffness = Spring.StiffnessVeryLow,
            ),
        )
        val cbYOffset by animateDpAsState(
            targetValue = when {
                viewModel.animatorStep < 3 -> 0.dp
                else -> 135.dp
            },
        )
        val csYOffset by animateDpAsState(
            targetValue = when (viewModel.animatorCardStep) {
                0, 1 -> 135.dp
                else -> 0.dp
            },
        )
        val csXScale by animateFloatAsState(
            targetValue = when (viewModel.animatorCardStep) {
                2, 3 -> 1F
                else -> -1F
            }
        )
        val csCardScale by animateFloatAsState(
            targetValue = when (viewModel.animatorCardStep) {
                2 -> 1.5F
                3 -> 0.1F
                else -> 1F
            }
        )
        val csAlpha by animateFloatAsState(
            targetValue = when (viewModel.animatorCardStep) {
                1, 2 -> 1F
                else -> 0F
            }
        )
        val cardNameScale by animateFloatAsState(
            targetValue = when (viewModel.animatorCardStep) {
                2 -> 1F
                else -> 0F
            }
        )
        val cardsLeftScale by animateFloatAsState(
            targetValue = when (viewModel.animatorStep) {
                3, 4 -> 1F
                else -> 0F
            }
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues),
            contentAlignment = Alignment.Center,
        ) {
            if (viewModel.animatorCardIndex < count - 1) {
                UnicodeCardBack(
                    modifier = Modifier
                        .absoluteOffset(
                            y = cbYOffset,
                        )
                )
            }
            if (viewModel.animatorStep < 5) {
                UnicodeCardPack(
                    modifier = Modifier
                        .size(
                            width = 190.dp,
                            height = 270.dp,
                        )
                        .clipToBounds()
                        .absoluteOffset(
                            x = cpStartXOffset,
                        )
                        .alpha(
                            alpha = cpStartAlpha,
                        ),
                    isOpened = true,
                    sampleCodePoint = cardPack.sampleCodePoint,
                    packName = cardPack.name,
                    cardCount = count,
                )
                UnicodeCardPackEndPart(
                    modifier = Modifier
                        .requiredSize(
                            width = 30.dp,
                            height = 270.dp,
                        )
                        .absoluteOffset(
                            x = 80.dp,
                            y = cpEndYOffset,
                        )
                        .alpha(
                            alpha = cpEndAlpha,
                        )
                )
            }
            if (viewModel.animatorStep == 0) {
                UnicodeCardPack(
                    modifier = Modifier.clickable {
                        viewModel.startAnimation()
                    },
                    sampleCodePoint = cardPack.sampleCodePoint,
                    packName = cardPack.name,
                    cardCount = count,
                )
                Text(
                    modifier = Modifier
                        .absoluteOffset(
                            y = 170.dp
                        ),
                    text = stringResource(
                        resource = Res.string.market_click_me,
                    ),
                    fontSize = 24.sp,
                )
            }
            // Show cards one by one!
            if (
                viewModel.animatorStep == 4
                && viewModel.animatorCardIndex in viewModel.cardPackResult.indices
            ) {
                val currentCard = viewModel.cardPackResult[viewModel.animatorCardIndex]
                OpenPackCardItem(
                    modifier = Modifier
                        .absoluteOffset(
                            y = csYOffset,
                        )
                        .alpha(
                            alpha = csAlpha,
                        ),
                    card = currentCard,
                    scaleX = csXScale,
                    cardScale = csCardScale,
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp)
                        .absoluteOffset(
                            y = (-200).dp,
                        )
                        .scale(
                            scale = cardNameScale,
                        ),
                    text = currentCard.name,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                )
            }
            // Cards left in the pack!
            OutlinedCard(
                modifier = Modifier
                    .requiredSize(
                        width = 60.dp,
                        height = 80.dp,
                    )
                    .offset(
                        x = 130.dp,
                        y = 195.dp,
                    )
                    .scale(
                        scale = cardsLeftScale,
                    ),
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = (count - viewModel.animatorCardIndex - 1).toString(),
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 32.sp,
                    )
                }
            }
            // Card results!
            AnimatedVisibility(
                visible = viewModel.animatorStep == 5,
            ) {
                OpenPackResult(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 16.dp,
                        )
                        .requiredHeight(
                            height = 450.dp,
                        ),
                    cardPackResult = viewModel.cardPackResult,
                )
            }
            // Button bar!
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.Center,
            ) {
                AnimatedVisibility(
                    visible = viewModel.animatorStep < 5
                ) {
                    FilledTonalButton(
                        onClick = {
                            viewModel.skipAnimation()
                        },
                        content = {
                            Text(
                                text = stringResource(
                                    resource = Res.string.skip,
                                ),
                            )
                        },
                    )
                }

                AnimatedVisibility(
                    visible = viewModel.animatorStep == 5
                ) {
                    FilledTonalButton(
                        onClick = {
                            navController.navigateUp()
                        },
                        content = {
                            Text(
                                text = stringResource(
                                    resource = Res.string.confirm,
                                ),
                            )
                        },
                    )
                }
            }
        }
    }
}

@Composable
private fun OpenPackCardItem(
    modifier: Modifier = Modifier,
    card: QueryDataByIndexWithUserData,
    scaleX: Float,
    cardScale: Float,
) {
    if (scaleX > 0) {
        UnicodeCard(
            modifier = modifier
                .scale(
                    scaleX = scaleX * cardScale,
                    scaleY = cardScale,
                ),
            codePoint = card.code_point.toInt(),
            category = card.category,
            valueCover = card.cover,
            count = if (card.card_count == 0L) {
                -1
            } else {
                0
            },
        )
    } else {
        UnicodeCardBack(
            modifier = modifier
                .scale(
                    scaleX = -scaleX * cardScale,
                    scaleY = cardScale,
                ),
        )
    }
}

@Composable
private fun OpenPackResult(
    modifier: Modifier = Modifier,
    cardPackResult: List<QueryDataByIndexWithUserData>,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    bottom = 8.dp,
                ),
            text = stringResource(
                resource = Res.string.market_pack_obtained,
            ),
        )
        LazyVerticalGrid(
            modifier = Modifier.fillMaxWidth(),
            columns = GridCells.FixedSize(
                size = 68.dp,
            ),
            horizontalArrangement = Arrangement.Center,
        ) {
            items(
                items = cardPackResult
            ) { item ->
                UnicodeCard(
                    modifier = Modifier
                        .padding(all = 4.dp),
                    scale = 0.4F,
                    codePoint = item.code_point.toInt(),
                    category = item.category,
                    valueCover = item.cover,
                    count = if (item.card_count == 0L) {
                        -1
                    } else {
                        0
                    },
                )
            }
        }
    }
}