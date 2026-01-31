package ru.alexeypostnov.effectivemobilecourses.core.data.mapper

import ru.alexeypostnov.effectivemobilecourses.core.data.model.CourseDTO
import ru.alexeypostnov.effectivemobilecourses.core.domain.model.Course

fun CourseDTO.toDomain(): Course = Course(
    id = id,
    title = title,
    text = text,
    price = price,
    rate = rate,
    startDate = startDate,
    hasLike = hasLike,
    publishDate = publishDate
)