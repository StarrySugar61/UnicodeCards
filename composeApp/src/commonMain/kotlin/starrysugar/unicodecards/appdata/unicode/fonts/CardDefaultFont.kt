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

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.FontResource
import starrysugar.unicodecards.AlibabaPuHuiTi_3_55_RegularL3
import starrysugar.unicodecards.BabelStoneHan
import starrysugar.unicodecards.NotoSansCJKtc_Regular
import starrysugar.unicodecards.NotoSansDuployan_Regular
import starrysugar.unicodecards.NotoSansPlane0_Regular
import starrysugar.unicodecards.NotoSansPlane1_Regular
import starrysugar.unicodecards.NotoSansSignWriting_Regular
import starrysugar.unicodecards.NotoZnamennyMusicalNotation_Regular
import starrysugar.unicodecards.Res
import starrysugar.unicodecards.Roboto_Regular

/**
 * Default font used for cards!
 *
 * @author StarrySugar61
 * @create 2024/6/30
 */
object CardDefaultFont {

    private val _buffer = HashMap<FontResource, FontFamily>()

    val credits = listOf(
        DefaultFontCredits(
            name = "Alibaba PuHuiTi 3.0 (阿里巴巴普惠体3.0)",
            url = "https://www.alibabafonts.com/#/font",
        ),
        DefaultFontCredits(
            name = "BabelStone Han",
            url = "https://www.babelstone.co.uk/Fonts/Han.html",
        ),
        DefaultFontCredits(
            name = "Noto",
            url = "https://fonts.google.com/noto",
        ),
        DefaultFontCredits(
            name = "Roboto",
            url = "https://fonts.google.com/specimen/Roboto",
        ),
        DefaultFontCredits(
            name = "Roboto Serif",
            url = "https://fonts.google.com/specimen/Roboto+Serif",
        ),
    )

