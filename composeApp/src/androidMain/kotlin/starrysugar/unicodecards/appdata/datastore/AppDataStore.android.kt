package starrysugar.unicodecards.appdata.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import starrysugar.unicodecards.app.UCApplication

/**
 * @author StarrySugar61
 * @create 2024/6/17
 */
actual class AppDataStore {
    actual val dataStore: DataStore<Preferences> by lazy {
        createDataStore(
            producePath = {
                UCApplication.instance.filesDir.resolve(dataStorePath).absolutePath
            }
        )
    }

}