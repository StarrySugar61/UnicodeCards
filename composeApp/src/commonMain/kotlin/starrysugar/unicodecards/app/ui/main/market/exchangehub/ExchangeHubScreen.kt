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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import starrysugar.unicodecards.Res
import starrysugar.unicodecards.app.ui.base.AppNavBackTopBar
import starrysugar.unicodecards.app.ui.base.AppScaffold
import starrysugar.unicodecards.app.ui.base.appViewModelFactory
import starrysugar.unicodecards.app.ui.common.game.UnicodeCard
import starrysugar.unicodecards.app.ui.common.paging.AppLazyPagingList
import starrysugar.unicodecards.app.ui.common.paging.collectAsLazyPagingItems
import starrysugar.unicodecards.appdata.database.table.QueryDataByCodeWithUserData
import starrysugar.unicodecards.appdata.database.table.Uc_fake_exchange_requests
import starrysugar.unicodecards.arch.utils.TimeUtils
import starrysugar.unicodecards.cancel
import starrysugar.unicodecards.confirm
import starrysugar.unicodecards.market_exchange_dialog_give
import starrysugar.unicodecards.market_exchange_dialog_select
import starrysugar.unicodecards.market_exchange_dialog_title
import starrysugar.unicodecards.market_exchange_dialog_warn_already_owned
import starrysugar.unicodecards.market_exchange_dialog_warn_no_duplicate
import starrysugar.unicodecards.market_exchange_dialog_warn_not_selected
import starrysugar.unicodecards.market_exchange_hub
import starrysugar.unicodecards.market_exchange_hub_available
import starrysugar.unicodecards.market_exchange_hub_posted_time
import starrysugar.unicodecards.market_exchange_hub_wanted
import kotlin.random.Random

/**
 * @author StarrySugar61
 * @create 2024/6/28
 */
