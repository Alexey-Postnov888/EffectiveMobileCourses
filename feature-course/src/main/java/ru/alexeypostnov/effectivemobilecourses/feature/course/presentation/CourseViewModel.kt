package ru.alexeypostnov.effectivemobilecourses.feature.course.presentation

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.alexeypostnov.effectivemobilecourses.core.domain.usecase.GetCourseByIdUseCase
import ru.alexeypostnov.effectivemobilecourses.core.domain.usecase.UpdateFavouriteStatusByCourseIdUseCase
import ru.alexeypostnov.effectivemobilecourses.core.ui.base.BaseViewModel
import ru.alexeypostnov.effectivemobilecourses.core.ui.mapper.toUi
import ru.alexeypostnov.effectivemobilecourses.core.ui.model.CourseUI

class CourseViewModel(
    private val courseId: Int,
    private val updateFavouriteStatusByCourseIdUseCase: UpdateFavouriteStatusByCourseIdUseCase,
    private val getCourseByIdUseCase: GetCourseByIdUseCase
): BaseViewModel() {
    private val _course = MutableStateFlow<CourseUI>(CourseUI.empty())
    val course: StateFlow<CourseUI> get() = _course

    init {
        loadCourse()
    }

    private fun loadCourse() {
        if (_isLoading.value) return

        viewModelScope.launch {
            getCourseByIdUseCase(courseId)
                .onStart {
                    _isLoading.value = true
                    _error.value = null
                }
                .catch {
                    _isLoading.value = false
                    _error.value = "Ошибка загрузки курса"
                }
                .collect {
                    _isLoading.value = false

                    it?.let {
                        _course.value = it.toUi()
                    }
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