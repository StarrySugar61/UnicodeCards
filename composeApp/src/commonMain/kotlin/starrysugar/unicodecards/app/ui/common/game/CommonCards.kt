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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.text.style.TextAlign
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

/**
 * Card pack!
 */
@Composable
fun UnicodeCardPack(
    modifier: Modifier = Modifier,
    sampleCodePoint: Int,
    packName: String,
    cardCount: Int,
) {
    OutlinedCard(
        modifier = modifier.size(
            width = 180.dp,
            height = 270.dp,
        ),
        shape = cardPackShape,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            repeat(20) {
                VerticalDivider()
            }
        }
        HorizontalDivider()
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(8F)
                .padding(
                    horizontal = 16.dp,
                    vertical = 8.dp,
                ),
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.TopStart),
                text = "UNICODE CARDS",
                fontSize = 12.sp,
            )
            Text(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(bottom = 32.dp),
                text = UnicodeUtils.charToString(sampleCodePoint),
                fontSize = 80.sp,
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 48.dp),
                text = packName,
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
            )
            Text(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(bottom = 11.dp),
                text = "$cardCount Cards",
                fontSize = 11.sp,
            )
            Text(
                modifier = Modifier
                    .align(Alignment.BottomStart),
                text = "Per Pack",
                fontSize = 11.sp,
            )
        }
        HorizontalDivider()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            repeat(20) {
                VerticalDivider()
            }
        }
    }
}

/**
 * Card deck!
 *
 * @param codePoint Code point show on the deck,
 *                  -1 means no cards collected in this deck,
 *                  and will show the card back instead.
 */
@Composable
fun UnicodeCardDeck(
    modifier: Modifier = Modifier,
    codePoint: Int,
) {
    Box(
        modifier = modifier,
    ) {
        CardBorder(
            modifier = Modifier
                .align(Alignment.Center)
                .rotate(25F),
            content = {}
        )
        CardBorder(
            modifier = Modifier
                .align(Alignment.Center)
                .rotate(10F),
            content = {}
        )
        if (codePoint == -1) {
            UnicodeCardBack(
                modifier = Modifier
                    .align(Alignment.Center)
                    .rotate(-5F),
            )
        } else {
            UnicodeCard(
                modifier = Modifier
                    .align(Alignment.Center)
                    .rotate(-5F),
                codePoint = codePoint,
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

private val cardPackShape = GenericShape { size, layoutDirection ->
    val sideHeight = size.height / 10
    val curve = size.width / 20
    moveTo(0F, 0F)
    lineTo(size.width, 0F)
    lineTo(size.width, sideHeight)
    lineTo(size.width - curve, sideHeight + curve)
    lineTo(size.width - curve, size.height - sideHeight - curve)
    lineTo(size.width, size.height - sideHeight)
    lineTo(size.width, size.height)
    lineTo(0F, size.height)
    lineTo(0F, size.height - sideHeight)
    lineTo(curve, size.height - sideHeight - curve)
    lineTo(curve, sideHeight + curve)
    lineTo(0F, sideHeight)
    close()
    op(
        this,
        Path().apply {
            addOval(
                Rect(
                    size.width / 2 - sideHeight / 4,
                    sideHeight / 4,
                    size.width / 2 + sideHeight / 4,
                    sideHeight * 3 / 4,
                )
            )
        },
        PathOperation.Xor,
    )
}
