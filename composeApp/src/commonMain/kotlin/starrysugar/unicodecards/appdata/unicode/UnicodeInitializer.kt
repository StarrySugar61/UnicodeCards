package starrysugar.unicodecards.appdata.unicode

import org.jetbrains.compose.resources.ExperimentalResourceApi
import starrysugar.unicodecards.Res
import starrysugar.unicodecards.appdata.database.Database

/**
 * Initialize unicode data to database!
 *
 * @author StarrySugar61
 * @create 2024/6/16
 */
object UnicodeInitializer {

    @OptIn(ExperimentalResourceApi::class)
    suspend fun importUnicodeDataTo(
        database: Database,
    ) {
        // Read iso 15924
        val iso15924Queries = database.unicodeIso15924Queries
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

        // Read character data
        val dataQueries = database.unicodeDataQueries
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


        // Read unicode blocks
        val blocksQueries = database.unicodeBlocksQueries
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

    }

}