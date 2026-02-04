package ru.alexeypostnov.effectivemobilecourses.core.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.alexeypostnov.effectivemobilecourses.core.domain.model.Course

interface CoursesDatabaseRepository {
    suspend fun upsertCourse(course: Course)
    suspend fun upsertCourses(courses: List<Course>)
    fun getCourses(): Flow<List<Course>>
    fun getCourseById(courseId: Int): Flow<Course?>
    fun getFavouritesCourses(): Flow<List<Course>>
    suspend fun updateFavouriteStatusByCourseId(courseId: Int, hasLike: Boolean)
}