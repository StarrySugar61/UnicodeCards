package starrysugar.unicodecards.appdata.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver

/**
 * @author StarrySugar61
 * @create 2024/6/16
 */
actual class AppDriverFactory {
    actual fun createDriver(): SqlDriver = JdbcSqliteDriver(
        url = "jdbc:sqlite:unicode_cards.db",
    ).apply {
        Database.Schema.create(this)
    }
}