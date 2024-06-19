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
package starrysugar.unicodecards.appdata.models.pack

import org.koin.core.component.KoinComponent

/**
 * Card pack of the game!
 *
 * @author StarrySugar61
 * @create 2024/6/19
 */
interface CardPack : KoinComponent {
    /**
     * Pack name!
     */
    val name: String

    /**
     * The char shows on the pack!
     */
    val sampleCodePoint: Int

    /**
     * Collected a card from this pack!
     */
    fun collectCards(
        count: Int
    ): List<CardResult>

    data class CardResult(
        val codePoint: Int,
        val isNew: Boolean,
    )
}
