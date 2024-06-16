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
package starrysugar.unicodecards.appdata.unicode

/**
 * @author StarrySugar61
 * @create 2024/6/16
 */
enum class CharBidiClass {
    /**
     * Left-to-Right
     */
    L,

    /**
     * Left-to-Right Embedding
     */
    LRE,

    /**
     * Left-to-Right Override
     */
    LRO,

    /**
     * Right-to-Left
     */
    R,

    /**
     * Right-to-Left Arabic
     */
    AL,

    /**
     * Right-to-Left Embedding
     */
    RLE,

    /**
     * Right-to-Left Override
     */
    RLO,

    /**
     * Pop Directional Format
     */
    PDF,

    /**
     * European Number
     */
    EN,

    /**
     * European Number Separator
     */
    ES,

    /**
     * European Number Terminator
     */
    ET,

    /**
     * Arabic Number
     */
    AN,

    /**
     * Common Number Separator
     */
    CS,

    /**
     * Non-Spacing Mark
     */
    NSM,

    /**
     * Boundary Neutral
     */
    BN,

    /**
     * Paragraph Separator
     */
    B,

    /**
     * Segment Separator
     */
    S,

    /**
     * Whitespace
     */
    WS,

    /**
     * Other Neutrals
     */
    ON,

    /**
     * Left-to-Right Isolate
     */
    LRI,

    /**
     * Right-to-Left Isolate
     */
    RLI,

    /**
     * First-Strong Isolate
     */
    FSI,

    /**
     * Pop Directional Isolate
     */
    PDI,

}