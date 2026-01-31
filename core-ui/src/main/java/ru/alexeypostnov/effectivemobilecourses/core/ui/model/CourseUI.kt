package ru.alexeypostnov.effectivemobilecourses.core.ui.model

data class CourseUI(
    val id: Int,
    val title: String,
    val text: String,
    val price: String,
    val rate: String,
    val startDate: String,
    val hasLike: Boolean,
    val publishDate: String
) {
    companion object {
        fun empty() = CourseUI(
            id = 0,
            title = "",
            text = "",
            price = "",
            rate = "",
            startDate = "",
            hasLike = false,
            publishDate = ""
        )
    }
}