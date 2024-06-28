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
package starrysugar.unicodecards.appdata.di

import org.koin.dsl.module
import starrysugar.unicodecards.appdata.database.AppDriverFactory
import starrysugar.unicodecards.appdata.database.Database
import starrysugar.unicodecards.appdata.database.createDatabase
import starrysugar.unicodecards.appdata.datastore.AppDataStore

/**
 * @author StarrySugar61
 * @create 2024/6/17
 */
val koinModules = module {
    // Database!
    single { AppDriverFactory() }
    single { get<AppDriverFactory>().createDriver() }
    single { createDatabase(get()) }
    single { get<Database>().fakeExchangeRequestsQueries }
    single { get<Database>().fakeUsersQueries }
    single { get<Database>().unicodeBlocksQueries }
    single { get<Database>().unicodeDataQueries }
    single { get<Database>().unicodeIso15924Queries }
    single { get<Database>().unicodeScriptsQueries }
    single { get<Database>().userCardFontsQueries }
    single { get<Database>().userCardsQueries }

    // DataStore!
    single { AppDataStore() }
    single { get<AppDataStore>().dataStore }
}