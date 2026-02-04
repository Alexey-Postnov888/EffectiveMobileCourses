package ru.alexeypostnov.effectivemobilecourses.feature.favourites.presentation

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.alexeypostnov.effectivemobilecourses.core.domain.usecase.GetFavouritesCourses
import ru.alexeypostnov.effectivemobilecourses.core.domain.usecase.UpdateFavouriteStatusByCourseIdUseCase
import ru.alexeypostnov.effectivemobilecourses.core.ui.base.BaseViewModel
import ru.alexeypostnov.effectivemobilecourses.core.ui.mapper.toUi

class FavouritesViewModel(
    private val getFavouritesCourses: GetFavouritesCourses,
    private val updateFavouriteStatusByCourseIdUseCase: UpdateFavouriteStatusByCourseIdUseCase
): BaseViewModel() {
    init {
        loadCourses()
    }

    fun loadCourses() {
        viewModelScope.launch {
            getFavouritesCourses()
                .map { courses ->
                    courses.map { it.toUi() }
                }
                .onStart {
                    _isLoading.value = true
                    _error.value = null
                }
                .catch { e ->
                    _error.value = "Ошибка загрузки курсов"
                    emit(emptyList())
                }
                .collect {
                    _courses.value = it
                    _isLoading.value = false
                }
        }
    }

    fun updateSavedStatus(courseId: Int, hasLike: Boolean) {
        updateFavouriteStatus(
            updateFavouriteStatusByCourseIdUseCase = updateFavouriteStatusByCourseIdUseCase,
            courseId = courseId,
            hasLike = hasLike
        )
    }
}