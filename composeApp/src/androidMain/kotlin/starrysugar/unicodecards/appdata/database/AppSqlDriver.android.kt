package starrysugar.unicodecards.appdata.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import starrysugar.unicodecards.app.UCApplication

/**
 * @author StarrySugar61
 * @create 2024/6/16
 */
actual class AppDriverFactory {
    actual fun createDriver(): SqlDriver = AndroidSqliteDriver(
        schema = Database.Schema,
        context = UCApplication.instance,
        name = "unicode_cards.db",
    )
}