package ru.alexeypostnov.effectivemobilecourses.feature.course.di

import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.alexeypostnov.effectivemobilecourses.core.domain.usecase.GetCourseByIdUseCase
import ru.alexeypostnov.effectivemobilecourses.core.domain.usecase.GetCourseByIdUseCaseImpl
import ru.alexeypostnov.effectivemobilecourses.feature.course.presentation.CourseViewModel

private val featureCourseViewModelModule = module {
    viewModel { (courseId: Int) ->
        CourseViewModel(courseId, get(), get())
    }
}

private val featureCourseUseCaseModule = module {
    singleOf(::GetCourseByIdUseCaseImpl) bind GetCourseByIdUseCase::class
}

val featureCourseModule = module {
    includes(
        featureCourseUseCaseModule,
        featureCourseViewModelModule
    )
}