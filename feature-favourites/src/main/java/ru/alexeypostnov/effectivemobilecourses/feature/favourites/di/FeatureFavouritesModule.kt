package ru.alexeypostnov.effectivemobilecourses.feature.favourites.di

import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.alexeypostnov.effectivemobilecourses.core.domain.usecase.GetFavouritesCourses
import ru.alexeypostnov.effectivemobilecourses.core.domain.usecase.GetFavouritesCoursesImpl
import ru.alexeypostnov.effectivemobilecourses.core.domain.usecase.UpdateFavouriteStatusByCourseIdUseCase
import ru.alexeypostnov.effectivemobilecourses.core.domain.usecase.UpdateFavouriteStatusByCourseIdUseCaseImpl
import ru.alexeypostnov.effectivemobilecourses.feature.favourites.presentation.FavouritesViewModel

private val featureFavouritesViewModelModule = module {
    viewModel { FavouritesViewModel(get(), get()) }
}

private val featureFavouritesUseCaseModule = module {
    singleOf(::GetFavouritesCoursesImpl) bind GetFavouritesCourses::class
    singleOf(::UpdateFavouriteStatusByCourseIdUseCaseImpl) bind UpdateFavouriteStatusByCourseIdUseCase::class
}

val featureFavouritesModule = module {
    includes(
        featureFavouritesUseCaseModule,
        featureFavouritesViewModelModule
    )
}