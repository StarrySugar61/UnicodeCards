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
package starrysugar.unicodecards.appdata.database

import app.cash.sqldelight.EnumColumnAdapter
import app.cash.sqldelight.db.SqlDriver
import starrysugar.unicodecards.appdata.database.table.Uc_unicode_data

/**
 * @author StarrySugar61
 * @create 2024/6/16
 */
expect class DriverFactory {
    fun createDriver(): SqlDriver
}

internal expect val driverFactory: DriverFactory

fun createDatabase(): Database {
    val driver = driverFactory.createDriver()
    val database = Database(
        driver = driver,
        uc_unicode_dataAdapter = Uc_unicode_data.Adapter(
            categoryAdapter = EnumColumnAdapter(),
            bidi_classAdapter = EnumColumnAdapter()
        )
    )
    return database
}