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
package starrysugar.unicodecards.app.ui.main.market

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.pluralStringResource
import org.jetbrains.compose.resources.stringResource
import starrysugar.unicodecards.Res
import starrysugar.unicodecards.app.nav.Screen
import starrysugar.unicodecards.app.ui.base.appViewModelFactory
import starrysugar.unicodecards.app.ui.common.game.UnicodeCardPack
import starrysugar.unicodecards.appdata.configs.AppConfigs
import starrysugar.unicodecards.appdata.models.pack.CardPacks
import starrysugar.unicodecards.arch.utils.TimeUtils
import starrysugar.unicodecards.coming_soon
import starrysugar.unicodecards.market_exchange_hub
import starrysugar.unicodecards.market_free_count
import starrysugar.unicodecards.market_free_pack
import starrysugar.unicodecards.market_free_refill
import starrysugar.unicodecards.market_the_chamber_of_time
import starrysugar.unicodecards.market_welcome_pack_line_1
import starrysugar.unicodecards.market_welcome_pack_line_2

/**
 * @author StarrySugar61
 * @create 2024/6/17
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MarketPage(
    navController: NavHostController,
    viewModel: MarketViewModel = viewModel(
        factory = appViewModelFactory,
    ),
) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Adaptive(
            minSize = 300.dp,
        ),
        horizontalArrangement = Arrangement.Center,
    ) {
        item(
            key = "Placeholder",
        ) {
            Spacer(modifier = Modifier)
        }
        if (!viewModel.isWelcomePackClaimed) {
            item(
                key = "WelcomeCardPack",
            ) {
                MarketItemWelcome(
                    modifier = Modifier
                        .animateItemPlacement()
                        .padding(
                            start = 4.dp,
                            end = 4.dp,
                            bottom = 4.dp,
                        ),
                    onClick = {
                        viewModel.claimWelcomePack(
                            onClaimed = {
                                navController.navigate(
                                    Screen.MarketOpenPack.buildRoute(
                                        packID = 1,
                                        count = AppConfigs.WELCOME_PACK_CARDS,
                                    )
                                )
                            },
                        )
                    }
                )
            }
        }
        if (viewModel.freePackFullTime > 0) {
            item(
                key = "FreePack",
            ) {
                val packsAvailable = AppConfigs.Utils.freePacksAvailable(
                    freePackFullTime = viewModel.freePackFullTime,
                )
                MarketItemFree(
                    modifier = Modifier
                        .animateItemPlacement()
                        .padding(
                            start = 4.dp,
                            end = 4.dp,
                            bottom = 4.dp,
                        ),
                    freePackFullTime = viewModel.freePackFullTime,
                    packsAvailable = packsAvailable,
                    onClick = {
                        if (packsAvailable == 0) {
                            // TODO
                        } else {
                            viewModel.claimFreePack(
                                onClaimed = {
                                    navController.navigate(
                                        Screen.MarketOpenPack.buildRoute(
                                            packID = 0,
                                            count = AppConfigs.FREE_PACK_CARDS,
                                        )
                                    )
                                },
                            )
                        }
                    }
                )
            }
        }
        item(
            key = "Exchange"
        ) {
            MarketItemExchange(
                modifier = Modifier
                    .animateItemPlacement()
                    .padding(
                        start = 4.dp,
                        end = 4.dp,
                        bottom = 4.dp,
                    ),
                onClick = {}
            )
        }
        item(
            key = "TimeChamber"
        ) {
            MarketItemTimeChamber(
                modifier = Modifier
                    .animateItemPlacement()
                    .padding(
                        start = 4.dp,
                        end = 4.dp,
                        bottom = 4.dp,
                    ),
                onClick = {}
            )
        }
    }
}

/**
 * Welcome Card!
 * item=1
 */
