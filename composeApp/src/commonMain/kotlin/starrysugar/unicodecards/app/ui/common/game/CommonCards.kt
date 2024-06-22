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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material3.Badge
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import starrysugar.unicodecards.app.ui.theme.AppColors
import starrysugar.unicodecards.appdata.unicode.CharCategory
import starrysugar.unicodecards.arch.utils.UnicodeUtils

/**
 * @author StarrySugar61
 * @create 2024/6/17
 */

private const val CARD_WIDTH = 150F
private const val CARD_HEIGHT = 200F
private const val PACK_WIDTH = 190F
private const val PACK_HEIGHT = 270F

/**
 * A common unicode card!
 *
 * @param valueCover When this value exists,
 *                   it will replace the character display on the card with alpha of 0.4!
 *                   see example 4 and 5.
 * @param count Cards owned for this code point! Shows "NEW!" when value == -1
 */
@Composable
fun UnicodeCard(
    modifier: Modifier = Modifier,
    scale: Float = 1F,
    codePoint: Int,
    category: CharCategory? = null,
    valueCover: String? = null,
    count: Int = 0,
) {
    CardBorder(
        modifier = modifier,
        scale = scale,
        count = count,
    ) {
        CardContent(
            codePoint = codePoint,
            category = category,
            valueCover = valueCover,
        )
    }
}

/**
 * A common unicode card back!
 */
@Composable
fun UnicodeCardBack(
    modifier: Modifier = Modifier,
    scale: Float = 1F,
) {
    CardBorder(
        modifier = modifier,
        scale = scale,
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
    scale: Float = 1F,
    codePoint: Int,
) {
    Box(
        modifier = modifier.size(
            width = (CARD_WIDTH * scale).dp,
            height = (CARD_HEIGHT * scale).dp,
        ),
    ) {
        OutlinedCard(
            modifier = Modifier
                .scale(scale)
                .requiredSize(
                    width = CARD_WIDTH.dp,
                    height = CARD_HEIGHT.dp,
                ),
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = codePoint.toString(radix = 16).uppercase(),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold,
                )
            }
        }
    }
}

/**
 * Card pack!
 */
@Composable
fun UnicodeCardPack(
    modifier: Modifier = Modifier,
    scale: Float = 1F,
    isOpened: Boolean = false,
    sampleCodePoint: Int,
    packName: String,
    cardCount: Int,
) {
    Box(
        modifier = modifier.size(
            width = (PACK_WIDTH * scale).dp,
            height = (PACK_HEIGHT * scale).dp,
        ),
    ) {
        OutlinedCard(
            modifier = modifier
                .scale(scale)
                .requiredSize(
                    width = 190.dp,
                    height = 270.dp,
                ),
            shape = if (isOpened) {
                openCardPackStartPartShape
            } else {
                cardPackShape
            },
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
}

@Composable
fun UnicodeCardPackEndPart(
    modifier: Modifier = Modifier
) {
    OutlinedCard(
        modifier = modifier.requiredSize(
            width = 30.dp,
            height = 270.dp,
        ),
        shape = openCardPackEndPartShape,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            repeat(4) {
                VerticalDivider()
            }
        }
        HorizontalDivider()
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .weight(8F)
                .padding(
                    horizontal = 16.dp,
                    vertical = 8.dp,
                ),
        )
        HorizontalDivider()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            repeat(4) {
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
    category: CharCategory? = null,
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
                category = category,
            )
        }
    }
}

/**
 * Display color for char category!
 */
fun CharCategory.getDisplayColor(): Color = when (this) {
    CharCategory.Lu,
    CharCategory.Ll,
    CharCategory.Lt,
    CharCategory.Lm -> AppColors.Green500

    CharCategory.Lo -> AppColors.Lime500
    CharCategory.Mn,
    CharCategory.Mc,
    CharCategory.Me -> AppColors.Cyan500

    CharCategory.Nd,
    CharCategory.Nl,
    CharCategory.No -> AppColors.Red500

    CharCategory.Pc,
    CharCategory.Pd,
    CharCategory.Ps,
    CharCategory.Pe,
    CharCategory.Pi,
    CharCategory.Pf,
    CharCategory.Po -> AppColors.Purple500

    CharCategory.Sm,
    CharCategory.Sc,
    CharCategory.Sk,
    CharCategory.So -> AppColors.Orange500

    CharCategory.Zs,
    CharCategory.Zl,
    CharCategory.Zp -> AppColors.Blue500

    CharCategory.Cc,
    CharCategory.Cf,
    CharCategory.Cs,
    CharCategory.Co -> AppColors.Yellow500

    CharCategory.Cn -> AppColors.Gray500
}

