package ru.alexeypostnov.effectivemobilecourses.feature.home.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.alexeypostnov.effectivemobilecourses.feature.home.presentation.HomeViewModel

private val featureHomeViewModelModule = module {
    viewModel { HomeViewModel(get()) }
}
val featureHomeModule = module {
    includes(
        featureHomeViewModelModule
    )
}