package ru.alexeypostnov.effectivemobilecourses.core.data.di

import androidx.room.Room
import okhttp3.OkHttpClient
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import ru.alexeypostnov.effectivemobilecourses.core.data.db.CoursesDAO
import ru.alexeypostnov.effectivemobilecourses.core.data.db.CoursesDatabase
import ru.alexeypostnov.effectivemobilecourses.core.data.dispatchers.DispatchersProviderImpl
import ru.alexeypostnov.effectivemobilecourses.core.data.repository.CoursesDatabaseRepositoryImpl
import ru.alexeypostnov.effectivemobilecourses.core.data.repository.CoursesRepositoryImpl
import ru.alexeypostnov.effectivemobilecourses.core.data.service.CoursesService
import ru.alexeypostnov.effectivemobilecourses.core.domain.dispatchers.DispatchersProvider
import ru.alexeypostnov.effectivemobilecourses.core.domain.repository.CoursesDatabaseRepository
import ru.alexeypostnov.effectivemobilecourses.core.domain.repository.CoursesRepository
import java.util.concurrent.TimeUnit

private val networkModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://drive.usercontent.google.com/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create<CoursesService>()
    } bind CoursesService::class

    single {
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    } bind OkHttpClient::class
}

private val databaseModule = module {
    single {
        Room.databaseBuilder(
            get(),
            CoursesDatabase::class.java,
            "courses.db"
        ).build()
    }

    single { get<CoursesDatabase>().coursesDAO } bind CoursesDAO::class
}

private val repositoryModule = module {
    singleOf(::CoursesRepositoryImpl) bind CoursesRepository::class
    singleOf(::CoursesDatabaseRepositoryImpl) bind CoursesDatabaseRepository::class
}

private val dispatchersModule = module {
    singleOf(::DispatchersProviderImpl) bind DispatchersProvider::class
}

val coreDataModule = module {
    includes(
        networkModule,
        dispatchersModule,
        databaseModule,
        repositoryModule
    )
}