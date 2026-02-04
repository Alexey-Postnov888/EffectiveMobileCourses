package ru.alexeypostnov.effectivemobilecourses.core.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.alexeypostnov.effectivemobilecourses.core.domain.model.Course

interface CoursesRepository {
    suspend fun getCourses(): Flow<List<Course>>
    fun getCourseById(courseId: Int): Flow<Course?>
}