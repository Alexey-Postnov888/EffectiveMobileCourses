package ru.alexeypostnov.effectivemobilecourses.core.ui.mapper

import ru.alexeypostnov.effectivemobilecourses.core.domain.model.Course
import ru.alexeypostnov.effectivemobilecourses.core.ui.model.CourseUI

fun Course.toUi(): CourseUI = CourseUI(
    id = id,
    title = title,
    text = text,
    price = price,
    rate = rate,
    startDate = startDate,
    hasLike = hasLike,
    publishDate = publishDate
)