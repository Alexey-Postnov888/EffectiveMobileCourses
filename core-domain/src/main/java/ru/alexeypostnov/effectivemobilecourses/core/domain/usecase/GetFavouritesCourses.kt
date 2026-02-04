package ru.alexeypostnov.effectivemobilecourses.core.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import ru.alexeypostnov.effectivemobilecourses.core.domain.model.Course
import ru.alexeypostnov.effectivemobilecourses.core.domain.repository.CoursesDatabaseRepository
import kotlin.collections.emptyList

interface GetFavouritesCourses {
    operator fun invoke(): Flow<List<Course>>
}

class GetFavouritesCoursesImpl(
    private val repository: CoursesDatabaseRepository
): GetFavouritesCourses {
    override fun invoke(): Flow<List<Course>> {
        return repository.getFavouritesCourses()
            .catch {
                emit(emptyList())
            }
    }
}