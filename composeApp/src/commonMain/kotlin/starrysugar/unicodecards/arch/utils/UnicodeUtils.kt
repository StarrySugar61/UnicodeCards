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
package starrysugar.unicodecards.arch.utils

/**
 * @author StarrySugar61
 * @create 2024/6/17
 */
object UnicodeUtils {

    /**
     * Convert a code point to character!
     */
    fun charToString(
        codePoint: Int,
    ): String = if (codePoint < 0x10000) {
        codePoint.toChar().toString()
    } else {
        val surrogate = codePoint - 0x10000
        val high = (0xd800 + (surrogate shr 10)).toChar()
        val low = (0xdc00 + (surrogate and 0x3ff)).toChar()
        charArrayOf(high, low).concatToString()
    }

    /**
     * Convert a code point to UTF-16!
     */
    fun charToUtf16(
        codePoint: Int,
    ): IntArray = if (codePoint < 0x10000) {
        intArrayOf(codePoint)
    } else {
        val surrogate = codePoint - 0x10000
        val high = (0xd800 + (surrogate shr 10))
        val low = (0xdc00 + (surrogate and 0x3ff))
        intArrayOf(high, low)
    }

    /**
     * Convert a code point to UTF-8
     */
    fun charToUtf8(
        codePoint: Int,
    ): IntArray = when (codePoint) {
        in 0x0..0x7f -> intArrayOf(codePoint)
        in 0x80..0x7ff -> intArrayOf(
            0xc0 or (codePoint shr 6),
            0x80 or (codePoint and 0x3f),
        )

        in 0x800..0xffff -> intArrayOf(
            0xe0 or (codePoint shr 12),
            0x80 or ((codePoint shr 6) and 0x3f),
            0x80 or (codePoint and 0x3f),
        )

        in 0x10000..0x10ffff -> intArrayOf(
            0xf0 or (codePoint shr 18),
            0x80 or ((codePoint shr 12) and 0x3f),
            0x80 or ((codePoint shr 6) and 0x3f),
            0x80 or (codePoint and 0x3F),
        )

        else -> intArrayOf()
    }

}