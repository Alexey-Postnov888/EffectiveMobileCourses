package ru.alexeypostnov.effectivemobilecourses.navigation.tab

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.vectorResource
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import ru.alexeypostnov.effectivemobilecourses.R
import ru.alexeypostnov.effectivemobilecourses.core.ui.composition.LocalCourseClick
import ru.alexeypostnov.effectivemobilecourses.feature.home.presentation.HomeScreen

class HomeNavigationTab: Tab {
    @Composable
    override fun Content() {
        val onCourseClick = LocalCourseClick.current

        HomeScreen(
            onCourseClick = onCourseClick
        )
    }

    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(ImageVector.vectorResource(id = R.drawable.home))
            return remember {
                TabOptions(
                    index = 0u,
                    title = "Главная",
                    icon = icon
                )
            }
        }
}