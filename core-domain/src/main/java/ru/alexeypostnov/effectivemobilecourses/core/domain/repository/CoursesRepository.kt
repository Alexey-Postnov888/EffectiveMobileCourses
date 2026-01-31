package ru.alexeypostnov.effectivemobilecourses.core.domain.repository

import ru.alexeypostnov.effectivemobilecourses.core.domain.model.Course

interface CoursesRepository {
    suspend fun getCourses(): List<Course>
}