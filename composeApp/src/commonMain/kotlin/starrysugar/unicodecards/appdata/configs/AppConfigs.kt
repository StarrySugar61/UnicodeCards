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
package starrysugar.unicodecards.appdata.configs

import starrysugar.unicodecards.arch.utils.TimeUtils

/**
 * Configs!
 *
 * @author StarrySugar61
 * @create 2024/6/22
 */
object AppConfigs {

    const val WELCOME_PACK_CARDS = 25

    const val FREE_PACK_CARDS = 10

    /**
     * Free pack refill time!
     */
    const val FREE_PACK_REFILL_TIME = 2 * 60 * 60 * 1000L

    /**
     * Max count of free packs can be held at market!
     */
    const val FREE_PACK_MAXIMUM_STORAGE = 10

    /**
     * Free packs available at market when first enter!
     */
    const val FREE_PACK_INITIAL_STORAGE = 3

    const val GD_UNLOCK_EXCHANGE_HUB = 3

    const val GD_UNLOCK_TIME_CHAMBER = 6

    object Utils {

        fun freePacksAvailable(
            freePackFullTime: Long
        ): Int {
            val now = TimeUtils.currentTimeMillis()
            return (FREE_PACK_MAXIMUM_STORAGE -
                    ((freePackFullTime - now) / FREE_PACK_REFILL_TIME).toInt() - 1)
                .coerceIn(0..FREE_PACK_MAXIMUM_STORAGE)
        }

        fun userGlyphDimensional(
            cardsObtained: Long,
        ) = (cardsObtained / 9).toString(radix = 4).length

        fun cardsRequiredForGlyphDimensional(
            level: Int,
        ) = if (level == 1) {
            0
        } else {
            9 * (4 shl level)
        }

        fun userCurrentGlyphDimensionalExp(
            level: Int,
            cardsObtained: Long,
        ) = cardsObtained - cardsRequiredForGlyphDimensional(level)

    }

}