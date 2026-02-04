package ru.alexeypostnov.effectivemobilecourses.feature.account.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import ru.alexeypostnov.effectivemobilecourses.core.ui.components.CoursesListComponent
import ru.alexeypostnov.effectivemobilecourses.core.ui.model.CourseUI
import ru.alexeypostnov.effectivemobilecourses.core.ui.theme.DarkGray
import ru.alexeypostnov.effectivemobilecourses.core.ui.theme.Stroke
import ru.alexeypostnov.effectivemobilecourses.core.ui.theme.White
import ru.alexeypostnov.effectivemobilecourses.feature.account.R

@Composable
fun AccountScreen(
    onCourseClick: (courseId: Int) -> Unit
) {
    val viewModel: AccountViewModel = koinViewModel()

    val courses by viewModel.courses.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val error by viewModel.error.collectAsStateWithLifecycle()

    AccountScreenContent(
        courses = courses,
        isLoading = isLoading,
        error = error,
        onCourseClick = onCourseClick,
        onSavedClick = viewModel::updateSavedStatus
    )
}

@Composable
fun AccountScreenContent(
    courses: List<CourseUI>,
    isLoading: Boolean,
    error: String?,
    onCourseClick: (courseId: Int) -> Unit,
    onSavedClick: (courseId: Int, hasLike: Boolean) -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        val (accountScreenTitle,
            accountScreenControlsContainer,
            accountScreenCoursesTitle,
            accountScreenCoursesComponent) = createRefs()

        Text(
            text = stringResource(R.string.profile),
            style = MaterialTheme.typography.titleLarge,
            lineHeight = 28.sp,
            color = White,
            modifier = Modifier
                .constrainAs(accountScreenTitle) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
        )

        val controlsItems = listOf<String>(
            stringResource(R.string.textToSupport),
            stringResource(R.string.settings),
            stringResource(R.string.logout)
        )

        ControlsList(
            items = controlsItems,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(accountScreenControlsContainer) {
                    top.linkTo(accountScreenTitle.bottom, margin = 16.dp)
                    start.linkTo(parent.start)
                }
        )

        Text(
            text = stringResource(R.string.yourCourses),
            style = MaterialTheme.typography.titleLarge,
            lineHeight = 28.sp,
            color = White,
            modifier = Modifier
                .constrainAs(accountScreenCoursesTitle) {
                    top.linkTo(accountScreenControlsContainer.bottom, margin = 32.dp)
                    start.linkTo(parent.start)
                }
        )

        CoursesListComponent(
            modifier = Modifier
                .constrainAs(accountScreenCoursesComponent) {
                    top.linkTo(accountScreenCoursesTitle.bottom, margin = 16.dp)
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
fun ControlsList(modifier: Modifier, items: List<String>) {
    Surface(
        color = DarkGray,
        contentColor = White,
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 14.dp)
        ) {
            items.forEachIndexed { index, item ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(11.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = item,
                            style = MaterialTheme.typography.labelLarge
                        )
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.arrow_right_controls),
                            contentDescription = item
                        )
                    }

                    if (index != items.size - 1) {
                        HorizontalDivider(
                            color = Stroke,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun AccountScreenPreview() {
    AccountScreenContent(
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
