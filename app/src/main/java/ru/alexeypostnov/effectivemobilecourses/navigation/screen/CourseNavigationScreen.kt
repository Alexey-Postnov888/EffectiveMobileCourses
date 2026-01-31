package ru.alexeypostnov.effectivemobilecourses.navigation.screen

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import ru.alexeypostnov.effectivemobilecourses.feature.course.presentation.CourseScreen

class CourseNavigationScreen(
    private val courseId: Int
): Screen {
    @Composable
    override fun Content() {
        CourseScreen(
            courseId = courseId
        )
    }
}