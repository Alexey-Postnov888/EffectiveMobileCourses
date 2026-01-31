package ru.alexeypostnov.effectivemobilecourses.core.domain.usecase

import kotlinx.coroutines.withContext
import ru.alexeypostnov.effectivemobilecourses.core.domain.dispatchers.DispatchersProvider
import ru.alexeypostnov.effectivemobilecourses.core.domain.model.Course
import ru.alexeypostnov.effectivemobilecourses.core.domain.repository.CoursesRepository

interface GetCoursesListUseCase {
    suspend operator fun invoke(): List<Course>
}

class GetCoursesListUseCaseImpl(
    private val repository: CoursesRepository,
    private val dispatchersProvider: DispatchersProvider
): GetCoursesListUseCase {
    override suspend fun invoke(): List<Course> =
        withContext(dispatchersProvider.io) {
            try {
                val courses = repository.getCourses()
                courses
            } catch (e: Exception) {
                emptyList()
            }
        }
}