package ru.alexeypostnov.effectivemobilecourses.navigation.screen

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import ru.alexeypostnov.effectivemobilecourses.feature.login.presentation.LoginScreen

class LoginNavigationScreen: Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

        LoginScreen(
            onLoginSuccess = {
                navigator?.replaceAll(MainTabsScreen())
            }
        )
    }
}