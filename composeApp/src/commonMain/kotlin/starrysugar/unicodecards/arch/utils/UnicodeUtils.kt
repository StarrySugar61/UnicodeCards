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

}