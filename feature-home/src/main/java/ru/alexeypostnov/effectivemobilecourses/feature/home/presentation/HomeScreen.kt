package ru.alexeypostnov.effectivemobilecourses.feature.home.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import ru.alexeypostnov.effectivemobilecourses.core.ui.components.CoursesListComponent
import ru.alexeypostnov.effectivemobilecourses.core.ui.model.CourseUI
import ru.alexeypostnov.effectivemobilecourses.core.ui.theme.DarkGray
import ru.alexeypostnov.effectivemobilecourses.core.ui.theme.Green
import ru.alexeypostnov.effectivemobilecourses.core.ui.theme.OnDarkGray
import ru.alexeypostnov.effectivemobilecourses.core.ui.theme.White
import ru.alexeypostnov.effectivemobilecourses.feature.home.R

@Composable
fun HomeScreen(
    onCourseClick: (courseId: Int) -> Unit
) {
    val viewModel: HomeViewModel = koinViewModel()

    val input by viewModel.input.collectAsStateWithLifecycle()
    val courses by viewModel.courses.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val error by viewModel.error.collectAsStateWithLifecycle()

    HomeScreenContent(
        input = input,
        onInputChanged = viewModel::updateInput,
        courses = courses,
        isLoading = isLoading,
        error = error,
        onCourseClick = onCourseClick,
        onSortClick = viewModel::sortCoursesByPublishDate,
        onSavedClick = viewModel::updateSavedStatus
    )
}

@Composable
fun HomeScreenContent(
    input: String,
    onInputChanged: (String) -> Unit,
    courses: List<CourseUI>,
    isLoading: Boolean,
    error: String?,
    onCourseClick: (courseId: Int) -> Unit,
    onSortClick: () -> Unit,
    onSavedClick: (courseId: Int, hasLike: Boolean) -> Unit,
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        val (homeScreenSearchInput,
            homeScreenFilterButton,
            homeScreenSortButton,
            homeScreenCoursesList) = createRefs()

        TextField(
            value = input,
            onValueChange = onInputChanged,
            leadingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.search),
                    contentDescription = stringResource(R.string.search),
                    tint = White
                )
            },
            placeholder = {
                Text(
                    "Search courses...",
                    style = MaterialTheme.typography.bodyMedium,
                    color = OnDarkGray
                )
            },
            shape = RoundedCornerShape(28.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = DarkGray,
                focusedContainerColor = DarkGray,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
            ),
            textStyle = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .height(56.dp)
                .constrainAs(homeScreenSearchInput) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(homeScreenFilterButton.start, margin = 8.dp)
                    width = Dimension.fillToConstraints
                }
        )

        IconButton(
            onClick = {  },
            modifier = Modifier
                .constrainAs(homeScreenFilterButton) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
                .height(56.dp)
                .width(56.dp),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = DarkGray
            )
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.funnel),
                contentDescription = stringResource(R.string.filter),
                tint = White
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .clickable(
                    onClick = onSortClick,
                )
                .constrainAs(homeScreenSortButton) {
                    top.linkTo(homeScreenFilterButton.bottom, margin = 16.dp)
                    end.linkTo(parent.end)
                },
        ) {
            Text(
                text = stringResource(R.string.byPublishDate),
                style = MaterialTheme.typography.labelLarge,
                color = Green
            )

            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.sort),
                contentDescription = stringResource(R.string.sorting),
                tint = Green
            )
        }

        CoursesListComponent(
            modifier = Modifier
                .constrainAs(homeScreenCoursesList) {
                    top.linkTo(homeScreenSortButton.bottom, margin = 16.dp)
                    centerHorizontallyTo(parent)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.fillToConstraints
                },
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
fun HomeScreenPreview() {
    HomeScreenContent(
        "",
        onInputChanged = { },
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
        onSortClick = { },
        onSavedClick = { _, _ -> }
    )
}