package ru.alexeypostnov.effectivemobilecourses.feature.course.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.alexeypostnov.effectivemobilecourses.feature.course.presentation.CourseViewModel

private val featureCourseViewModelModule = module {
    viewModel { (courseId: Int) ->
        CourseViewModel(courseId, get())
    }
}

val featureCourseModule = module {
    includes(featureCourseViewModelModule)
}