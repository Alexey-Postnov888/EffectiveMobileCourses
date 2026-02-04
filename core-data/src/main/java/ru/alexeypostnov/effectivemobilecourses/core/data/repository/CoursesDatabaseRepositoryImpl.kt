package ru.alexeypostnov.effectivemobilecourses.core.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.alexeypostnov.effectivemobilecourses.core.data.db.CoursesDAO
import ru.alexeypostnov.effectivemobilecourses.core.data.mapper.toDomain
import ru.alexeypostnov.effectivemobilecourses.core.data.mapper.toEntity
import ru.alexeypostnov.effectivemobilecourses.core.domain.model.Course
import ru.alexeypostnov.effectivemobilecourses.core.domain.repository.CoursesDatabaseRepository

class CoursesDatabaseRepositoryImpl(
    private val coursesDAO: CoursesDAO
): CoursesDatabaseRepository {
    override suspend fun upsertCourse(course: Course) {
        coursesDAO.upsertCourse(course.toEntity())
    }

    override suspend fun upsertCourses(courses: List<Course>) {
        coursesDAO.upsertCourses(courses.map { it.toEntity() })
    }

    override fun getCourses(): Flow<List<Course>> =
        coursesDAO.getCourses().map { entities -> entities.map { it.toDomain() } }

    override fun getCourseById(courseId: Int): Flow<Course?> =
        coursesDAO.getCourseById(courseId).map { courseEntity -> courseEntity?.toDomain() }

    override fun getFavouritesCourses(): Flow<List<Course>> =
        coursesDAO.getFavouritesCourses().map { entities -> entities.map { it.toDomain() } }

    override suspend fun updateFavouriteStatusByCourseId(
        courseId: Int,
        hasLike: Boolean
    ) {
        coursesDAO.updateFavouriteStatusByCourseId(courseId, hasLike)
    }
}