package ru.alexeypostnov.effectivemobilecourses.feature.course.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.eygraber.compose.placeholder.PlaceholderHighlight
import com.eygraber.compose.placeholder.material3.fade
import com.eygraber.compose.placeholder.placeholder
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import ru.alexeypostnov.effectivemobilecourses.core.ui.components.DateBadgeComponent
import ru.alexeypostnov.effectivemobilecourses.core.ui.components.RateBadgeComponent
import ru.alexeypostnov.effectivemobilecourses.core.ui.model.CourseUI
import ru.alexeypostnov.effectivemobilecourses.core.ui.theme.Dark
import ru.alexeypostnov.effectivemobilecourses.core.ui.theme.DarkGray
import ru.alexeypostnov.effectivemobilecourses.core.ui.theme.DescriptionTextColor
import ru.alexeypostnov.effectivemobilecourses.core.ui.theme.Green
import ru.alexeypostnov.effectivemobilecourses.core.ui.theme.LightGray
import ru.alexeypostnov.effectivemobilecourses.core.ui.theme.OnDarkGray
import ru.alexeypostnov.effectivemobilecourses.core.ui.theme.White
import ru.alexeypostnov.effectivemobilecourses.feature.course.R
import ru.alexeypostnov.effectivemobilecourses.core.ui.R.drawable as CoreUIDrawable

@Composable
fun CourseScreen(courseId: Int) {
    val viewModel: CourseViewModel = koinViewModel(parameters = { parametersOf(courseId) })

    val course by viewModel.course.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val error by viewModel.error.collectAsStateWithLifecycle()

    val navigator = LocalNavigator.currentOrThrow

    CourseScreenContent(
        course = course,
        isLoading = isLoading,
        error = error,
        onBackClick = { navigator.pop() },
        onSavedClick = viewModel::updateSavedStatus
    )
}

