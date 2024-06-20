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

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import starrysugar.unicodecards.appdata.unicode.CharCategory

/**
 * Examples for Unicode cards!
 *
 * @author StarrySugar61
 * @create 2024/6/17
 */

@Composable
@Preview
private fun UnicodeCardExample1() {
    UnicodeCard(
        codePoint = 0x21,
        category = CharCategory.Po,
    )
}

@Composable
@Preview
private fun UnicodeCardExample2() {
    UnicodeCard(
        codePoint = 0x10000,
        category = CharCategory.Lo,
    )
}

@Composable
@Preview
private fun UnicodeCardExample3() {
    UnicodeCard(
        codePoint = 0x1f600,
        category = CharCategory.So,
    )
}

@Composable
@Preview
private fun UnicodeCardExample4() {
    UnicodeCard(
        codePoint = 0xfeff,
        category = CharCategory.Cf,
        valueCover = "<ZWNBSP>",
    )
}

@Composable
@Preview
private fun UnicodeCardExample5() {
    UnicodeCard(
        codePoint = 0x200c,
        category = CharCategory.Cf,
        valueCover = "<ZWNC>",
    )
}

@Composable
@Preview
private fun UnicodeCardBackPreview() {
    UnicodeCardBack()
}

@Composable
@Preview
private fun UnicodeCardPlaceholderExample1() {
    UnicodeCardPlaceholder(
        modifier = Modifier.alpha(0.4F),
        codePoint = 0x21,
    )
}

@Composable
@Preview
private fun UnicodeCardPlaceholderExample2() {
    UnicodeCardPlaceholder(
        modifier = Modifier.alpha(0.4F),
        codePoint = 0x1f600,
    )
}

@Composable
@Preview
private fun UnicodeCardPackExample1() {
    UnicodeCardPack(
        sampleCodePoint = 0x41,
        packName = "ASCII Pack",
        cardCount = 10,
    )
}

@Composable
@Preview
private fun UnicodeCardPackExample2() {
    UnicodeCardPack(
        sampleCodePoint = 0x10000,
        packName = "Linear B Syllabary Pack",
        cardCount = 10,
    )
}

@Composable
@Preview
private fun UnicodeCardPackExample3() {
    UnicodeCardPack(
        sampleCodePoint = 0x1f604,
        packName = "Emoji Pack",
        cardCount = 10,
    )
}

@Preview
@Composable
private fun UnicodeCardPackEndPartExample() {
    UnicodeCardPackEndPart()
}

@Composable
@Preview
private fun UnicodeCardDeckExample1() {
    UnicodeCardDeck(
        modifier = Modifier.padding(
            horizontal = 32.dp,
            vertical = 20.dp,
        ),
        codePoint = 0x21,
        category = CharCategory.Po,
    )
}
