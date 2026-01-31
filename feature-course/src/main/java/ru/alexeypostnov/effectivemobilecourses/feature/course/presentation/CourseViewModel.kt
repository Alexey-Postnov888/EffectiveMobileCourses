package ru.alexeypostnov.effectivemobilecourses.feature.course.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.alexeypostnov.effectivemobilecourses.core.domain.usecase.GetCoursesListUseCase
import ru.alexeypostnov.effectivemobilecourses.core.ui.mapper.toUi
import ru.alexeypostnov.effectivemobilecourses.core.ui.model.CourseUI

class CourseViewModel(
    private val courseId: Int,
    private val getCoursesListUseCase: GetCoursesListUseCase
): ViewModel() {
    private val _course = MutableStateFlow<CourseUI>(CourseUI.empty())
    val course: StateFlow<CourseUI> get() = _course

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>("")
    val error: StateFlow<String?> get() = _error.asStateFlow()

    init {
        loadCourse()
    }

    fun loadCourse() {
        if (_isLoading.value) return

        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                val coursesList = getCoursesListUseCase().map { it.toUi() }
                _course.value = coursesList.first { it.id == courseId }
            } catch (e: Exception) {
                if (_course.value == CourseUI.empty()) {
                    _error.value = "Ошибка загрузки информации о курсе"
                }
            } finally {
                _isLoading.value = false
            }
        }
    }
}