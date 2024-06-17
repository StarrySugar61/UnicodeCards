import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.koin.core.context.startKoin
import starrysugar.unicodecards.appdata.di.koinModules

fun main() = application {
    startKoin {
        modules(koinModules)
    }
    Window(
        onCloseRequest = ::exitApplication,
        title = "Unicode Cards",
    ) {
        App()
    }
}