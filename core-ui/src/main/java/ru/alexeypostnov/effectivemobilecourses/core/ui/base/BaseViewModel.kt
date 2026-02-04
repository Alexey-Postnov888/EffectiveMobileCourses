package ru.alexeypostnov.effectivemobilecourses.core.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.alexeypostnov.effectivemobilecourses.core.domain.usecase.GetCoursesListUseCase
import ru.alexeypostnov.effectivemobilecourses.core.domain.usecase.UpdateFavouriteStatusByCourseIdUseCase
import ru.alexeypostnov.effectivemobilecourses.core.ui.mapper.toUi
import ru.alexeypostnov.effectivemobilecourses.core.ui.model.CourseUI

abstract class BaseViewModel: ViewModel() {
    protected val _courses = MutableStateFlow<List<CourseUI>>(emptyList())
    val courses: StateFlow<List<CourseUI>> get() = _courses.asStateFlow()

    protected val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading.asStateFlow()

    protected val _error = MutableStateFlow<String?>("")
    val error: StateFlow<String?> get() = _error.asStateFlow()


    protected fun loadCourses(
        getCoursesListUseCase: GetCoursesListUseCase,
        errorMessage: String = "Ошибка загрузки курсов"
    ) {
        if (_isLoading.value) return

        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                getCoursesListUseCase().collectLatest { courses ->
                    _courses.value = courses.map { it.toUi() }
                    _isLoading.value = false
                    _error.value = null
                }
            } catch (e: Exception) {
                if (_courses.value.isEmpty()) {
                    _error.value = errorMessage
                }
            }
        }
    }

    protected fun updateFavouriteStatus(
        updateFavouriteStatusByCourseIdUseCase: UpdateFavouriteStatusByCourseIdUseCase,
        courseId: Int,
        hasLike: Boolean
    ) {
        viewModelScope.launch {
            updateFavouriteStatusByCourseIdUseCase(courseId, hasLike)
        }
    }
}