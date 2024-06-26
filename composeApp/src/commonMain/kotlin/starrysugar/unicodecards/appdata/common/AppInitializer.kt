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
package starrysugar.unicodecards.appdata.common

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.firstOrNull
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import starrysugar.unicodecards.Res
import starrysugar.unicodecards.appdata.configs.AppConfigs
import starrysugar.unicodecards.appdata.database.Database
import starrysugar.unicodecards.appdata.datastore.AppDataStoreKeys
import starrysugar.unicodecards.appdata.unicode.CharBidiClass
import starrysugar.unicodecards.appdata.unicode.CharCategory
import starrysugar.unicodecards.arch.utils.TimeUtils

/**
 * Initialize app data to database!
 *
 * @author StarrySugar61
 * @create 2024/6/16
 */
object AppInitializer : KoinComponent {

    private const val DATA_SCRIPT_VERSION = 1
    private const val DATA_CHAR_VERSION = 1
    private const val DATA_BLOCK_VERSION = 1
    private const val DATA_USERNAME_VERSION = 1

    private val dataStore: DataStore<Preferences> by inject()

    // TODO Optimize database importing!
    @OptIn(ExperimentalResourceApi::class)
    suspend fun importAppDataTo(
        database: Database,
        onProgress: (
            currentStep: Int,
            totalSteps: Int,
            progress: Int,
            maxProgress: Int,
        ) -> Unit
    ) {
        val preferences = dataStore.data.firstOrNull()
        val currentScriptVersion = preferences?.get(AppDataStoreKeys.KEY_DATA_SCRIPT_VERSION) ?: 0
        val currentCharVersion = preferences?.get(AppDataStoreKeys.KEY_DATA_CHAR_VERSION) ?: 0
        val currentBlockVersion = preferences?.get(AppDataStoreKeys.KEY_DATA_BLOCK_VERSION) ?: 0
        val currentUsernameVersion =
            preferences?.get(AppDataStoreKeys.KEY_DATA_USERNAME_VERSION) ?: 0
        // prefs    * 1
        // script   * 2
        // data     * 2
        // block    * 1
        // username * 1
        val totalSteps = 7
        var currentStep = 1

        initPreferences(preferences)
        onProgress(currentStep, totalSteps, 1, 1)

        if (currentScriptVersion < DATA_SCRIPT_VERSION) {
            // Read iso 15924
            currentStep = 2
            val iso15924Queries = database.unicodeIso15924Queries
            iso15924Queries.clear()
            val iso15924Map = HashMap<String, Pair<Int, String>>()
            Res.readBytes("files/UCD/iso15924.txt")
                .decodeToString()
                .lineSequence()
                .filterNot {
                    it.isBlank() || it.startsWith('#')
                }
                .toList()
                .let { res ->
                    val size = res.size
                    iso15924Queries.transaction {
                        res.forEachIndexed { index, s ->
                            onProgress(currentStep, totalSteps, index, size)
                            val split = s.split(';')
                            val code = split.getOrNull(0)?.trim() ?: return@forEachIndexed
                            val id =
                                split.getOrNull(1)?.trim()?.toIntOrNull() ?: return@forEachIndexed
                            val name =
                                split.getOrNull(4)?.trim()?.ifEmpty { null }
                                    ?: return@forEachIndexed
                            iso15924Map[name] = id to code
                            iso15924Queries.insertData(id.toLong(), code, name)
                        }
                    }
                }

            // Read scripts
            currentStep = 3
            val scriptQueries = database.unicodeScriptsQueries
            scriptQueries.clear()
            var lastRangeStart = 0
            var lastRangeEnd = 0
            var lastScript = -1
            Res.readBytes("files/UCD/Scripts.txt")
                .decodeToString()
                .lineSequence()
                .filterNot {
                    it.isBlank() || it.startsWith('#')
                }
                .toList()
                .let { res ->
                    val size = res.size
                    scriptQueries.transaction {
                        res.asSequence()
                            .map {
                                val split = it.substringBefore('#')
                                    .split(';')
                                val rangeText = split.getOrNull(0)?.trim() ?: return@map null
                                val range = rangeText.split("..").let { r ->
                                    r.getOrNull(0)
                                        ?.toIntOrNull(radix = 16)
                                        ?.rangeTo(
                                            r.getOrElse(1) {
                                                r[0]
                                            }.toIntOrNull(radix = 16) ?: return@map null
                                        ) ?: return@map null
                                }
                                val id =
                                    iso15924Map[split.getOrNull(1)?.trim()]?.first
                                        ?: return@map null
                                range to id
                            }
                            .filterNotNull()
                            .sortedBy {
                                it.first.first
                            }
                            .forEachIndexed { index, pair ->
                                onProgress(currentStep, totalSteps, index, size)
                                if (
                                    pair.second != lastScript
                                    || lastRangeEnd + 1 < pair.first.first
                                ) {
                                    if (lastScript >= 0) {
                                        scriptQueries.insertScript(
                                            code_point_start = lastRangeStart.toLong(),
                                            code_point_end = lastRangeEnd.toLong(),
                                            id = lastScript.toLong(),
                                        )
                                    }
                                    lastRangeStart = pair.first.first
                                    lastScript = pair.second
                                }
                                lastRangeEnd = pair.first.last
                            }
                    }
                }
            // flush!
            scriptQueries.insertScript(
                code_point_start = lastRangeStart.toLong(),
                code_point_end = lastRangeEnd.toLong(),
                id = lastScript.toLong(),
            )
            // clear!
            iso15924Map.clear()
            // Update version!
            dataStore.edit { prefs ->
                prefs[AppDataStoreKeys.KEY_DATA_SCRIPT_VERSION] = DATA_SCRIPT_VERSION
            }
        }

        // Read character data
        val dataQueries = database.unicodeDataQueries
        if (currentCharVersion < DATA_CHAR_VERSION) {
            currentStep = 4
            dataQueries.clear()
            var lastRangedCode = 0
            var totalChars = 0
            Res.readBytes("files/UCD/UnicodeData.txt")
                .decodeToString()
                .lineSequence()
                .filterNot {
                    it.isBlank() || it.startsWith('#')
                }
                .toList()
                .let { res ->
                    val size = res.size
                    dataQueries.transaction {
                        res.asSequence()
                            .filterNot {
                                it.isBlank() || it.startsWith('#')
                            }
                            .forEachIndexed { index, s ->
                                onProgress(currentStep, totalSteps, index, size)
                                val split = s.split(';')
                                val name = split.getOrNull(1)?.trim() ?: return@forEachIndexed
                                // filter surrogates and private use
                                if (name.contains("Surrogate") || name.contains("Private")) {
                                    return@forEachIndexed
                                }
                                val codeString = split.getOrNull(0)?.trim() ?: return@forEachIndexed
                                val codeValue =
                                    codeString.toIntOrNull(radix = 16) ?: return@forEachIndexed
                                if (name.endsWith("First>")) {
                                    lastRangedCode = codeValue
                                } else {
                                    val category = CharCategory.valueOf(
                                        value = split.getOrNull(2) ?: "Cn"
                                    )
                                    val combining = split.getOrNull(3)?.toIntOrNull() ?: 0
                                    val bidiClass = CharBidiClass.valueOf(
                                        value = split.getOrNull(4) ?: "ON"
                                    )
                                    if (name.endsWith("Last>")) {
                                        val tempName = name.removeSurrounding("<", ", Last>")
                                        (lastRangedCode..codeValue).forEach { code ->
                                            dataQueries.insertData(
                                                code_point = code.toLong(),
                                                name = "$tempName-${code.toString(radix = 16)}".uppercase(),
                                                category = category,
                                                combining = combining.toLong(),
                                                bidi_class = bidiClass,
                                            )
                                            totalChars++
                                        }
                                    } else {
                                        dataQueries.insertData(
                                            code_point = codeValue.toLong(),
                                            name = name,
                                            category = category,
                                            combining = combining.toLong(),
                                            bidi_class = bidiClass,
                                        )
                                        totalChars++
                                    }
                                }
                            }
                    }
                }
            // Save total chars
            dataStore.edit { prefs ->
                prefs[AppDataStoreKeys.KEY_DATA_TOTAL_CARDS] = totalChars
            }

            currentStep = 5
            // Correct names and add value cover from name aliases!
            Res.readBytes("files/UCD/NameAliases.txt")
                .decodeToString()
                .lineSequence()
                .filterNot {
                    it.isBlank() || it.startsWith('#')
                }
                .toList()
                .let { res ->
                    val size = res.size
                    dataQueries.transaction {
                        res.forEachIndexed { index, s ->
                            onProgress(currentStep, totalSteps, index, size)
                            val split = s.split(';')
                            val type = split.getOrNull(2)?.trim() ?: return@forEachIndexed
                            val name = split.getOrNull(1)?.trim() ?: return@forEachIndexed
                            val codeString = split.getOrNull(0)?.trim() ?: return@forEachIndexed
                            val codeValue =
                                codeString.toIntOrNull(radix = 16) ?: return@forEachIndexed
                            when (type) {
                                "correction",
                                "control",
                                "figment" -> {
                                    dataQueries.updateNameFor(
                                        name = name,
                                        CodePoint = codeValue.toLong()
                                    )
                                }

                                "abbreviation" -> {
                                    dataQueries.updateCoverFor(
                                        cover = name,
                                        CodePoint = codeValue.toLong()
                                    )
                                }
                            }
                        }
                    }
                }
            // Update version!
            dataStore.edit { prefs ->
                prefs[AppDataStoreKeys.KEY_DATA_CHAR_VERSION] = DATA_CHAR_VERSION
            }
        }

        // Update block char count when char data changed
        if (currentBlockVersion < DATA_BLOCK_VERSION || currentCharVersion < DATA_CHAR_VERSION) {
            currentStep = 6
            // Read unicode blocks
            val blocksQueries = database.unicodeBlocksQueries
            blocksQueries.clear()
            Res.readBytes("files/UCD/Blocks.txt")
                .decodeToString()
                .lineSequence()
                .filterNot {
                    it.isBlank() || it.startsWith('#')
                }
                .toList()
                .let { res ->
                    val size = res.size
                    blocksQueries.transaction {
                        res.forEachIndexed { index, s ->
                            onProgress(currentStep, totalSteps, index, size)
                            val split = s.split(';')
                            val rangeText = split.getOrNull(0)?.trim() ?: return@forEachIndexed
                            val range = rangeText.split("..").let { r ->
                                r.getOrNull(0)
                                    ?.toIntOrNull(radix = 16)
                                    ?.rangeTo(
                                        r.getOrElse(1) {
                                            r[0]
                                        }.toIntOrNull(radix = 16) ?: return@forEachIndexed
                                    ) ?: return@forEachIndexed
                            }
                            val name = split.getOrNull(1)?.trim() ?: return@forEachIndexed
                            // filter surrogates and private use
                            if (name.contains("Surrogate") || name.contains("Private")) {
                                return@forEachIndexed
                            }
                            val codePointStart = range.first.toLong()
                            val codePointEnd = range.last.toLong()
                            blocksQueries.insertBlock(
                                start = codePointStart,
                                end = codePointEnd,
                                name = name,
                                // We use the first letter of a block as example char!
                                // If there are no letters exists in a block,
                                // uses the first char instead!
                                example = dataQueries
                                    .queryFirstLetterByBlock(codePointStart, codePointEnd)
                                    .executeAsOneOrNull()
                                    ?: dataQueries
                                        .queryFirstCharByBlock(codePointStart, codePointEnd)
                                        .executeAsOneOrNull()
                                    ?: codePointStart,
                                count = dataQueries
                                    .queryBlockDataCount(codePointStart, codePointEnd)
                                    .executeAsOne()
                            )
                        }
                    }
                }
            // Update version!
            dataStore.edit { prefs ->
                prefs[AppDataStoreKeys.KEY_DATA_BLOCK_VERSION] = DATA_BLOCK_VERSION
            }
        }

        // Update fake usernames!
        if (currentUsernameVersion < DATA_USERNAME_VERSION) {
            currentStep = 7
            val fakeUsersQueries = database.fakeUsersQueries
            fakeUsersQueries.clear()
            Res.readBytes("files/game/FakeUsers.txt")
                .decodeToString()
                .lineSequence()
                .filterNot {
                    it.isBlank() || it.startsWith('#')
                }
                .toList()
                .let { res ->
                    val size = res.size
                    fakeUsersQueries.transaction {
                        res.forEachIndexed { index, s ->
                            onProgress(currentStep, totalSteps, index, size)
                            fakeUsersQueries.insertFakeUser(s)
                        }
                    }
                }
            // Update version!
            dataStore.edit { prefs ->
                prefs[AppDataStoreKeys.KEY_DATA_USERNAME_VERSION] = DATA_USERNAME_VERSION
            }
        }
        // Finish!
        currentStep = totalSteps + 1
        onProgress(currentStep, totalSteps, 1, 1)
    }

    private suspend fun initPreferences(prefs: Preferences?) {
        // Initialize free pack!
        val freePackFullTime =
            prefs?.get(AppDataStoreKeys.KEY_MARKET_FREE_PACK_FULL_TIME) ?: 0L
        if (freePackFullTime == 0L) {
            val newFreePackFullTime = TimeUtils.currentTimeMillis() +
                    (AppConfigs.FREE_PACK_MAXIMUM_STORAGE -
                            AppConfigs.FREE_PACK_INITIAL_STORAGE) *
                    AppConfigs.FREE_PACK_REFILL_TIME
            dataStore.edit {
                it[AppDataStoreKeys.KEY_MARKET_FREE_PACK_FULL_TIME] = newFreePackFullTime
            }
        }
    }

}