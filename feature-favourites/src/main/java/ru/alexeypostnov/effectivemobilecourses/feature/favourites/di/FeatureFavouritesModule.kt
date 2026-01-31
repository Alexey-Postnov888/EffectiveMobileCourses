package ru.alexeypostnov.effectivemobilecourses.feature.favourites.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.alexeypostnov.effectivemobilecourses.feature.favourites.presentation.FavouritesViewModel

private val featureFavouritesViewModelModule = module {
    viewModel { FavouritesViewModel(get()) }
}

val featureFavouritesModule = module {
    includes(featureFavouritesViewModelModule)
}