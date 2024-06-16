package starrysugar.unicodecards.appdata.database

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import starrysugar.unicodecards.app.UCApplication

/**
 * @author StarrySugar61
 * @create 2024/6/16
 */
actual class DriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver = AndroidSqliteDriver(
        schema = Database.Schema,
        context = context,
        name = "unicode_cards.db",
    )
}

internal actual val driverFactory by lazy {
    DriverFactory(UCApplication.instance)
}