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

import androidx.compose.runtime.Composable
import kotlinx.datetime.Clock
import org.jetbrains.compose.resources.stringResource
import starrysugar.unicodecards.Res
import starrysugar.unicodecards.time_dh
import starrysugar.unicodecards.time_hm
import starrysugar.unicodecards.time_ms
import starrysugar.unicodecards.time_s

/**
 * @author StarrySugar61
 * @create 2024/6/22
 */
object TimeUtils {

    fun currentTimeMillis(): Long = Clock.System.now().toEpochMilliseconds()

    @Composable
    fun millisToString(
        millis: Long,
    ): String {
        val day = millis / DAY
        val hour = (millis % DAY) / HOUR
        if (day > 0) {
            return stringResource(
                resource = Res.string.time_dh,
                day,
                hour
            )
        }
        val minute = (millis % HOUR) / MINUTE
        if (hour > 0) {
            return stringResource(
                resource = Res.string.time_hm,
                hour,
                minute,
            )
        }
        val second = (millis % MINUTE) / SECOND
        if (minute > 0) {
            return stringResource(
                resource = Res.string.time_ms,
                minute,
                second,
            )
        }
        return stringResource(
            resource = Res.string.time_s,
            second,
        )
    }

    private const val SECOND = 1000L
    private const val MINUTE = 60L * SECOND
    private const val HOUR = 60L * MINUTE
    private const val DAY = 24L * HOUR

}