@Composable
private fun CardBorder(
    modifier: Modifier = Modifier,
    scale: Float = 1F,
    count: Int = 0,
    content: @Composable ColumnScope.() -> Unit,
) {
    Box(
        modifier = modifier.size(
            width = (CARD_WIDTH * scale).dp,
            height = (CARD_HEIGHT * scale).dp,
        ),
    ) {
        OutlinedCard(
            modifier = Modifier
                .scale(scale)
                .requiredSize(
                    width = CARD_WIDTH.dp,
                    height = CARD_HEIGHT.dp,
                ),
        ) {
            OutlinedCard(
                modifier = Modifier.fillMaxSize().padding(all = 4.dp),
                content = content,
            )
        }
        if (count == -1) {
            Badge(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(
                        bottom = 6.dp,
                    )
            ) {
                Text(
                    text = "NEW!",
                )
            }
        } else if (count > 1) {
            Badge(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(
                        bottom = 6.dp,
                    )
            ) {
                Text(
                    text = "×$count",
                )
            }
        }
    }
}

// TODO Add font
@Composable
private fun CardContent(
    codePoint: Int,
    category: CharCategory? = null,
    valueCover: String? = null,
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (valueCover == null) {
            val char = UnicodeUtils.charToString(codePoint)
            Text(
                modifier = Modifier
                    .align(Alignment.Center),
                text = if (category == CharCategory.Mn) {
                    "◌$char"
                } else {
                    char
                },
                fontSize = 80.sp,
                textAlign = TextAlign.Center,
            )
        } else {
            Text(
                modifier = Modifier
                    .align(Alignment.Center)
                    .alpha(0.4F)
                    .padding(horizontal = 4.dp),
                text = valueCover,
                fontSize = when (valueCover.length) {
                    5 -> 35.sp
                    6 -> 30.sp
                    else -> 40.sp
                },
                textAlign = TextAlign.Center,
            )
        }
        Text(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(all = 8.dp)
                .drawBehind {
                    // Custom underline!
                    // https://stackoverflow.com/a/75647847
                    if (category != null) {
                        val strokeWidthPx = 2.dp.toPx()
                        val verticalOffset = size.height
                        drawLine(
                            color = category.getDisplayColor(),
                            strokeWidth = strokeWidthPx,
                            start = Offset(0f, verticalOffset),
                            end = Offset(size.width, verticalOffset)
                        )
                    }
                },
            text = codePoint.toString(radix = 16).uppercase(),
            fontSize = 16.sp,
        )
        Text(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(all = 8.dp)
                .drawBehind {
                    // Custom underline!
                    // https://stackoverflow.com/a/75647847
                    if (category != null) {
                        val strokeWidthPx = 2.dp.toPx()
                        val verticalOffset = 0f
                        drawLine(
                            color = category.getDisplayColor(),
                            strokeWidth = strokeWidthPx,
                            start = Offset(0f, verticalOffset),
                            end = Offset(size.width, verticalOffset)
                        )
                    }
                }
                .rotate(180F),
            text = codePoint.toString(radix = 16).uppercase(),
            fontSize = 16.sp,
        )
//        color?.let {
//            Surface(
//                modifier = Modifier
//                    .align(Alignment.TopEnd)
//                    .padding(all = 8.dp)
//                    .size(
//                        width = 4.dp,
//                        height = 16.dp,
//                    ),
//                color = color,
//                content = {},
//            )
//            Surface(
//                modifier = Modifier
//                    .align(Alignment.BottomStart)
//                    .padding(all = 8.dp)
//                    .size(
//                        width = 4.dp,
//                        height = 16.dp,
//                    ),
//                color = color,
//                content = {},
//            )
//        }
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

private val cardPackShape = GenericShape { size, _ ->
    val start = 0f
    val end = size.width
    val sideHeight = size.height / 10
    val curve = size.width / 20
    moveTo(start, 0F)
    lineTo(end, 0F)
    lineTo(end, sideHeight)
    lineTo(size.width - curve, sideHeight + curve)
    lineTo(size.width - curve, size.height - sideHeight - curve)
    lineTo(end, size.height - sideHeight)
    lineTo(end, size.height)
    lineTo(start, size.height)
    lineTo(start, size.height - sideHeight)
    lineTo(curve, size.height - sideHeight - curve)
    lineTo(curve, sideHeight + curve)
    lineTo(start, sideHeight)
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

private val openCardPackStartPartShape = GenericShape { size, _ ->
    val start = 0f
    val end = size.width
    val sideHeight = size.height / 10
    val curve = size.width / 20
    val opened = curve * 3
    moveTo(start, 0F)
    lineTo(end - opened, 0F)
    lineTo(end - opened, size.height)
    lineTo(start, size.height)
    lineTo(start, size.height - sideHeight)
    lineTo(curve, size.height - sideHeight - curve)
    lineTo(curve, sideHeight + curve)
    lineTo(start, sideHeight)
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

private val openCardPackEndPartShape = GenericShape { size, _ ->
    val start = 0f
    val end = size.width
    val sideHeight = size.height / 10
    val curve = size.width / 3
    moveTo(start, 0f)
    lineTo(end, 0f)
    lineTo(end, sideHeight)
    lineTo(end - curve, sideHeight + curve)
    lineTo(end - curve, size.height - sideHeight - curve)
    lineTo(end, size.height - sideHeight)
    lineTo(end, size.height)
    lineTo(start, size.height)
    close()
}