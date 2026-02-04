package ru.alexeypostnov.effectivemobilecourses.core.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.alexeypostnov.effectivemobilecourses.core.domain.dispatchers.DispatchersProvider
import ru.alexeypostnov.effectivemobilecourses.core.domain.model.Course
import ru.alexeypostnov.effectivemobilecourses.core.domain.repository.CoursesRepository

interface GetCoursesListUseCase {
    suspend operator fun invoke(): Flow<List<Course>>
}

class GetCoursesListUseCaseImpl(
    private val repository: CoursesRepository,
    private val dispatchersProvider: DispatchersProvider
): GetCoursesListUseCase {
    override suspend fun invoke(): Flow<List<Course>> = flow {
        repository.getCourses().collect { emit(it) }
    }
        .catch { e ->
            emit(emptyList())
        }
        .flowOn(dispatchersProvider.io)
}