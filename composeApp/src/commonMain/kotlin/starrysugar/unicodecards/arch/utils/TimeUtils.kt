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

import kotlinx.datetime.Clock

/**
 * @author StarrySugar61
 * @create 2024/6/22
 */
object TimeUtils {

    fun currentTimeMillis(): Long = Clock.System.now().toEpochMilliseconds()

}