/*
 * Copyright (C) 2024 StarrySugar
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the 
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program. 
 * If not, see <https://www.gnu.org/licenses/>.
 */
package starrysugar.unicodecards.app.nav

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import starrysugar.unicodecards.app.ui.main.MainScreen
import starrysugar.unicodecards.app.ui.main.cards.deck.DeckScreen
import starrysugar.unicodecards.app.ui.main.cards.deck.code.CodePointScreen

/**
 * App navigation!
 *
 * @author StarrySugar61
 * @create 2024/6/16
 */
@Composable
fun AppNavHost(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Main.route,
    ) {
        appComposable(
            screen = Screen.Main,
        ) {
            MainScreen(
                navController = navController,
            )
        }
        appComposable(
            screen = Screen.MainDeck,
        ) {
            DeckScreen(
                startCodePoint = it.arguments!!.getInt("startCodePoint"),
                navController = navController,
            )
        }
        appComposable(
            screen = Screen.MainDeckCodePoint,
        ) {
            CodePointScreen(
                codePoint = it.arguments!!.getInt("codePoint"),
                navController = navController,
            )
        }
    }
}

private fun NavGraphBuilder.appComposable(
    screen: Screen,
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
    composable(
        route = screen.composableRoute(),
        arguments = screen.arguments ?: emptyList(),
        content = content,
    )
}

/**
 * Screens of app!
 */
sealed class Screen(
    override val route: String,
    override val arguments: List<NamedNavArgument>? = null,
) : Route {
    data object Main : Screen(route = "uc_main")

    data object MainDeck : Screen(
        route = "uc_main_deck",
        arguments = listOf(
            navArgument(
                name = "startCodePoint",
            ) {
                type = NavType.IntType
            }
        )
    ) {
        fun buildRoute(
            startCodePoint: Int,
        ): String = "$route/$startCodePoint"
    }

    data object MainDeckCodePoint : Screen(
        route = "uc_main_deck_code_point",
        arguments = listOf(
            navArgument(
                name = "codePoint",
            ) {
                type = NavType.IntType
            }
        )
    ) {
        fun buildRoute(
            codePoint: Int,
        ): String = "$route/$codePoint"
    }
}

interface Route {
    val route: String
    val arguments: List<NamedNavArgument>?

    fun composableRoute(): String = buildString {
        append(route)
        arguments?.forEach {
            append("/{")
            append(it.name)
            append('}')
        }
    }
}