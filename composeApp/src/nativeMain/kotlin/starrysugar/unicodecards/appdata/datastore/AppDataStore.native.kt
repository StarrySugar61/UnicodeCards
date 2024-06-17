package starrysugar.unicodecards.appdata.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

/**
 * @author StarrySugar61
 * @create 2024/6/17
 */
actual class AppDataStore {
    actual val dataStore: DataStore<Preferences> by lazy {
        createDataStore(
            producePath = {
                val documentDirectory: NSURL = NSFileManager.defaultManager.URLForDirectory(
                    directory = NSDocumentDirectory,
                    inDomain = NSUserDomainMask,
                    appropriateForURL = null,
                    create = false,
                    error = null,
                )
                requireNotNull(documentDirectory).path + "/$dataStorePath"
            }
        )
    }

}