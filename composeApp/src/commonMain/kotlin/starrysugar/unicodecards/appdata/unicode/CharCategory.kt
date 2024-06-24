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
import starrysugar.unicodecards.unicode_category_cc
import starrysugar.unicodecards.unicode_category_cf
import starrysugar.unicodecards.unicode_category_cn
import starrysugar.unicodecards.unicode_category_co
import starrysugar.unicodecards.unicode_category_cs
import starrysugar.unicodecards.unicode_category_ll
import starrysugar.unicodecards.unicode_category_lm
import starrysugar.unicodecards.unicode_category_lo
import starrysugar.unicodecards.unicode_category_lt
import starrysugar.unicodecards.unicode_category_lu
import starrysugar.unicodecards.unicode_category_mc
import starrysugar.unicodecards.unicode_category_me
import starrysugar.unicodecards.unicode_category_mn
import starrysugar.unicodecards.unicode_category_nd
import starrysugar.unicodecards.unicode_category_nl
import starrysugar.unicodecards.unicode_category_no
import starrysugar.unicodecards.unicode_category_pc
import starrysugar.unicodecards.unicode_category_pd
import starrysugar.unicodecards.unicode_category_pe
import starrysugar.unicodecards.unicode_category_pf
import starrysugar.unicodecards.unicode_category_pi
import starrysugar.unicodecards.unicode_category_po
import starrysugar.unicodecards.unicode_category_ps
import starrysugar.unicodecards.unicode_category_sc
import starrysugar.unicodecards.unicode_category_sk
import starrysugar.unicodecards.unicode_category_sm
import starrysugar.unicodecards.unicode_category_so
import starrysugar.unicodecards.unicode_category_zl
import starrysugar.unicodecards.unicode_category_zp
import starrysugar.unicodecards.unicode_category_zs

/**
 * @author StarrySugar61
 * @create 2024/6/16
 */
enum class CharCategory {
    /**
     * Letter, Uppercase
     */
    Lu,

    /**
     * Letter, Lowercase
     */
    Ll,

    /**
     * Letter, Titlecase
     */
    Lt,

    /**
     * Letter, Modifier
     */
    Lm,

    /**
     * Letter, Other
     */
    Lo,

    /**
     * Mark, Non-Spacing
     */
    Mn,

    /**
     * Mark, Spacing Combining
     */
    Mc,

    /**
     * Mark, Enclosing
     */
    Me,

    /**
     * Number, Decimal
     */
    Nd,

    /**
     * Number, Letter
     */
    Nl,

    /**
     * Number, Other
     */
    No,

    /**
     * Punctuation, Connector
     */
    Pc,

    /**
     * Punctuation, Dash
     */
    Pd,

    /**
     * Punctuation, Open
     */
    Ps,

    /**
     * Punctuation, Close
     */
    Pe,

    /**
     * Punctuation, Initial quote (may behave like Ps or Pe depending on usage)
     */
    Pi,

    /**
     * Punctuation, Final quote (may behave like Ps or Pe depending on usage)
     */
    Pf,

    /**
     * Punctuation, Other
     */
    Po,

    /**
     * Symbol, Math
     */
    Sm,

    /**
     * Symbol, Currency
     */
    Sc,

    /**
     * Symbol, Modifier
     */
    Sk,

    /**
     * Symbol, Other
     */
    So,

    /**
     * Separator, Space
     */
    Zs,

    /**
     * Separator, Line
     */
    Zl,

    /**
     * Separator, Paragraph
     */
    Zp,

    /**
     * Other, Control
     */
    Cc,

    /**
     * Other, Format
     */
    Cf,

    /**
     * Other, Surrogate
     */
    Cs,

    /**
     * Other, Private Use
     */
    Co,

    /**
     * Other, Not Assigned (no characters in the file have this property)
     */
    Cn,

    ;

    fun getInfoStringRes() = when (this) {
        Lu -> Res.string.unicode_category_lu
        Ll -> Res.string.unicode_category_ll
        Lt -> Res.string.unicode_category_lt
        Lm -> Res.string.unicode_category_lm
        Lo -> Res.string.unicode_category_lo
        Mn -> Res.string.unicode_category_mn
        Mc -> Res.string.unicode_category_mc
        Me -> Res.string.unicode_category_me
        Nd -> Res.string.unicode_category_nd
        Nl -> Res.string.unicode_category_nl
        No -> Res.string.unicode_category_no
        Pc -> Res.string.unicode_category_pc
        Pd -> Res.string.unicode_category_pd
        Ps -> Res.string.unicode_category_ps
        Pe -> Res.string.unicode_category_pe
        Pi -> Res.string.unicode_category_pi
        Pf -> Res.string.unicode_category_pf
        Po -> Res.string.unicode_category_po
        Sm -> Res.string.unicode_category_sm
        Sc -> Res.string.unicode_category_sc
        Sk -> Res.string.unicode_category_sk
        So -> Res.string.unicode_category_so
        Zs -> Res.string.unicode_category_zs
        Zl -> Res.string.unicode_category_zl
        Zp -> Res.string.unicode_category_zp
        Cc -> Res.string.unicode_category_cc
        Cf -> Res.string.unicode_category_cf
        Cs -> Res.string.unicode_category_cs
        Co -> Res.string.unicode_category_co
        Cn -> Res.string.unicode_category_cn
    }
}