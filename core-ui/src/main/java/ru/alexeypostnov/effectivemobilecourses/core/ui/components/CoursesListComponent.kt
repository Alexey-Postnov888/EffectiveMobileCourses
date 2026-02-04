package ru.alexeypostnov.effectivemobilecourses.core.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import ru.alexeypostnov.effectivemobilecourses.core.ui.R
import ru.alexeypostnov.effectivemobilecourses.core.ui.model.CourseUI
import ru.alexeypostnov.effectivemobilecourses.core.ui.theme.DarkGray
import ru.alexeypostnov.effectivemobilecourses.core.ui.theme.Green
import ru.alexeypostnov.effectivemobilecourses.core.ui.theme.OnDarkGray
import ru.alexeypostnov.effectivemobilecourses.core.ui.theme.White

@Composable
fun CoursesListComponent(
    modifier: Modifier = Modifier,
    items: List<CourseUI>,
    isLoading: Boolean,
    error: String?,
    onCourseClick: (courseId: Int) -> Unit,
    listState: LazyListState = rememberLazyListState(),
    onSavedClick: (courseId: Int, hasLike: Boolean) -> Unit
) {
    ConstraintLayout(
        modifier = modifier
    ) {
        val (loadingComponent,
            errorComponent,
            coursesList) = createRefs()

        if (isLoading) {
            LoadingComponent(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(loadingComponent) {
                        top.linkTo(parent.top)
                    },
                color = Green
            )
        }

        when {
            !error.isNullOrEmpty() -> {
                ErrorComponent(
                    error = error,
                    modifier = Modifier
                        .wrapContentSize()
                        .constrainAs(errorComponent) {
                            centerHorizontallyTo(parent)
                            centerVerticallyTo(parent)
                        }
                )
            }

            !isLoading && items.isEmpty() -> {
                ErrorComponent(error = stringResource(R.string.coursesIsNotFound), modifier = Modifier
                    .constrainAs(errorComponent) {
                        centerHorizontallyTo(parent)
                        centerVerticallyTo(parent)
                    })
            }

            else -> {
                LazyColumn(
                    state = listState,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(coursesList) {
                            top.linkTo(parent.top)
                            centerHorizontallyTo(parent)
                            bottom.linkTo(parent.bottom)
                            height = Dimension.fillToConstraints
                        },
                ) {
                    items(
                        items = items,
                        key = { item -> item.id }
                    ) { course ->
                        CourseCard(
                            course = course,
                            onCourseClick = onCourseClick,
                            isLoading = isLoading,
                            onSavedClick = onSavedClick
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CourseCard(
    course: CourseUI,
    isLoading: Boolean,
    onCourseClick: (courseId: Int) -> Unit,
    onSavedClick: (courseId: Int, hasLike: Boolean) -> Unit
) {
    Card(
        onClick = { onCourseClick(course.id) },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = DarkGray
        ),
    ) {
        Column (
            modifier = Modifier
        ) {
            ConstraintLayout(
                modifier = Modifier
            ) {
                val (courseImage,
                    favouriteButton,
                    rateChip,
                    startDateChip) = createRefs()

                Image(
                    bitmap = ImageBitmap.imageResource(R.drawable.cover),
                    contentDescription = stringResource(R.string.imageOfCourse),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(114.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .constrainAs(courseImage) {
                            top.linkTo(parent.top)
                        },
                    contentScale = ContentScale.Crop,
                )

                IconButton(
                    onClick = { onSavedClick(course.id, !course.hasLike) },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = DarkGray.copy(alpha = 0.3f)
                    ),
                    modifier = Modifier
                        .constrainAs(favouriteButton) {
                            top.linkTo(parent.top, margin = 8.dp)
                            end.linkTo(parent.end, margin = 8.dp)
                        }
                        .size(28.dp)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(
                            if (course.hasLike) {
                                R.drawable.saved_fill
                            } else {
                                R.drawable.saved
                            }
                        ),
                        contentDescription = stringResource(R.string.favourites),
                        tint = if (course.hasLike) Green else White,
                        modifier = Modifier
                            .padding(6.dp)
                    )
                }

                RateBadgeComponent(
                    modifier = Modifier
                        .constrainAs(rateChip) {
                            start.linkTo(parent.start, margin = 8.dp)
                            bottom.linkTo(parent.bottom, margin = 8.dp)
                        },
                    rate = course.rate,
                    isLoading = isLoading
                )

                DateBadgeComponent(
                    modifier = Modifier
                        .constrainAs(startDateChip) {
                            start.linkTo(rateChip.end, margin = 4.dp)
                            top.linkTo(rateChip.top)
                        },
                    date = course.startDate,
                    isLoading = isLoading
                )
            }

            ConstraintLayout(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                val (courseTitle,
                    courseText,
                    coursePrice,
                    detailsButton) = createRefs()

                Text(
                    text = course.title,
                    fontSize = 16.sp,
                    color = White,
                    modifier = Modifier
                        .constrainAs(courseTitle) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                        }
                )

                Text(
                    text = course.text,
                    fontSize = 12.sp,
                    color = OnDarkGray,
                    maxLines = 2,
                    letterSpacing = 0.4.sp,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .constrainAs(courseText) {
                            top.linkTo(courseTitle.bottom, margin = 12.dp)
                            start.linkTo(parent.start)
                        }
                )

                Text(
                    text = "${course.price} ₽",
                    fontSize = 16.sp,
                    color = White,
                    modifier = Modifier
                        .constrainAs(coursePrice) {
                            top.linkTo(courseText.bottom, margin = 12.dp)
                            start.linkTo(parent.start)
                        }
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier
                        .constrainAs(detailsButton) {
                            centerVerticallyTo(coursePrice)
                            end.linkTo(parent.end)
                        },
                ) {
                    Text(
                        text = stringResource(R.string.moreDetails),
                        fontSize = 12.sp,
                        color = Green
                    )

                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.arrow_right_short_fill),
                        contentDescription = stringResource(R.string.moreDetails),
                        tint = Green
                    )
                }
            }
        }
    }
}