package ru.alexeypostnov.effectivemobilecourses.core.data.model

import com.google.gson.annotations.SerializedName

data class CoursesResponse(
    @SerializedName("courses")
    val courses: List<CourseDTO>
)