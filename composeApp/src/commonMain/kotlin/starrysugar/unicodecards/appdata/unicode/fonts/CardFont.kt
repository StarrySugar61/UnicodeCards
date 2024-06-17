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
package starrysugar.unicodecards.appdata.unicode.fonts

import org.jetbrains.compose.resources.FontResource

/**
 * Unicode card font (skin)!
 *
 * @author StarrySugar61
 * @create 2024/6/17
 *
 * @param name Name of the font
 * @param author Author of the font
 * @param link Link of the font
 * @param version Version of the font
 * @param subFonts Sub fonts
 * @param bitPrice Bits cost to purchase in game
 */
data class CardFont(
    val name: String,
    val author: String,
    val link: String,
    val version: String,
    val subFonts: List<SubFont>,
    val bitPrice: Int,
) {

    /**
     * @param ranges Codepoints available for the sub font
     * @param res Font resource
     */
    data class SubFont(
        val ranges: List<IntRange>,
        val res: FontResource,
    )

}

/**
 * Map of available fonts in this game!
 * Only accept regular fonts
 */
val availableFonts: Map<Int, CardFont> = mapOf(
    0 to CardFont(
        name = "Noto",
        author = "Google",
        link = "https://fonts.google.com/noto",
        version = "24.6.1",
        subFonts = listOf(

        ),
        bitPrice = 0,
    )
)