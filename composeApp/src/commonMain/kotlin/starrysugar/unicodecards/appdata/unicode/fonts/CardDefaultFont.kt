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
import starrysugar.unicodecards.AlibabaPuHuiTi_3_55_Regular
import starrysugar.unicodecards.AlibabaPuHuiTi_3_55_RegularL3
import starrysugar.unicodecards.NotoSansHistorical_Regular
import starrysugar.unicodecards.NotoSansLiving_Regular
import starrysugar.unicodecards.NotoSerifHistorical_Regular
import starrysugar.unicodecards.NotoSerifLiving_Regular
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
            name = "Noto",
            url = "https://fonts.google.com/noto",
        ),
        DefaultFontCredits(
            name = "Roboto",
            url = "https://fonts.google.com/specimen/Roboto",
        ),
    )

    private fun getFontResFor(
        codePoint: Int,
    ): FontResource? = when (codePoint) {
        in 0x0..0x17f -> Res.font.Roboto_Regular
        in 0x380..0x3ce, 0x3d1, 0x3d2, 0x3d6, in 0x400..0x513
        -> Res.font.Roboto_Regular

        in 0x180..0x7ff -> Res.font.NotoSansLiving_Regular

        in 0x800..0x85f -> Res.font.NotoSansHistorical_Regular
        // TODO 0x860..0x86f Syriac Supplement
        in 0x870..0xeff -> Res.font.NotoSansLiving_Regular
        in 0xf00..0xfff -> Res.font.NotoSerifLiving_Regular

        in 0x1000..0x10ff -> Res.font.NotoSansLiving_Regular
        // TODO 0x1100..0x11ff Hangul Jamo
        in 0x1200..0x167f -> Res.font.NotoSansLiving_Regular

        0x1e00, 0x1e01, 0x1e3e, 0x1e3f, in 0x1e80..0x1e85, in 0x1ea0..0x1ef9, 0x1f4d
        -> Res.font.Roboto_Regular

        in 0x1680..0x1fff -> Res.font.NotoSansLiving_Regular

        in 0x2000..0x27ff -> Res.font.NotoSansLiving_Regular
        // TODO 0x2800..0x28ff Braille Patterns
        in 0x2900..0x2bff -> Res.font.NotoSansLiving_Regular
        in 0x2c00..0x2c5f -> Res.font.NotoSansHistorical_Regular
        in 0x2c60..0x2e7f -> Res.font.NotoSansLiving_Regular
        // TODO 0x2e80..0x2eff CJK Radicals Supplement
        in 0x2f00..0x2fdf -> Res.font.AlibabaPuHuiTi_3_55_RegularL3
        in 0x2ff0..0x2fff -> Res.font.AlibabaPuHuiTi_3_55_Regular

        // TODO 0x3000..0x33ff
        in 0x3400..0x4dbf -> Res.font.AlibabaPuHuiTi_3_55_Regular

        in 0x4e00..0x9fef -> Res.font.AlibabaPuHuiTi_3_55_Regular

        in 0xa000..0xa83f -> Res.font.NotoSansLiving_Regular
        in 0xa840..0xa87f -> Res.font.NotoSansHistorical_Regular
        in 0xa880..0xa95f -> Res.font.NotoSansLiving_Regular
        // TODO 0xa960..0xa97f Hangul Jamo Extended-A
        in 0xa980..0xabff -> Res.font.NotoSansLiving_Regular
        // TODO 0xac00..0xd7ff Hangul Syllables & Hangul Jamo Extended-B

        in 0xf900..0xfaff -> Res.font.AlibabaPuHuiTi_3_55_Regular
        in 0xfb01..0xfb04 -> Res.font.Roboto_Regular
        in 0xfb00..0xfdff -> Res.font.NotoSansLiving_Regular
        // TODO 0xfe00..0xfe6f
        in 0xfe70..0xfeff -> Res.font.NotoSansLiving_Regular
        // TODO 0xff00..0xfffd


        in 0x10000..0x1013f -> Res.font.NotoSansHistorical_Regular
        // TODO 0x10140..0x1027f
        in 0x10280..0x1047f -> Res.font.NotoSansHistorical_Regular
        in 0x10480..0x104ff -> Res.font.NotoSansLiving_Regular
        in 0x10500..0x105bf,
        in 0x10600..0x107bf,
        in 0x10800..0x108af,
        in 0x108e0..0x1093f,
        in 0x10980..0x10a9f,
        in 0x10ac0..0x10baf,
        in 0x10c00..0x10c4f,
        in 0x10c80..0x10cff -> Res.font.NotoSansHistorical_Regular

        in 0x10d00..0x10d3f -> Res.font.NotoSansLiving_Regular
        // TODO 0x10e60..0x10e7f Rumi Numeral Symbols
        in 0x10e80..0x10ebf -> Res.font.NotoSerifHistorical_Regular
        // TODO 0x10ec0..0x10eff Arabic Extended-C
        in 0x10f00..0x10f6f -> Res.font.NotoSansHistorical_Regular
        in 0x10f70..0x10faf -> Res.font.NotoSerifHistorical_Regular
        in 0x10fb0..0x10fff -> Res.font.NotoSansHistorical_Regular

        in 0x11000..0x110cf -> Res.font.NotoSansHistorical_Regular
        in 0x110d0..0x1114f -> Res.font.NotoSansLiving_Regular
        in 0x11150..0x1117f,
        in 0x11180..0x111ff -> Res.font.NotoSansHistorical_Regular

        in 0x11200..0x1124f,
        in 0x11280..0x112af -> Res.font.NotoSansLiving_Regular

        in 0x112b0..0x1137f -> Res.font.NotoSansHistorical_Regular
        in 0x11400..0x1147f -> Res.font.NotoSansLiving_Regular
        in 0x11480..0x114df -> Res.font.NotoSansHistorical_Regular
        in 0x11580..0x115ff,
        in 0x11600..0x116cf -> Res.font.NotoSansLiving_Regular

        in 0x11700..0x1174f -> Res.font.NotoSerifLiving_Regular
        in 0x11800..0x1184f -> Res.font.NotoSerifHistorical_Regular
        in 0x118a0..0x118ff -> Res.font.NotoSansLiving_Regular
        in 0x11900..0x1195f -> Res.font.NotoSerifHistorical_Regular
        in 0x119a0..0x119ff -> Res.font.NotoSansHistorical_Regular
        in 0x11a00..0x11a4f -> Res.font.NotoSansLiving_Regular
        in 0x11a50..0x11aaf -> Res.font.NotoSansHistorical_Regular
        in 0x11ab0..0x11b5f -> Res.font.NotoSansLiving_Regular
        in 0x11c00..0x11cbf -> Res.font.NotoSansHistorical_Regular
        in 0x11d00..0x11daf -> Res.font.NotoSansLiving_Regular
        in 0x11ee0..0x11eff -> Res.font.NotoSerifHistorical_Regular
        in 0x11f00..0x11f5f -> Res.font.NotoSansHistorical_Regular
        in 0x11fb0..0x11fbf -> Res.font.NotoSansLiving_Regular
        // TODO 0x11fc0..0x11fff Tamil Supplement

        in 0x12000..0x1254f,
        in 0x12f90..0x12fff -> Res.font.NotoSansHistorical_Regular

        in 0x13000..0x1345f,
        in 0x14400..0x1467f -> Res.font.NotoSansHistorical_Regular

        in 0x16800..0x16b8f,
        in 0x16e40..0x16e9f,
        in 0x16f00..0x16f9f -> Res.font.NotoSansLiving_Regular

        0x16fe0, 0x16fe4 -> Res.font.NotoSerifHistorical_Regular
        0x16fe1 -> Res.font.NotoSansHistorical_Regular

        in 0x17000..0x18d7f -> Res.font.NotoSerifHistorical_Regular

        // TODO Kana Extensions

        in 0x1b170..0x1b2ff -> Res.font.NotoSansHistorical_Regular

        // TODO 0x1bc00..0x1bcaf Duployan
        // TODO 0x1cf00..0x1cf9f Znamenny Musical Notation
        // TODO 0x1d000..0x1d3ff

        in 0x1d400..0x1d7ff -> Res.font.NotoSansLiving_Regular

        // TODO 0x1d800..0x1daaf Sutton SignWriting

        in 0x1df00..0x1dfff -> Res.font.NotoSansLiving_Regular

        in 0x1e000..0x1e02f -> Res.font.NotoSansHistorical_Regular
        // TODO 0x1e030..0x1e08f Cyrillic Extended-D
        in 0x1e100..0x1e14f,
        in 0x1e290..0x1e2bf -> Res.font.NotoSerifLiving_Regular

        in 0x1e2c0..0x1e2ff,
        in 0x1e4d0..0x1e4df,
        in 0x1e7e0..0x1e7ff,
        in 0x1e800..0x1e8df,
        in 0x1e900..0x1e95f -> Res.font.NotoSansLiving_Regular

        in 0x1ec70..0x1ecbf -> Res.font.NotoSansHistorical_Regular
        in 0x1ed00..0x1ed4f -> Res.font.NotoSerifHistorical_Regular
        in 0x1ee00..0x1eeff -> Res.font.NotoSansLiving_Regular

        // TODO 0x1f000..0x1f0ff
        in 0x1f100..0x1f1ff -> Res.font.NotoSansLiving_Regular
        // TODO 0x1f200..0x1f6ff
        in 0x1f700..0x1f7df -> Res.font.NotoSansLiving_Regular
        // TODO 0x1f7e0..0x1f7ff
        // TODO 0x1f800..0x1fbff

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
        -> Res.font.AlibabaPuHuiTi_3_55_Regular

        in 0x20000..0x2ffff -> Res.font.AlibabaPuHuiTi_3_55_RegularL3
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