package ru.alexeypostnov.effectivemobilecourses.core.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import ru.alexeypostnov.effectivemobilecourses.core.data.mapper.toDomain
import ru.alexeypostnov.effectivemobilecourses.core.data.service.CoursesService
import ru.alexeypostnov.effectivemobilecourses.core.domain.model.Course
import ru.alexeypostnov.effectivemobilecourses.core.domain.repository.CoursesDatabaseRepository
import ru.alexeypostnov.effectivemobilecourses.core.domain.repository.CoursesRepository

class CoursesRepositoryImpl(
    private val service: CoursesService,
    private val coursesDatabaseRepository: CoursesDatabaseRepository
): CoursesRepository {
    override suspend fun getCourses(): Flow<List<Course>> = flow {
        val cachedCourses = try {
            coursesDatabaseRepository.getCourses().firstOrNull() ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }

        if (cachedCourses.isNotEmpty()) {
            emit(cachedCourses)
        }

        try {
            val response = service.getCourses()

            if (response.isSuccessful) {
                val coursesResponse = response.body()
                val courseDTOs = coursesResponse?.courses ?: emptyList()

                val remoteCourses = courseDTOs.map { dto ->
                    val remoteCourse = dto.toDomain()
                    val cachedCourse = cachedCourses.firstOrNull { it.id == remoteCourse.id }

                    if (cachedCourse != null) {
                        remoteCourse.copy(hasLike = cachedCourse.hasLike)
                    } else {
                        remoteCourse
                    }
                }

                coursesDatabaseRepository.upsertCourses(remoteCourses)
            }

            emitAll(coursesDatabaseRepository.getCourses())
        } catch (e: Exception) {
            if (cachedCourses.isEmpty()) {
                emit(emptyList())
            }
        }

        emitAll(coursesDatabaseRepository.getCourses())
    }

    override fun getCourseById(courseId: Int): Flow<Course?> = flow {
        val cachedCourse = try {
            coursesDatabaseRepository.getCourseById(courseId).firstOrNull()
        } catch (e: Exception) {
            null
        }

        if (cachedCourse != null) {
            emit(cachedCourse)
        }

        try {
            getCourses().collect { remoteCourses ->
                val remoteCourse = remoteCourses.firstOrNull { it.id == courseId }
                if (remoteCourse != null) {
                    emit(remoteCourse)
                    return@collect
                }
            }
        } catch (e: Exception) {
            if (cachedCourse == null) {
                emit(null)
            }
        }

        emitAll(coursesDatabaseRepository.getCourseById(courseId))
    }
}