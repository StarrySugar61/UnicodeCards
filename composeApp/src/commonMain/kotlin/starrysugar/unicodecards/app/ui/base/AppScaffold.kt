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
package starrysugar.unicodecards.app.ui.base

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import starrysugar.unicodecards.Res
import starrysugar.unicodecards.back
import starrysugar.unicodecards.ic_baseline_arrow_back_24

/**
 * App base scaffold!
 *
 * @author StarrySugar61
 * @create 2024/6/15
 */
@Composable
fun <VM : BaseViewModel> AppScaffold(
    modifier: Modifier = Modifier,
    viewModel: VM,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit,
) {
    if (viewModel.isLoading) {
        AppLoadingDialog()
    }
    Scaffold(
        modifier = modifier,
        topBar = topBar,
        bottomBar = bottomBar,
        content = content,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavBackTopBar(
    title: @Composable () -> Unit,
    onBack: () -> Unit,
) = TopAppBar(
    title = title,
    navigationIcon = {
        IconButton(
            onClick = onBack,
        ) {
            Icon(
                painter = painterResource(
                    resource = Res.drawable.ic_baseline_arrow_back_24,
                ),
                contentDescription = stringResource(
                    resource = Res.string.back,
                ),
            )
        }
    },
)

/**
 * Loading dialog!
 */
@Composable
private fun AppLoadingDialog() {
    Dialog(
        onDismissRequest = {}
    ) {
        CircularProgressIndicator()
    }
}

