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
package starrysugar.unicodecards.app.ui.main.mine

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import starrysugar.unicodecards.Res
import starrysugar.unicodecards.app.ui.base.appViewModelFactory
import starrysugar.unicodecards.app.ui.main.mine.settings.SettingColumn
import starrysugar.unicodecards.app.ui.main.mine.settings.switcherRow
import starrysugar.unicodecards.appdata.datastore.AppDataStoreKeys
import starrysugar.unicodecards.settings_appearance_serif
import starrysugar.unicodecards.settings_appearance_system_font

/**
 * @author StarrySugar61
 * @create 2024/6/17
 */
@Composable
fun MinePage(
    navController: NavHostController,
    viewModel: MineViewModel = viewModel(
        factory = appViewModelFactory,
    ),
) {
    SettingColumn(
        modifier = Modifier.fillMaxSize(),
        dataStore = viewModel.dataStore,
    ) {
        switcherRow(
            key = AppDataStoreKeys.KEY_SETTINGS_APPEARANCE_SYSTEM_FONT,
            labelRes = Res.string.settings_appearance_system_font,
        )
        switcherRow(
            key = AppDataStoreKeys.KEY_SETTINGS_APPEARANCE_SERIF,
            labelRes = Res.string.settings_appearance_serif,
        )
    }
}