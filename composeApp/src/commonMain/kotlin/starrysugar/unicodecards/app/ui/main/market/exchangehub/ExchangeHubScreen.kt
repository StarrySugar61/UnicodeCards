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
package starrysugar.unicodecards.app.ui.main.market.exchangehub

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.stringResource
import starrysugar.unicodecards.Res
import starrysugar.unicodecards.app.ui.base.AppNavBackTopBar
import starrysugar.unicodecards.app.ui.base.AppScaffold
import starrysugar.unicodecards.app.ui.base.appViewModelFactory
import starrysugar.unicodecards.app.ui.common.game.UnicodeCard
import starrysugar.unicodecards.app.ui.common.paging.AppLazyPagingList
import starrysugar.unicodecards.app.ui.common.paging.collectAsLazyPagingItems
import starrysugar.unicodecards.appdata.database.table.Uc_fake_exchange_requests
import starrysugar.unicodecards.arch.utils.TimeUtils
import starrysugar.unicodecards.market_exchange_hub
import starrysugar.unicodecards.market_exchange_hub_available
import starrysugar.unicodecards.market_exchange_hub_posted_time
import starrysugar.unicodecards.market_exchange_hub_wanted

/**
 * @author StarrySugar61
 * @create 2024/6/28
 */
@Composable
fun ExchangeHubScreen(
    navController: NavHostController,
    viewModel: ExchangeHubViewModel = viewModel(
        factory = appViewModelFactory,
    ),
) {
    var now by remember {
        mutableStateOf(TimeUtils.currentTimeMillis())
    }
    LaunchedEffect(
        key1 = now,
    ) {
        delay(1000)
        now = TimeUtils.currentTimeMillis()
    }
    AppScaffold(
        modifier = Modifier.fillMaxSize(),
        viewModel = viewModel,
        topBar = {
            AppNavBackTopBar(
                title = {
                    Text(
                        text = stringResource(
                            resource = Res.string.market_exchange_hub,
                        ),
                    )
                },
                onBack = {
                    navController.navigateUp()
                },
            )
        }
    ) { paddingValues ->
        AppLazyPagingList(
            modifier = Modifier.padding(paddingValues = paddingValues),
            lazyPagingItems = viewModel.requestFlow.collectAsLazyPagingItems(),
            itemContent = { _, item ->
                ExchangeItem(
                    modifier = Modifier.fillMaxWidth()
                        .padding(
                            start = 4.dp,
                            end = 4.dp,
                            bottom = 4.dp,
                        ),
                    item = item,
                    now = now,
                    viewModel = viewModel,
                    onClick = {

                    },
                )
            },
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ExchangeItem(
    modifier: Modifier = Modifier,
    item: Uc_fake_exchange_requests,
    now: Long,
    viewModel: ExchangeHubViewModel,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier,
        onClick = onClick,
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 4.dp,
                ),
            text = stringResource(
                resource = Res.string.market_exchange_hub_posted_time,
                TimeUtils.millisToString(now - item.sent_time),
            ),
            textAlign = TextAlign.End,
            fontSize = 11.sp,
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .basicMarquee(
                    iterations = Int.MAX_VALUE,
                )
                .padding(
                    start = 8.dp,
                    end = 8.dp,
                    bottom = 4.dp,
                ),
            text = item.username,
            textAlign = TextAlign.Start,
            maxLines = 1,
            fontSize = 22.sp,
        )
        HorizontalDivider()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 8.dp,
                ),
        ) {
            Text(
                modifier = Modifier
                    .width(100.dp),
                text = stringResource(
                    resource = Res.string.market_exchange_hub_wanted,
                ),
                fontSize = 11.sp,
            )
            Text(
                modifier = Modifier
                    .weight(1F),
                text = stringResource(
                    resource = Res.string.market_exchange_hub_available,
                ),
                fontSize = 11.sp,
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 8.dp,
                    bottom = 8.dp,
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val cardWanted = viewModel.getCodePointData(item.card_wanted)
            UnicodeCard(
                modifier = Modifier
                    .alpha(
                        alpha = if (cardWanted.card_count > 1) {
                            1F
                        } else {
                            0.4F
                        },
                    ),
                scale = 0.4F,
                codePoint = cardWanted.code_point.toInt(),
                category = cardWanted.category,
                valueCover = cardWanted.cover,
            )
            Text(
                modifier = Modifier.width(40.dp),
                text = "←",
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
            )
            Row(
                modifier = Modifier
                    .weight(1F)
                    .basicMarquee(
                        iterations = Int.MAX_VALUE,
                    ),
            ) {
                // card1
                item.card_available_1
                    .takeUnless { it == -1L }
                    ?.let {
                        viewModel.getCodePointData(it)
                    }
                    ?.let {
                        UnicodeCard(
                            modifier = Modifier
                                .padding(
                                    end = 4.dp
                                )
                                .alpha(
                                    alpha = if (it.card_count == 0L) {
                                        1F
                                    } else {
                                        0.4F
                                    },
                                ),
                            scale = 0.4F,
                            codePoint = it.code_point.toInt(),
                            category = it.category,
                            valueCover = it.cover,
                        )
                    }
                // card2
                item.card_available_2
                    .takeUnless { it == -1L }
                    ?.let {
                        viewModel.getCodePointData(it)
                    }
                    ?.let {
                        UnicodeCard(
                            modifier = Modifier
                                .padding(
                                    end = 4.dp
                                )
                                .alpha(
                                    alpha = if (it.card_count == 0L) {
                                        1F
                                    } else {
                                        0.4F
                                    },
                                ),
                            scale = 0.4F,
                            codePoint = it.code_point.toInt(),
                            category = it.category,
                            valueCover = it.cover,
                        )
                    }
                // card3
                item.card_available_3
                    .takeUnless { it == -1L }
                    ?.let {
                        viewModel.getCodePointData(it)
                    }
                    ?.let {
                        UnicodeCard(
                            modifier = Modifier
                                .padding(
                                    end = 4.dp
                                )
                                .alpha(
                                    alpha = if (it.card_count == 0L) {
                                        1F
                                    } else {
                                        0.4F
                                    },
                                ),
                            scale = 0.4F,
                            codePoint = it.code_point.toInt(),
                            category = it.category,
                            valueCover = it.cover,
                        )
                    }
                // card4
                item.card_available_4
                    .takeUnless { it == -1L }
                    ?.let {
                        viewModel.getCodePointData(it)
                    }
                    ?.let {
                        UnicodeCard(
                            modifier = Modifier
                                .padding(
                                    end = 4.dp
                                )
                                .alpha(
                                    alpha = if (it.card_count == 0L) {
                                        1F
                                    } else {
                                        0.4F
                                    },
                                ),
                            scale = 0.4F,
                            codePoint = it.code_point.toInt(),
                            category = it.category,
                            valueCover = it.cover,
                        )
                    }
                // card5
                item.card_available_5
                    .takeUnless { it == -1L }
                    ?.let {
                        viewModel.getCodePointData(it)
                    }
                    ?.let {
                        UnicodeCard(
                            modifier = Modifier
                                .padding(
                                    end = 4.dp
                                )
                                .alpha(
                                    alpha = if (it.card_count == 0L) {
                                        1F
                                    } else {
                                        0.4F
                                    },
                                ),
                            scale = 0.4F,
                            codePoint = it.code_point.toInt(),
                            category = it.category,
                            valueCover = it.cover,
                        )
                    }
            }
        }
    }
}
