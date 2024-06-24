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

import starrysugar.unicodecards.Res
import starrysugar.unicodecards.unicode_bidirectional_al
import starrysugar.unicodecards.unicode_bidirectional_an
import starrysugar.unicodecards.unicode_bidirectional_b
import starrysugar.unicodecards.unicode_bidirectional_bn
import starrysugar.unicodecards.unicode_bidirectional_cs
import starrysugar.unicodecards.unicode_bidirectional_en
import starrysugar.unicodecards.unicode_bidirectional_es
import starrysugar.unicodecards.unicode_bidirectional_et
import starrysugar.unicodecards.unicode_bidirectional_fsi
import starrysugar.unicodecards.unicode_bidirectional_l
import starrysugar.unicodecards.unicode_bidirectional_lre
import starrysugar.unicodecards.unicode_bidirectional_lri
import starrysugar.unicodecards.unicode_bidirectional_lro
import starrysugar.unicodecards.unicode_bidirectional_nsm
import starrysugar.unicodecards.unicode_bidirectional_on
import starrysugar.unicodecards.unicode_bidirectional_pdf
import starrysugar.unicodecards.unicode_bidirectional_pdi
import starrysugar.unicodecards.unicode_bidirectional_r
import starrysugar.unicodecards.unicode_bidirectional_rle
import starrysugar.unicodecards.unicode_bidirectional_rli
import starrysugar.unicodecards.unicode_bidirectional_rlo
import starrysugar.unicodecards.unicode_bidirectional_s
import starrysugar.unicodecards.unicode_bidirectional_ws

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

    ;

    fun getInfoStringRes() = when (this) {
        L -> Res.string.unicode_bidirectional_l
        LRE -> Res.string.unicode_bidirectional_lre
        LRO -> Res.string.unicode_bidirectional_lro
        R -> Res.string.unicode_bidirectional_r
        AL -> Res.string.unicode_bidirectional_al
        RLE -> Res.string.unicode_bidirectional_rle
        RLO -> Res.string.unicode_bidirectional_rlo
        PDF -> Res.string.unicode_bidirectional_pdf
        EN -> Res.string.unicode_bidirectional_en
        ES -> Res.string.unicode_bidirectional_es
        ET -> Res.string.unicode_bidirectional_et
        AN -> Res.string.unicode_bidirectional_an
        CS -> Res.string.unicode_bidirectional_cs
        NSM -> Res.string.unicode_bidirectional_nsm
        BN -> Res.string.unicode_bidirectional_bn
        B -> Res.string.unicode_bidirectional_b
        S -> Res.string.unicode_bidirectional_s
        WS -> Res.string.unicode_bidirectional_ws
        ON -> Res.string.unicode_bidirectional_on
        LRI -> Res.string.unicode_bidirectional_lri
        RLI -> Res.string.unicode_bidirectional_rli
        FSI -> Res.string.unicode_bidirectional_fsi
        PDI -> Res.string.unicode_bidirectional_pdi
    }

}