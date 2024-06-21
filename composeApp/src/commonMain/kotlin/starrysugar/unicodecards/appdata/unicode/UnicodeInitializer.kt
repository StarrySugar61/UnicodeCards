package starrysugar.unicodecards.appdata.unicode

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.firstOrNull
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import starrysugar.unicodecards.Res
import starrysugar.unicodecards.appdata.database.Database
import starrysugar.unicodecards.appdata.datastore.AppDataStoreKeys

/**
 * Initialize unicode data to database!
 *
 * @author StarrySugar61
 * @create 2024/6/16
 */
object UnicodeInitializer : KoinComponent {

    private const val DATA_SCRIPT_VERSION = 1
    private const val DATA_CHAR_VERSION = 1
    private const val DATA_BLOCK_VERSION = 1

    private val dataStore: DataStore<Preferences> by inject()

    // TODO Optimize database importing!
    @OptIn(ExperimentalResourceApi::class)
    suspend fun importUnicodeDataTo(
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
        var totalSteps = 0
        var currentStep = 0
        if (currentScriptVersion < DATA_SCRIPT_VERSION) {
            totalSteps += 2
        }
        if (currentCharVersion < DATA_CHAR_VERSION) {
            totalSteps += 2
        }
        if (currentBlockVersion < DATA_BLOCK_VERSION) {
            totalSteps++
        }
        if (currentScriptVersion < DATA_SCRIPT_VERSION) {
            // Read iso 15924
            currentStep++
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
                    res.forEachIndexed { index, s ->
                        onProgress(currentStep, totalSteps, index, size)
                        val split = s.split(';')
                        val code = split.getOrNull(0)?.trim() ?: return@forEachIndexed
                        val id = split.getOrNull(1)?.trim()?.toIntOrNull() ?: return@forEachIndexed
                        val name =
                            split.getOrNull(4)?.trim()?.ifEmpty { null } ?: return@forEachIndexed
                        iso15924Map[name] = id to code
                        iso15924Queries.insertData(id.toLong(), code, name)
                    }
                }

            // Read scripts
            currentStep++
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
                                iso15924Map[split.getOrNull(1)?.trim()]?.first ?: return@map null
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
            currentStep++
            dataQueries.clear()
            var lastRangedCode = 0
            val dataRes = Res.readBytes("files/UCD/UnicodeData.txt")
                .decodeToString()
                .lineSequence()
                .filterNot {
                    it.isBlank() || it.startsWith('#')
                }
                .toList()
                .let { res ->
                    val size = res.size
                    res.asSequence()
                        .filterNot {
                            it.isBlank() || it.startsWith('#')
                        }
                        .forEachIndexed { index, s ->
                            onProgress(currentStep, totalSteps, index, size)
                            val split = s.split(';')
                            val name = split.getOrNull(1)?.trim() ?: return@forEachIndexed
                            // filter surrogates and private use
                            if (name.contains("Surrogates") || name.contains("Private")) {
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
                                    }
                                } else {
                                    dataQueries.insertData(
                                        code_point = codeValue.toLong(),
                                        name = name,
                                        category = category,
                                        combining = combining.toLong(),
                                        bidi_class = bidiClass,
                                    )
                                }
                            }
                        }
                }

            currentStep++
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
                    res.forEachIndexed { index, s ->
                        onProgress(currentStep, totalSteps, index, size)
                        val split = s.split(';')
                        val type = split.getOrNull(2)?.trim() ?: return@forEachIndexed
                        val name = split.getOrNull(1)?.trim() ?: return@forEachIndexed
                        val codeString = split.getOrNull(0)?.trim() ?: return@forEachIndexed
                        val codeValue = codeString.toIntOrNull(radix = 16) ?: return@forEachIndexed
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
            // Update version!
            dataStore.edit { prefs ->
                prefs[AppDataStoreKeys.KEY_DATA_CHAR_VERSION] = DATA_CHAR_VERSION
            }
        }

        // Update block char count when char data changed
        if (currentBlockVersion < DATA_BLOCK_VERSION || currentCharVersion < DATA_CHAR_VERSION) {
            currentStep++
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
                        if (name.contains("Surrogates") || name.contains("Private")) {
                            return@forEachIndexed
                        }
                        val codePointStart = range.first.toLong()
                        val codePointEnd = range.last.toLong()
                        blocksQueries.insertBlock(
                            code_point_start = codePointStart,
                            code_point_end = codePointEnd,
                            block_name = name,
                            char_count = dataQueries
                                .queryBlockDataCount(codePointStart, codePointEnd)
                                .executeAsOne()
                        )
                    }
                }
            // Update version!
            dataStore.edit { prefs ->
                prefs[AppDataStoreKeys.KEY_DATA_BLOCK_VERSION] = DATA_BLOCK_VERSION
            }
        }
        // Finish!
        currentStep++
        onProgress(currentStep, totalSteps, 1, 1)
    }

}