@Composable
private fun MarketItemWelcome(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val welcomeCardPack = CardPacks.data[1] ?: return
    OutlinedCard(
        modifier = modifier
            .requiredHeight(180.dp),
        onClick = onClick,
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1F)
            ) {
                UnicodeCardPack(
                    modifier = Modifier.align(Alignment.Center),
                    scale = 0.5F,
                    sampleCodePoint = welcomeCardPack.sampleCodePoint,
                    packName = welcomeCardPack.name,
                    cardCount = AppConfigs.WELCOME_PACK_CARDS,
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1F),
                verticalArrangement = Arrangement.SpaceEvenly,
            ) {
                Text(
                    text = stringResource(
                        resource = Res.string.market_welcome_pack_line_1,
                    ),
                )
                Text(
                    text = stringResource(
                        resource = Res.string.market_welcome_pack_line_2,
                        AppConfigs.WELCOME_PACK_CARDS,
                    ),
                )
            }
        }
    }
}

/**
 * Welcome Card!
 * item=1
 */
@Composable
private fun MarketItemFree(
    modifier: Modifier = Modifier,
    freePackFullTime: Long,
    packsAvailable: Int,
    onClick: () -> Unit,
) {
    val freePack = CardPacks.data[0] ?: return
    val packAlpha by animateFloatAsState(
        if (packsAvailable == 0) {
            0.4F
        } else {
            1F
        }
    )
    var refillTime by remember {
        mutableStateOf(
            (freePackFullTime - TimeUtils.currentTimeMillis()) %
                    AppConfigs.FREE_PACK_REFILL_TIME
        )
    }
    LaunchedEffect(
        key1 = refillTime
    ) {
        delay(1000)
        refillTime = (freePackFullTime - TimeUtils.currentTimeMillis()) %
                AppConfigs.FREE_PACK_REFILL_TIME
    }
    OutlinedCard(
        modifier = modifier
            .requiredHeight(180.dp),
        onClick = onClick,
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1F)
                    .alpha(packAlpha),
            ) {
                UnicodeCardPack(
                    modifier = Modifier.align(Alignment.Center),
                    scale = 0.5F,
                    sampleCodePoint = freePack.sampleCodePoint,
                    packName = freePack.name,
                    cardCount = AppConfigs.FREE_PACK_CARDS,
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1F),
                verticalArrangement = Arrangement.SpaceEvenly,
            ) {
                Text(
                    text = stringResource(
                        resource = Res.string.market_free_pack,
                        AppConfigs.FREE_PACK_CARDS,
                    ),
                )
                Text(
                    text = if (packsAvailable > 0) {
                        pluralStringResource(
                            resource = Res.plurals.market_free_count,
                            packsAvailable,
                            packsAvailable,
                        )
                    } else {
                        stringResource(
                            resource = Res.string.market_free_refill,
                            TimeUtils.millisToString(refillTime),
                        )
                    },
                )
            }
        }
    }
}

/**
 * Exchange Hub!
 * Exchange cards with others!
 */
@Composable
private fun MarketItemExchange(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    OutlinedCard(
        modifier = modifier
            .requiredHeight(180.dp),
        onClick = onClick,
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1F),
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "\uD83C\uDFEC",
                    fontSize = 96.sp,
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1F),
                verticalArrangement = Arrangement.SpaceEvenly,
            ) {
                Text(
                    text = stringResource(
                        resource = Res.string.market_exchange_hub,
                    ),
                )
                Text(
                    text = stringResource(
                        resource = Res.string.coming_soon,
                    ),
                )
            }
        }
    }
}

/**
 * The Chamber of Time!
 * The only way to obtain cards above SMP!
 */
@Composable
private fun MarketItemTimeChamber(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    OutlinedCard(
        modifier = modifier
            .requiredHeight(180.dp),
        onClick = onClick,
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1F),
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "\uD83D\uDD70\uFE0F",
                    fontSize = 96.sp,
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1F),
                verticalArrangement = Arrangement.SpaceEvenly,
            ) {
                Text(
                    text = stringResource(
                        resource = Res.string.market_the_chamber_of_time,
                    ),
                )
                Text(
                    text = stringResource(
                        resource = Res.string.coming_soon,
                    ),
                )
            }
        }
    }
}