    private fun getFontResFor(
        codePoint: Int,
    ): FontResource? = when (codePoint) {
        in 0x0..0x17e -> Res.font.Roboto_Regular
        // TODO 0x860..0x86f Syriac Supplement
        in 0x1100..0x11ff,
        in 0x2500..0x259f,
        in 0x2e80..0x2eff,
        in 0x2ff0..0x2fff,
        in 0x3000..0x4dbf,
        in 0x4e00..0x9fef,
        in 0xa960..0xa97f,
        in 0xac00..0xd7ff,
        in 0xf900..0xfaff,
        in 0xff00..0xffef -> Res.font.NotoSansCJKtc_Regular

        in 0x2f00..0x2fdf -> Res.font.AlibabaPuHuiTi_3_55_RegularL3
        in 0xfb01..0xfb04 -> Res.font.Roboto_Regular
        // TODO 0x10ec0..0x10eff Arabic Extended-C
        // TODO 0x11fc0..0x11fff Tamil Supplement
        // TODO Kana Extensions
        in 0x1bc00..0x1bcaf -> Res.font.NotoSansDuployan_Regular
        in 0x1cf00..0x1cf9f -> Res.font.NotoZnamennyMusicalNotation_Regular
        // TODO 0x1d000..0x1d3ff
        in 0x1d800..0x1daaf -> Res.font.NotoSansSignWriting_Regular
        // TODO 0x1e030..0x1e08f Cyrillic Extended-D
        // TODO 0x1f000..0x1f0ff
        // TODO 0x1f200..0x1f6ff
        // TODO 0x1f7e0..0x1f7ff
        // TODO 0x1f800..0x1fbff

        in 0x0..0xffff -> Res.font.NotoSansPlane0_Regular
        in 0x10000..0x1ffff -> Res.font.NotoSansPlane1_Regular

        0x20164, 0x20676, 0x20cd0, 0x2139a, 0x21413, 0x235cb, 0x239c7, 0x239c8, 0x23e23, 0x249db,
        0x24a7d, 0x24ac9, 0x25532, 0x25562, 0x255a8, 0x25ed7, 0x26221, 0x2648d, 0x26676, 0x2677c,
        0x26b5c, 0x26c21, 0x27ff9, 0x28408, 0x28678, 0x28695, 0x287e0, 0x28b49, 0x28c47, 0x28c4f,
        0x28c51, 0x28c54, 0x28e99, 0x29f7e, 0x29f83, 0x29f8c, 0x2a7dd, 0x2a8fb, 0x2a917, 0x2aa30,
        0x2aa36, 0x2aa58, 0x2afa2, 0x2b127, 0x2b128, 0x2b137, 0x2b138, 0x2b1ed, 0x2b300, 0x2b363,
        0x2b36f, 0x2b372, 0x2b37d, 0x2b404, 0x2b410, 0x2b413, 0x2b461, 0x2b4e7, 0x2b4ef, 0x2b4f6,
        0x2b4f9, 0x2b50d, 0x2b50e, 0x2b536, 0x2b5ae, 0x2b5af, 0x2b5b3, 0x2b5e7, 0x2b5f4, 0x2b61c,
        0x2b61d, 0x2b626, 0x2b627, 0x2b628, 0x2b62a, 0x2b62c, 0x2b695, 0x2b696, 0x2b6ad, 0x2b6ed,
        0x2b7a9, 0x2b7c5, 0x2b7e6, 0x2b7f9, 0x2b7fc, 0x2b806, 0x2b80a, 0x2b81c, 0x2b8b8, 0x2bac7,
        0x2bb5f, 0x2bb62, 0x2bb7c, 0x2bb83, 0x2bc1b, 0x2bd77, 0x2bd87, 0x2bdf7, 0x2be29, 0x2c029,
        0x2c02a, 0x2c0a9, 0x2c0ca, 0x2c1d5, 0x2c1d9, 0x2c1f9, 0x2c27c, 0x2c288, 0x2c2a4, 0x2c317,
        0x2c35b, 0x2c361, 0x2c364, 0x2c488, 0x2c494, 0x2c497, 0x2c542, 0x2c613, 0x2c618, 0x2c621,
        0x2c629, 0x2c62b, 0x2c62c, 0x2c62d, 0x2c62f, 0x2c642, 0x2c64a, 0x2c64b, 0x2c72c, 0x2c72f,
        0x2c79f, 0x2c7c1, 0x2c7fd, 0x2c8d9, 0x2c8de, 0x2c8e1, 0x2c8f3, 0x2c907, 0x2c90a, 0x2c91d,
        0x2ca02, 0x2ca0e, 0x2ca7d, 0x2caa9, 0x2cb29, 0x2cb2d, 0x2cb2e, 0x2cb31, 0x2cb38, 0x2cb39,
        0x2cb3b, 0x2cb3f, 0x2cb41, 0x2cb4a, 0x2cb4e, 0x2cb5a, 0x2cb5b, 0x2cb64, 0x2cb69, 0x2cb6c,
        0x2cb6f, 0x2cb73, 0x2cb76, 0x2cb78, 0x2cb7c, 0x2cbb1, 0x2cbbf, 0x2cbc0, 0x2cbce, 0x2cc56,
        0x2cc5f, 0x2ccf5, 0x2ccf6, 0x2ccfd, 0x2ccff, 0x2cd02, 0x2cd03, 0x2cd0a, 0x2cd8b, 0x2cd8d,
        0x2cd8f, 0x2cd90, 0x2cd9f, 0x2cda0, 0x2cda8, 0x2cdad, 0x2cdae, 0x2ccd5, 0x2ce18, 0x2ce1a,
        0x2ce23, 0x2ce26, 0x2ce2a, 0x2ce7c, 0x2ce88, 0x2ce93
        -> Res.font.NotoSansCJKtc_Regular

        in 0x20000..0x2ebef -> Res.font.AlibabaPuHuiTi_3_55_RegularL3
        in 0x2ebf0..0x2ee5f,
        in 0x2f800..0x2fa1f,
        in 0x30000..0x3ffff -> Res.font.BabelStoneHan

        else -> null
    }

    @Composable
    fun getFontFamilyFor(
        codePoint: Int,
    ): FontFamily? = getFontResFor(
        codePoint = codePoint,
    )?.let {
        _buffer[it] ?: FontFamily(
            Font(
                resource = it,
                weight = FontWeight.Normal,
                style = FontStyle.Normal,
            ),
        ).also { fontFamily ->
            _buffer[it] = fontFamily
        }
    }

    data class DefaultFontCredits(
        val name: String,
        val url: String?,
    )

}