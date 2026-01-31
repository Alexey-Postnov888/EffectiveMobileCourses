package ru.alexeypostnov.effectivemobilecourses.core.data.service

import retrofit2.Response
import retrofit2.http.GET
import ru.alexeypostnov.effectivemobilecourses.core.data.model.CoursesResponse

interface CoursesService {
    @GET("download?id=15arTK7XT2b7Yv4BJsmDctA4Hg-BbS8-q&export=download")
    suspend fun getCourses(): Response<CoursesResponse>
}