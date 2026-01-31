package ru.alexeypostnov.effectivemobilecourses.feature.login.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.alexeypostnov.effectivemobilecourses.feature.login.presentation.LoginViewModel

private val featureLoginViewModelModule = module {
    viewModel { LoginViewModel() }
}
val featureLoginModule = module {
    includes(featureLoginViewModelModule)
}