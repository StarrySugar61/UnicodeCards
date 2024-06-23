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
package starrysugar.unicodecards.app.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import starrysugar.unicodecards.Res
import starrysugar.unicodecards.app.ui.base.AppScaffold
import starrysugar.unicodecards.app.ui.common.game.UnicodeCard
import starrysugar.unicodecards.app.ui.common.game.UnicodeCardBack
import starrysugar.unicodecards.app.ui.main.cards.CardsPage
import starrysugar.unicodecards.app.ui.main.home.HomePage
import starrysugar.unicodecards.app.ui.main.market.MarketPage
import starrysugar.unicodecards.app.ui.main.settings.SettingsPage
import starrysugar.unicodecards.app_name
import starrysugar.unicodecards.appdata.unicode.CharCategory
import starrysugar.unicodecards.arch.utils.toPrecision
import starrysugar.unicodecards.ic_baseline_home_24
import starrysugar.unicodecards.ic_baseline_settings_24
import starrysugar.unicodecards.ic_baseline_store_mall_directory_24
import starrysugar.unicodecards.ic_baseline_style_24
import starrysugar.unicodecards.main_tab_cards
import starrysugar.unicodecards.main_tab_home
import starrysugar.unicodecards.main_tab_market
import starrysugar.unicodecards.main_tab_settings
import kotlin.math.sin

/**
 * The main screen!
 *
 * @author StarrySugar61
 * @create 2024/6/15
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavHostController,
    viewModel: MainViewModel = viewModel(),
) {
    val homeBottomNavController = rememberNavController()
    if (viewModel.currentStep > viewModel.totalSteps) {
        AppScaffold(
            viewModel = viewModel,
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = stringResource(
                                resource = Res.string.app_name,
                            ),
                        )
                    }
                )
            },
            bottomBar = {
                HomeBottomBar(
                    navController = homeBottomNavController,
                    selectedTabIndex = viewModel.selectedTabIndex,
                    onTabSelected = {
                        viewModel.selectedTabIndex = it
                    },
                )
            },
        ) { paddingValues ->
            HomeNavHost(
                modifier = Modifier.padding(paddingValues = paddingValues),
                navController = homeBottomNavController,
                mainNavController = navController,
            )
        }
    } else {
        LoadingScreen(
            modifier = Modifier.fillMaxSize(),
            viewModel = viewModel,
        )
    }
}

@Composable
private fun LoadingScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
) {
    var sineAnimate by remember { mutableStateOf(0) }
    LaunchedEffect(
        key1 = Unit,
    ) {
        while (true) {
            sineAnimate++
            if (sineAnimate == 125) {
                sineAnimate = 0
            }
            delay(16)
        }
    }
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val scaleX = sin(sineAnimate * 0.05026f)
            val stepProgress = 1f / viewModel.totalSteps
            val progress = stepProgress * (viewModel.currentStep - 1
                    + 1f * viewModel.progress / viewModel.maxProgress)
            if (scaleX > 0) {
                UnicodeCard(
                    modifier = Modifier
                        .scale(
                            scaleX = scaleX,
                            scaleY = 1f,
                        ),
                    codePoint = 0x27f3,
                    category = CharCategory.Sm,
                )
            } else {
                UnicodeCardBack(
                    modifier = Modifier
                        .scale(
                            scaleX = -scaleX,
                            scaleY = 1f,
                        ),
                )
            }
            Text(
                modifier = Modifier.padding(
                    horizontal = 16.dp,
                    vertical = 8.dp,
                ),
                text = "${(100f * progress).toPrecision(2)}%"
            )
            LinearProgressIndicator(
                modifier = Modifier.width(180.dp),
                progress = { progress },
            )
        }
    }
}

@Composable
private fun HomeBottomBar(
    navController: NavHostController,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
) {
    NavigationBar {
        mainBottomAppBarItems.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedTabIndex == index,
                label = {
                    Text(
                        text = stringResource(
                            resource = item.labelRes,
                        ),
                    )
                },
                icon = {
                    Icon(
                        painter = painterResource(
                            resource = item.iconRes,
                        ),
                        contentDescription = null,
                    )
                },
                onClick = {
                    onTabSelected(index)
                    navController.navigate(item.route) {
                        navController.graph.findStartDestination().route?.let {
                            popUpTo(it) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
private fun HomeNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    mainNavController: NavHostController,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = mainBottomAppBarItems[0].route,
    ) {
        composable(
            route = mainBottomAppBarItems[0].route,
        ) {
            HomePage(
                navController = mainNavController,
            )
        }
        composable(
            route = mainBottomAppBarItems[1].route,
        ) {
            CardsPage(
                navController = mainNavController,
            )
        }
        composable(
            route = mainBottomAppBarItems[2].route,
        ) {
            MarketPage(
                navController = mainNavController,
            )
        }
        composable(
            route = mainBottomAppBarItems[3].route,
        ) {
            SettingsPage(
                navController = mainNavController,
            )
        }
    }
}

private val mainBottomAppBarItems = listOf(
    MainBottomAppBarItem(
        labelRes = Res.string.main_tab_home,
        iconRes = Res.drawable.ic_baseline_home_24,
        route = "uc_main_home",
    ),
    MainBottomAppBarItem(
        labelRes = Res.string.main_tab_cards,
        iconRes = Res.drawable.ic_baseline_style_24,
        route = "uc_main_cards",
    ),
    MainBottomAppBarItem(
        labelRes = Res.string.main_tab_market,
        iconRes = Res.drawable.ic_baseline_store_mall_directory_24,
        route = "uc_main_market",
    ),
    MainBottomAppBarItem(
        labelRes = Res.string.main_tab_settings,
        iconRes = Res.drawable.ic_baseline_settings_24,
        route = "uc_main_settings",
    ),
)

private class MainBottomAppBarItem(
    val labelRes: StringResource,
    val iconRes: DrawableResource,
    val route: String,
)