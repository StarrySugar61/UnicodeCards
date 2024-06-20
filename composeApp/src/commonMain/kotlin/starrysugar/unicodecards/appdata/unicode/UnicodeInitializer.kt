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


    @OptIn(ExperimentalResourceApi::class)
    suspend fun importUnicodeDataTo(
        database: Database,
    ) {
        val preferences = dataStore.data.firstOrNull()
        val currentScriptVersion = preferences?.get(AppDataStoreKeys.KEY_DATA_SCRIPT_VERSION) ?: 0
        val currentCharVersion = preferences?.get(AppDataStoreKeys.KEY_DATA_CHAR_VERSION) ?: 0
        val currentBlockVersion = preferences?.get(AppDataStoreKeys.KEY_DATA_BLOCK_VERSION) ?: 0

        if (currentScriptVersion < DATA_SCRIPT_VERSION) {
            // Read iso 15924
            val iso15924Queries = database.unicodeIso15924Queries
            iso15924Queries.clear()
            val iso15924Map = HashMap<String, Pair<Int, String>>()
            Res.readBytes("files/UCD/iso15924.txt")
                .decodeToString()
                .lineSequence()
                .filterNot {
                    it.isBlank() || it.startsWith('#')
                }
                .forEach {
                    val split = it.split(';')
                    val code = split.getOrNull(0)?.trim() ?: return@forEach
                    val id = split.getOrNull(1)?.trim()?.toIntOrNull() ?: return@forEach
                    val name = split.getOrNull(4)?.trim()?.ifEmpty { null } ?: return@forEach
                    iso15924Map[name] = id to code
                    iso15924Queries.insertData(id.toLong(), code, name)
                }
            // Read scripts
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
                    val id = iso15924Map[split.getOrNull(1)?.trim()]?.first ?: return@map null
                    range to id
                }
                .filterNotNull()
                .sortedBy {
                    it.first.first
                }
                .forEach {
                    if (
                        it.second != lastScript
                        || lastRangeEnd + 1 < it.first.first
                    ) {
                        if (lastScript >= 0) {
                            scriptQueries.insertScript(
                                code_point_start = lastRangeStart.toLong(),
                                code_point_end = lastRangeEnd.toLong(),
                                id = lastScript.toLong(),
                            )
                        }
                        lastRangeStart = it.first.first
                        lastScript = it.second
                    }
                    lastRangeEnd = it.first.last
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
            dataQueries.clear()
            var lastRangedCode = 0
            Res.readBytes("files/UCD/UnicodeData.txt")
                .decodeToString()
                .lineSequence()
                .filterNot {
                    it.isBlank() || it.startsWith('#')
                }
                .forEach {
                    val split = it.split(';')
                    val name = split.getOrNull(1)?.trim() ?: return@forEach
                    // filter surrogates and private use
                    if (name.contains("Surrogates") || name.contains("Private")) {
                        return@forEach
                    }
                    val codeString = split.getOrNull(0)?.trim() ?: return@forEach
                    val codeValue = codeString.toIntOrNull(radix = 16) ?: return@forEach
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

            // Correct names and add value cover from name aliases!
            Res.readBytes("files/UCD/NameAliases.txt")
                .decodeToString()
                .lineSequence()
                .filterNot {
                    it.isBlank() || it.startsWith('#')
                }
                .forEach {
                    val split = it.split(';')
                    val type = split.getOrNull(2)?.trim() ?: return@forEach
                    val name = split.getOrNull(1)?.trim() ?: return@forEach
                    val codeString = split.getOrNull(0)?.trim() ?: return@forEach
                    val codeValue = codeString.toIntOrNull(radix = 16) ?: return@forEach
                    when (type) {
                        "correction",
                        "control",
                        "figment" -> {
                            dataQueries.updateNameFor(name = name, CodePoint = codeValue.toLong())
                        }

                        "abbreviation" -> {
                            dataQueries.updateCoverFor(cover = name, CodePoint = codeValue.toLong())
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
            // Read unicode blocks
            val blocksQueries = database.unicodeBlocksQueries
            blocksQueries.clear()
            Res.readBytes("files/UCD/Blocks.txt")
                .decodeToString()
                .lineSequence()
                .filterNot {
                    it.isBlank() || it.startsWith('#')
                }
                .forEach {
                    val split = it.split(';')
                    val rangeText = split.getOrNull(0)?.trim() ?: return@forEach
                    val range = rangeText.split("..").let { r ->
                        r.getOrNull(0)
                            ?.toIntOrNull(radix = 16)
                            ?.rangeTo(
                                r.getOrElse(1) {
                                    r[0]
                                }.toIntOrNull(radix = 16) ?: return@forEach
                            ) ?: return@forEach
                    }
                    val name = split.getOrNull(1)?.trim() ?: return@forEach
                    // filter surrogates and private use
                    if (name.contains("Surrogates") || name.contains("Private")) {
                        return@forEach
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
            // Update version!
            dataStore.edit { prefs ->
                prefs[AppDataStoreKeys.KEY_DATA_BLOCK_VERSION] = DATA_BLOCK_VERSION
            }
        }

    }

}