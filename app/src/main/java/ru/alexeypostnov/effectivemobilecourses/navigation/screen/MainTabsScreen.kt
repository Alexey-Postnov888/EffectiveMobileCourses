package ru.alexeypostnov.effectivemobilecourses.navigation.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import ru.alexeypostnov.effectivemobilecourses.core.ui.composition.LocalCourseClick
import ru.alexeypostnov.effectivemobilecourses.core.ui.theme.DarkGray
import ru.alexeypostnov.effectivemobilecourses.core.ui.theme.Green
import ru.alexeypostnov.effectivemobilecourses.core.ui.theme.LightGray
import ru.alexeypostnov.effectivemobilecourses.core.ui.theme.White
import ru.alexeypostnov.effectivemobilecourses.navigation.tab.AccountNavigationTab
import ru.alexeypostnov.effectivemobilecourses.navigation.tab.FavouritesNavigationTab
import ru.alexeypostnov.effectivemobilecourses.navigation.tab.HomeNavigationTab

@Composable
private fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current

    NavigationBarItem(
        selected = tabNavigator.current == tab,
        onClick = { tabNavigator.current = tab },
        icon = {
            tab.options.icon?.let {
                Icon(
                    painter = it,
                    contentDescription = tab.options.title,
                )
            }
        },
        alwaysShowLabel = true,
        label = {
            Text(
                text = tab.options.title,
                style = MaterialTheme.typography.labelMedium,
            )
        },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = Green,
            selectedTextColor = Green,
            unselectedIconColor = White,
            unselectedTextColor = White,
            indicatorColor = LightGray
        )
    )
}
class MainTabsScreen: Screen {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    override fun Content() {
        val homeTab = remember { HomeNavigationTab() }
        val favouritesTab = remember { FavouritesNavigationTab() }
        val accountTab = remember { AccountNavigationTab() }

        val navigator = LocalNavigator.current

        val localCourseClickHandler = remember<(courseId: Int) -> Unit>(navigator) {
            { courseId: Int ->
                navigator?.push(CourseNavigationScreen(courseId))
            }
        }

        CompositionLocalProvider(
            LocalCourseClick provides localCourseClickHandler
        ) {
            TabNavigator(homeTab) {
                Scaffold(
                    bottomBar = {
                        NavigationBar(
                            containerColor = DarkGray,
                        ) {
                            TabNavigationItem(homeTab)
                            TabNavigationItem(favouritesTab)
                            TabNavigationItem(accountTab)
                        }
                    },
                    content = { innerPadding ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                        ) {
                            CurrentTab()
                        }
                    }
                )
            }
        }
    }
}