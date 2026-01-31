package ru.alexeypostnov.effectivemobilecourses.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.alexeypostnov.effectivemobilecourses.core.domain.usecase.GetCoursesListUseCase
import ru.alexeypostnov.effectivemobilecourses.core.domain.usecase.GetCoursesListUseCaseImpl


private val useCaseModule = module {
    singleOf(::GetCoursesListUseCaseImpl) bind GetCoursesListUseCase::class
}

val appModule = module {
    includes(useCaseModule)
}