@OptIn(ExperimentalFoundationApi::class)
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
    var exchangingItem by remember {
        mutableStateOf<Uc_fake_exchange_requests?>(null)
    }
    val isPlatformFont by viewModel.isPlatformFontFlow.collectAsState(false)
    val isSerif by viewModel.isSerifFlow.collectAsState(false)
    exchangingItem?.let {
        ExchangingDialog(
            viewModel = viewModel,
            item = it,
            onConfirmed = { selectedCodePoint ->
                viewModel.onExchangeCard(
                    exchangeId = it.id,
                    cardGiven = it.card_wanted,
                    cardReceived = selectedCodePoint,
                )
                exchangingItem = null
            },
            onDismiss = {
                exchangingItem = null
            },
            isPlatformFont = isPlatformFont,
            isSerif = isSerif,
        )
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
        val lazyPagingItems = viewModel.requestFlow.collectAsLazyPagingItems()
        AppLazyPagingList(
            modifier = Modifier.padding(paddingValues = paddingValues),
            lazyPagingItems = lazyPagingItems,
            itemKey = { index ->
                lazyPagingItems[index]?.id ?: Random.nextLong()
            },
            itemContent = { _, item ->
                ExchangeItem(
                    modifier = Modifier
                        .animateItemPlacement()
                        .fillMaxWidth()
                        .padding(
                            start = 4.dp,
                            end = 4.dp,
                            bottom = 4.dp,
                        ),
                    item = item,
                    now = now,
                    viewModel = viewModel,
                    onClick = {
                        exchangingItem = item
                    },
                    isPlatformFont = isPlatformFont,
                    isSerif = isSerif,
                )
            },
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ExchangingDialog(
    viewModel: ExchangeHubViewModel,
    item: Uc_fake_exchange_requests,
    onConfirmed: (selectedCodePoint: Long) -> Unit,
    onDismiss: () -> Unit,
    isPlatformFont: Boolean,
    isSerif: Boolean,
) {
    var selectedCodePoint by remember {
        mutableStateOf(-1L)
    }
    var warningText by remember {
        mutableStateOf<StringResource?>(null)
    }
    val cardWanted = viewModel.getCodePointData(item.card_wanted)

    @Composable
    fun mapAvailableFor(
        cardAvailable: Long,
    ) {
        cardAvailable.takeUnless { it == -1L }
            ?.let {
                viewModel.getCodePointData(it)
            }
            ?.let {
                ExchangeAvailableSelectingCard(
                    modifier = Modifier
                        .padding(
                            all = 2.dp
                        ),
                    data = it,
                    isSelected = it.code_point == selectedCodePoint,
                    onClicked = {
                        selectedCodePoint = it.code_point
                        warningText = if (it.card_count > 0) {
                            Res.string.market_exchange_dialog_warn_already_owned
                        } else {
                            null
                        }
                    },
                    isPlatformFont = isPlatformFont,
                    isSerif = isSerif,
                )
            }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = stringResource(
                    resource = Res.string.market_exchange_dialog_title,
                ),
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(
                        resource = Res.string.market_exchange_dialog_give,
                        item.username,
                    ),
                )
                UnicodeCard(
                    modifier = Modifier
                        .padding(
                            vertical = 4.dp,
                        ),
                    scale = 0.4F,
                    codePoint = cardWanted.code_point.toInt(),
                    category = cardWanted.category,
                    valueCover = cardWanted.cover,
                    isPlatformFont = isPlatformFont,
                    isSerif = isSerif,
                )
                if (cardWanted.card_count > 1) {
                    Text(
                        modifier = Modifier.fillMaxWidth()
                            .padding(
                                vertical = 4.dp,
                            ),
                        text = stringResource(
                            resource = Res.string.market_exchange_dialog_select,
                        ),
                    )
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        mapAvailableFor(item.card_available_1)
                        mapAvailableFor(item.card_available_2)
                        mapAvailableFor(item.card_available_3)
                        mapAvailableFor(item.card_available_4)
                        mapAvailableFor(item.card_available_5)
                    }
                } else {
                    Text(
                        modifier = Modifier
                            .padding(
                                vertical = 4.dp,
                            ),
                        text = stringResource(
                            resource = Res.string.market_exchange_dialog_warn_no_duplicate,
                        ),
                        color = MaterialTheme.colorScheme.error,
                    )
                }
                warningText?.let {
                    Text(
                        modifier = Modifier
                            .padding(
                                vertical = 4.dp,
                            ),
                        text = stringResource(
                            resource = it,
                        ),
                        color = MaterialTheme.colorScheme.error,
                    )
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    when {
                        cardWanted.card_count < 2 -> onDismiss()
                        selectedCodePoint < 0 -> warningText =
                            Res.string.market_exchange_dialog_warn_not_selected

                        else -> onConfirmed(selectedCodePoint)
                    }
                },
            ) {
                Text(
                    text = stringResource(
                        resource = Res.string.confirm,
                    ),
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
            ) {
                Text(
                    text = stringResource(
                        resource = Res.string.cancel,
                    ),
                )
            }
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ExchangeItem(
    modifier: Modifier = Modifier,
    item: Uc_fake_exchange_requests,
    now: Long,
    viewModel: ExchangeHubViewModel,
    onClick: () -> Unit,
    isPlatformFont: Boolean,
    isSerif: Boolean,
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
                isPlatformFont = isPlatformFont,
                isSerif = isSerif,
            )
            Text(
                modifier = Modifier.width(40.dp),
                text = "→",
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
                        ExchangeAvailableCard(
                            modifier = Modifier
                                .padding(
                                    end = 4.dp
                                ),
                            data = it,
                            isPlatformFont = isPlatformFont,
                            isSerif = isSerif,
                        )
                    }
                // card2
                item.card_available_2
                    .takeUnless { it == -1L }
                    ?.let {
                        viewModel.getCodePointData(it)
                    }
                    ?.let {
                        ExchangeAvailableCard(
                            modifier = Modifier
                                .padding(
                                    end = 4.dp
                                ),
                            data = it,
                            isPlatformFont = isPlatformFont,
                            isSerif = isSerif,
                        )
                    }
                // card3
                item.card_available_3
                    .takeUnless { it == -1L }
                    ?.let {
                        viewModel.getCodePointData(it)
                    }
                    ?.let {
                        ExchangeAvailableCard(
                            modifier = Modifier
                                .padding(
                                    end = 4.dp
                                ),
                            data = it,
                            isPlatformFont = isPlatformFont,
                            isSerif = isSerif,
                        )
                    }
                // card4
                item.card_available_4
                    .takeUnless { it == -1L }
                    ?.let {
                        viewModel.getCodePointData(it)
                    }
                    ?.let {
                        ExchangeAvailableCard(
                            modifier = Modifier
                                .padding(
                                    end = 4.dp
                                ),
                            data = it,
                            isPlatformFont = isPlatformFont,
                            isSerif = isSerif,
                        )
                    }
                // card5
                item.card_available_5
                    .takeUnless { it == -1L }
                    ?.let {
                        viewModel.getCodePointData(it)
                    }
                    ?.let {
                        ExchangeAvailableCard(
                            modifier = Modifier
                                .padding(
                                    end = 4.dp
                                ),
                            data = it,
                            isPlatformFont = isPlatformFont,
                            isSerif = isSerif,
                        )
                    }
            }
        }
    }
}

@Composable
private fun ExchangeAvailableCard(
    modifier: Modifier = Modifier,
    data: QueryDataByCodeWithUserData,
    isPlatformFont: Boolean,
    isSerif: Boolean,
) {
    UnicodeCard(
        modifier = modifier
            .alpha(
                alpha = if (data.card_count == 0L) {
                    1F
                } else {
                    0.4F
                },
            ),
        scale = 0.4F,
        codePoint = data.code_point.toInt(),
        category = data.category,
        valueCover = data.cover,
        isPlatformFont = isPlatformFont,
        isSerif = isSerif,
    )
}

@Composable
private fun ExchangeAvailableSelectingCard(
    modifier: Modifier = Modifier,
    data: QueryDataByCodeWithUserData,
    isSelected: Boolean?,
    onClicked: () -> Unit,
    isPlatformFont: Boolean,
    isSerif: Boolean,
) {
    Box(
        modifier = modifier
    ) {
        ExchangeAvailableCard(
            modifier = Modifier.clickable(onClick = onClicked),
            data = data,
            isPlatformFont = isPlatformFont,
            isSerif = isSerif,
        )
        isSelected?.let {
            RadioButton(
                modifier = Modifier.align(Alignment.TopEnd),
                selected = it,
                onClick = null,
            )
        }
    }
}

