package ru.alexeypostnov.effectivemobilecourses.feature.favourites.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import ru.alexeypostnov.effectivemobilecourses.core.ui.components.CoursesListComponent
import ru.alexeypostnov.effectivemobilecourses.core.ui.model.CourseUI
import ru.alexeypostnov.effectivemobilecourses.core.ui.theme.White
import ru.alexeypostnov.effectivemobilecourses.feature.favourites.R

@Composable
fun FavouritesScreen(
    onCourseClick: (courseId: Int) -> Unit
) {
    val viewModel: FavouritesViewModel = koinViewModel()

    val courses by viewModel.courses.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val error by viewModel.error.collectAsStateWithLifecycle()

    FavouritesScreenContent(
        courses = courses,
        isLoading = isLoading,
        error = error,
        onCourseClick = onCourseClick,
        onSavedClick = viewModel::updateSavedStatus
    )
}

@Composable
fun FavouritesScreenContent(
    courses: List<CourseUI>,
    isLoading: Boolean,
    error: String?,
    onCourseClick: (courseId: Int) -> Unit,
    onSavedClick: (courseId: Int, hasLike: Boolean) -> Unit
) {
    Column (
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.favourites),
            style = MaterialTheme.typography.titleLarge,
            lineHeight = 28.sp,
            color = White
        )

        CoursesListComponent(
            modifier = Modifier
                .fillMaxSize(),
            items = courses,
            isLoading = isLoading,
            error = error,
            onCourseClick = onCourseClick,
            onSavedClick = onSavedClick
        )
    }
}

@Composable
@Preview
fun FavouritesScreenPreview() {
    FavouritesScreenContent(
        courses = listOf(
            CourseUI(
                id = 1,
                title = "Java-разработчик с нуля",
                text = "Освойте backend-разработку и программирование на Java, фреймворки, что-то ещё, ещё и ещё",
                price = "999",
                rate = "4.9",
                startDate = "29-01-2026",
                hasLike = true,
                publishDate = "21-01-2026"
            )
        ),
        isLoading = true,
        error = null,
        onCourseClick = { },
        onSavedClick = { _, _ -> }
    )
}