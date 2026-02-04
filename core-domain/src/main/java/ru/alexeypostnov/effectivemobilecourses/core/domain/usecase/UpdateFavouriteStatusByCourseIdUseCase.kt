package ru.alexeypostnov.effectivemobilecourses.core.domain.usecase

import ru.alexeypostnov.effectivemobilecourses.core.domain.repository.CoursesDatabaseRepository

interface UpdateFavouriteStatusByCourseIdUseCase {
    suspend operator fun invoke(courseId: Int, hasLike: Boolean)
}

class UpdateFavouriteStatusByCourseIdUseCaseImpl(
    private val repository: CoursesDatabaseRepository
): UpdateFavouriteStatusByCourseIdUseCase {
    override suspend fun invoke(courseId: Int, hasLike: Boolean) {
        repository.updateFavouriteStatusByCourseId(courseId, hasLike)
    }
}