@Composable
fun CourseScreenContent(
    course: CourseUI,
    isLoading: Boolean,
    error: String?,
    onBackClick: () -> Unit,
    onSavedClick: (courseId: Int, hasLike: Boolean) -> Unit
) {
    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .background(Dark)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding()
            ) {
                Image(
                    painter = painterResource(CoreUIDrawable.cover),
                    contentDescription = stringResource(R.string.imageOfCourse),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp)
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .background(
                            brush = Brush.verticalGradient(
                                0.0f to Dark.copy(alpha = 0.3f),
                                1.0f to Color.Transparent
                            )
                        )
                )
            }

            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                val (courseScreenTopSection,
                    courseScreenCourseTitle,
                    courseScreenAuthorContainer,
                    courseScreenControlsContainer,
                    courseScreenAboutCourseTitle,
                    courseScreenAboutCourseText) = createRefs()

                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(224.dp)
                        .constrainAs(courseScreenTopSection) {
                            top.linkTo(parent.top)
                            centerHorizontallyTo(parent)
                        }
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        IconButton(
                            onClick = onBackClick,
                            modifier = Modifier
                                .size(44.dp),
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = White,
                                contentColor = Dark
                            ),
                        ) {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.arrow_left),
                                contentDescription = stringResource(R.string.back),
                            )
                        }

                        IconButton(
                            onClick = { onSavedClick(course.id, !course.hasLike) },
                            modifier = Modifier
                                .size(44.dp),
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = White,
                            ),
                        ) {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.bookmark),
                                contentDescription = stringResource(R.string.favourites),
                                tint = if (course.hasLike) Green else Dark
                            )
                        }
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    ) {
                        RateBadgeComponent(
                            rate = course.rate,
                            isLoading = isLoading
                        )
                        DateBadgeComponent(
                            date = course.startDate,
                            isLoading = isLoading
                        )
                    }
                }

                Text(
                    text = course.title,
                    style = MaterialTheme.typography.titleLarge,
                    lineHeight = 28.sp,
                    color = White,
                    modifier = Modifier
                        .widthIn(min = 200.dp)
                        .placeholder(
                            visible = isLoading,
                            highlight = PlaceholderHighlight.fade(),
                            color = LightGray.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(28.dp)
                        )
                        .constrainAs(courseScreenCourseTitle) {
                            top.linkTo(courseScreenTopSection.bottom, margin = 16.dp)
                            start.linkTo(parent.start)
                        }
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(courseScreenAuthorContainer) {
                            top.linkTo(courseScreenCourseTitle.bottom, margin = 20.dp)
                            start.linkTo(parent.start)
                        }
                ) {
                    Image(
                        painter = painterResource(R.drawable.author),
                        contentDescription = stringResource(R.string.imageOfAuthor),
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(20.dp))
                    )

                    Column(

                    ) {
                        Text(
                            text = stringResource(R.string.author),
                            color = OnDarkGray,
                            fontSize = 12.sp,
                            lineHeight = 14.sp,
                            letterSpacing = 0.4.sp
                        )

                        Text(
                            text = "Merion Academy",
                            color = White,
                            fontSize = 16.sp,
                            lineHeight = 18.sp,
                            letterSpacing = 0.15.sp
                        )
                    }
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(courseScreenControlsContainer) {
                            top.linkTo(courseScreenAuthorContainer.bottom, margin = 32.dp)
                            centerHorizontallyTo(parent)
                        }
                ) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp),
                        onClick = {  },
                        shape = RoundedCornerShape(30.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Green
                        ),
                        contentPadding = PaddingValues(vertical = 10.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.startCourse),
                            style = MaterialTheme.typography.labelLarge,
                            color = White,
                            lineHeight = 20.sp
                        )
                    }

                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp),
                        onClick = {  },
                        shape = RoundedCornerShape(30.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = DarkGray
                        ),
                        contentPadding = PaddingValues(vertical = 10.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.goToPlatform),
                            style = MaterialTheme.typography.labelLarge,
                            color = White,
                            lineHeight = 20.sp
                        )
                    }
                }

                Text(
                    text = stringResource(R.string.aboutCourse),
                    style = MaterialTheme.typography.titleLarge,
                    lineHeight = 28.sp,
                    color = White,
                    modifier = Modifier
                        .constrainAs(courseScreenAboutCourseTitle) {
                            top.linkTo(courseScreenControlsContainer.bottom, margin = 28.dp)
                            start.linkTo(parent.start)
                        }
                )

                LazyColumn(
                    modifier = Modifier
                        .constrainAs(courseScreenAboutCourseText) {
                            top.linkTo(courseScreenAboutCourseTitle.bottom, margin = 12.dp)
                            start.linkTo(parent.start)
                            bottom.linkTo(parent.bottom)
                            height = Dimension.fillToConstraints
                        }
                ) {
                    item {
                        if (isLoading) {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = "",
                                    lineHeight = 12.sp,
                                    modifier = Modifier
                                        .width(170.dp)
                                        .placeholder(
                                            visible = true,
                                            highlight = PlaceholderHighlight.fade(),
                                            color = DarkGray.copy(alpha = 0.5f),
                                            shape = RoundedCornerShape(28.dp)
                                        )
                                )

                                Text(
                                    text = "",
                                    lineHeight = 12.sp,
                                    modifier = Modifier
                                        .width(220.dp)
                                        .placeholder(
                                            visible = true,
                                            highlight = PlaceholderHighlight.fade(),
                                            color = DarkGray.copy(alpha = 0.5f),
                                            shape = RoundedCornerShape(28.dp)
                                        )
                                )

                                Text(
                                    text = "",
                                    lineHeight = 12.sp,
                                    modifier = Modifier
                                        .width(120.dp)
                                        .placeholder(
                                            visible = true,
                                            highlight = PlaceholderHighlight.fade(),
                                            color = DarkGray.copy(alpha = 0.5f),
                                            shape = RoundedCornerShape(28.dp)
                                        )
                                )
                            }
                        } else {
                            Text(
                                text = course.text,
                                style = MaterialTheme.typography.bodyMedium,
                                color = DescriptionTextColor,
                                lineHeight = 20.sp,
                                letterSpacing = 0.25.sp,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun CourseScreenPreview() {
    CourseScreenContent(
        course =
            CourseUI(
                id = 1,
                title = "Java-разработчик с нуля",
                text = "У вас будет 7 видеоуроков в высоком качестве. На них спикер объясняет теорию и показывает как выполнять практические задания. Доступ к материалам сохраняется на 2 года\n" +
                        "Кроме теоретических материалов вас ждут тесты и практические задания. Они помогут лучше запомнить новую информацию и прокачать навыки, которые необходимы для реальной работы с RabbitMQ.",
                price = "999",
                rate = "4.9",
                startDate = "29-01-2026",
                hasLike = true,
                publishDate = "21-01-2026"
            ),
        isLoading = true,
        error = null,
        onBackClick = { },
        onSavedClick = { _, _ -> }
    )
}