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
}