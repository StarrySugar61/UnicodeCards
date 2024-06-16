package starrysugar.unicodecards.appdata.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver

/**
 * @author StarrySugar61
 * @create 2024/6/16
 */
actual class DriverFactory {
    actual fun createDriver(): SqlDriver = NativeSqliteDriver(
        schema = Database.Schema,
        name = "unicode_cards.db",
    )
}

internal actual val driverFactory = DriverFactory()