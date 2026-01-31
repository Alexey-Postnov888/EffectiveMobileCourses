package ru.alexeypostnov.effectivemobilecourses

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import ru.alexeypostnov.effectivemobilecourses.core.ui.theme.Dark
import ru.alexeypostnov.effectivemobilecourses.core.ui.theme.EffectiveMobileCoursesTheme
import ru.alexeypostnov.effectivemobilecourses.navigation.screen.LoginNavigationScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EffectiveMobileCoursesTheme {
                Box(
                    modifier = Modifier
                        .background(Dark)
                        .fillMaxSize()
                ) {
                    Navigator(
                        screen = LoginNavigationScreen()
                    ) { navigator ->
                        SlideTransition(navigator)
                    }
                }
            }
        }
    }
}