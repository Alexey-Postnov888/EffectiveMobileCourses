package ru.alexeypostnov.effectivemobilecourses.core.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.alexeypostnov.effectivemobilecourses.core.domain.model.Course
import ru.alexeypostnov.effectivemobilecourses.core.domain.repository.CoursesRepository

interface GetCourseByIdUseCase {
    operator fun invoke(courseId: Int): Flow<Course?>
}

class GetCourseByIdUseCaseImpl(
    private val repository: CoursesRepository
): GetCourseByIdUseCase {
    override fun invoke(courseId: Int): Flow<Course?> =
        repository.getCourseById(courseId)
}