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
package starrysugar.unicodecards.app.ui.common.game

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import starrysugar.unicodecards.arch.utils.UnicodeUtils

/**
 * @author StarrySugar61
 * @create 2024/6/17
 */

/**
 * A common unicode card!
 */
@Composable
fun UnicodeCard(
    modifier: Modifier = Modifier,
    codePoint: Int,
) {
    CardBorder(
        modifier = modifier
    ) {
        CardContent(codePoint)
    }
}

/**
 * A common unicode card back!
 */
@Composable
fun UnicodeCardBack(
    modifier: Modifier = Modifier,
) {
    CardBorder(
        modifier = modifier
    ) {
        CardBackContent()
    }
}

/**
 * Placeholder for cards not collected yet!
 */
@Composable
fun UnicodeCardPlaceholder(
    modifier: Modifier = Modifier,
    codePoint: Int,
) {
    OutlinedCard(
        modifier = modifier.size(
            width = 150.dp,
            height = 200.dp,
        ),
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = codePoint.toString(radix = 16).uppercase(),
                fontSize = 32.sp,
            )
        }
    }
}

@Composable
private fun CardBorder(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    OutlinedCard(
        modifier = modifier.size(
            width = 150.dp,
            height = 200.dp,
        ),
    ) {
        OutlinedCard(
            modifier = Modifier.fillMaxSize().padding(all = 4.dp),
            content = content,
        )
    }
}

// TODO Add font
@Composable
private fun CardContent(
    codePoint: Int,
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = UnicodeUtils.charToString(codePoint),
            fontSize = 80.sp,
        )
        Text(
            modifier = Modifier.align(Alignment.TopStart).padding(all = 8.dp),
            text = codePoint.toString(radix = 16).uppercase(),
            fontSize = 16.sp,
        )
        Text(
            modifier = Modifier.align(Alignment.BottomEnd).rotate(180F).padding(all = 8.dp),
            text = codePoint.toString(radix = 16).uppercase(),
            fontSize = 16.sp,
        )
    }
}

@Composable
private fun CardBackContent() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text(
                text = "UNICODE",
                fontSize = 24.sp,
            )
            Text(
                text = "CARDS",
                fontSize = 24.sp,
            )
        }
    }
}
