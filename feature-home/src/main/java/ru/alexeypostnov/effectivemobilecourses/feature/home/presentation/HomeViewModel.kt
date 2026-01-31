package ru.alexeypostnov.effectivemobilecourses.feature.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.alexeypostnov.effectivemobilecourses.core.domain.usecase.GetCoursesListUseCase
import ru.alexeypostnov.effectivemobilecourses.core.ui.mapper.toUi
import ru.alexeypostnov.effectivemobilecourses.core.ui.model.CourseUI
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HomeViewModel(
    private val getCoursesListUseCase: GetCoursesListUseCase
): ViewModel() {
    private val _input = MutableStateFlow<String>("")
    val input: StateFlow<String> get() = _input

    private val _courses = MutableStateFlow<List<CourseUI>>(emptyList())
    val courses: StateFlow<List<CourseUI>> get() = _courses.asStateFlow()

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>("")
    val error: StateFlow<String?> get() = _error.asStateFlow()

    init {
        loadCourses()
    }

    fun loadCourses() {
        if (_isLoading.value) return

        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                _courses.value = getCoursesListUseCase().map { it.toUi() }
            } catch (e: Exception) {
                if (_courses.value.isEmpty()) {
                    _error.value = "Ошибка загрузки курсов"
                }
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateInput(newInput: String) {
        _input.value = newInput
    }

    fun sortCoursesByPublishDate() {
        val sortedCourses = _courses.value.sortedByDescending {
            parseDate(it.publishDate)
        }
        _courses.value = sortedCourses
//        _courses.update { currentCourses ->
//            currentCourses.sortedByDescending { course ->
//                parseDate(course.publishDate)
//            }
//        }
    }

    private fun parseDate(date: String): Date? {
        return try {
            SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse(date)
        } catch (e: Exception) {
            _error.value = "Ошибка парсинга даты"
            null
        }
    }
}