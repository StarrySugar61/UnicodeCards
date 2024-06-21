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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import starrysugar.unicodecards.app.nav.Screen
import starrysugar.unicodecards.app.ui.common.game.UnicodeCardPack
import starrysugar.unicodecards.appdata.models.pack.CardPacks

/**
 * @author StarrySugar61
 * @create 2024/6/17
 */
@Composable
fun MarketPage(
    navController: NavHostController,
    viewModel: MarketViewModel = viewModel(),
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        val cardPackSample = CardPacks.data[0] ?: return
        Column {
            UnicodeCardPack(
                modifier = Modifier.clickable {
                    navController.navigate(
                        Screen.MarketOpenPack.buildRoute(packID = 0, count = 10)
                    )
                },
                sampleCodePoint = cardPackSample.sampleCodePoint,
                packName = cardPackSample.name,
                cardCount = 10,
            )
        }
    }
}