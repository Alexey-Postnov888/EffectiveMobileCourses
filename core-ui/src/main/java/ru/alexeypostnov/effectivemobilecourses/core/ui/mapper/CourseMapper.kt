package ru.alexeypostnov.effectivemobilecourses.core.ui.mapper

import ru.alexeypostnov.effectivemobilecourses.core.domain.model.Course
import ru.alexeypostnov.effectivemobilecourses.core.ui.model.CourseUI
import java.text.SimpleDateFormat
import java.util.Locale

fun Course.toUi(): CourseUI = CourseUI(
    id = id,
    title = title,
    text = text,
    price = price,
    rate = rate,
    startDate = startDate.formatDate(),
    hasLike = hasLike,
    publishDate = publishDate.formatDate()
)

private fun String.formatDate(): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = inputFormat.parse(this) ?: return this

        val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale("ru", "RU"))
        outputFormat.format(date)
    } catch (e: Exception) {
        this
    }
}