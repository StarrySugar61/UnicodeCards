package starrysugar.unicodecards.appdata.datastore

import androidx.datastore.preferences.core.intPreferencesKey

/**
 * @author StarrySugar61
 * @create 2024/6/17
 */
object AppDataStoreKeys {
    val KEY_DATA_SCRIPT_VERSION = intPreferencesKey("KEY_DATA_SCRIPT_VERSION")
    val KEY_DATA_CHAR_VERSION = intPreferencesKey("KEY_DATA_CHAR_VERSION")
    val KEY_DATA_BLOCK_VERSION = intPreferencesKey("KEY_DATA_BLOCK_VERSION")
}