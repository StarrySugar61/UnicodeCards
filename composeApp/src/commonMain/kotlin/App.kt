import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.ui.tooling.preview.Preview
import starrysugar.unicodecards.app.nav.AppNavHost

@Composable
@Preview
fun App() {
    MaterialTheme {
        AppNavHost(
            navController = rememberNavController(),
        )
    }
}