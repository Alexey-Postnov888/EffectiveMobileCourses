package ru.alexeypostnov.effectivemobilecourses.core.ui.composition

import androidx.compose.runtime.staticCompositionLocalOf

val LocalCourseClick = staticCompositionLocalOf<(courseId: Int) -> Unit> {
    error("LocalCourseClickHandler is not provided")
}