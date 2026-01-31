package ru.alexeypostnov.effectivemobilecourses.core.data.repository

import ru.alexeypostnov.effectivemobilecourses.core.data.mapper.toDomain
import ru.alexeypostnov.effectivemobilecourses.core.data.service.CoursesService
import ru.alexeypostnov.effectivemobilecourses.core.domain.model.Course
import ru.alexeypostnov.effectivemobilecourses.core.domain.repository.CoursesRepository

class CoursesRepositoryImpl(
    private val service: CoursesService,
): CoursesRepository {
    override suspend fun getCourses(): List<Course> {
        return try {
            val response = service.getCourses()

            if (response.isSuccessful) {
                val coursesResponse = response.body()
                val courseDTOs = coursesResponse?.courses ?: emptyList()
                courseDTOs.map { it.toDomain() }
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

}