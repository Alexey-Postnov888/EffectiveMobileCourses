package ru.alexeypostnov.effectivemobilecourses.feature.account.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.alexeypostnov.effectivemobilecourses.feature.account.presentation.AccountViewModel

private val featureAccountViewModelModule = module {
    viewModel { AccountViewModel(get(), get()) }
}

val featureAccountModule = module {
    includes(featureAccountViewModelModule)
}