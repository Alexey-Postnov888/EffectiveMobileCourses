package ru.alexeypostnov.effectivemobilecourses

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.alexeypostnov.effectivemobilecourses.core.data.di.coreDataModule
import ru.alexeypostnov.effectivemobilecourses.di.appModule
import ru.alexeypostnov.effectivemobilecourses.feature.account.di.featureAccountModule
import ru.alexeypostnov.effectivemobilecourses.feature.course.di.featureCourseModule
import ru.alexeypostnov.effectivemobilecourses.feature.favourites.di.featureFavouritesModule
import ru.alexeypostnov.effectivemobilecourses.feature.home.di.featureHomeModule
import ru.alexeypostnov.effectivemobilecourses.feature.login.di.featureLoginModule

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApplication)
            modules(
                appModule,
                coreDataModule,
                featureLoginModule,
                featureHomeModule,
                featureFavouritesModule,
                featureAccountModule,
                featureCourseModule
            )
        }
    }
}