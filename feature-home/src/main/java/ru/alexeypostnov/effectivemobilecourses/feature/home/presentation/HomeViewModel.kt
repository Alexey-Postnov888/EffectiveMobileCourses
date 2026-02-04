package ru.alexeypostnov.effectivemobilecourses.feature.home.presentation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.alexeypostnov.effectivemobilecourses.core.domain.usecase.GetCoursesListUseCase
import ru.alexeypostnov.effectivemobilecourses.core.domain.usecase.UpdateFavouriteStatusByCourseIdUseCase
import ru.alexeypostnov.effectivemobilecourses.core.ui.base.BaseViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HomeViewModel(
    private val getCoursesListUseCase: GetCoursesListUseCase,
    private val updateFavouriteStatusByCourseIdUseCase: UpdateFavouriteStatusByCourseIdUseCase
): BaseViewModel() {
    private val _input = MutableStateFlow<String>("")
    val input: StateFlow<String> get() = _input

    init {
        loadCourses(
            getCoursesListUseCase = getCoursesListUseCase,
        )
    }

    fun updateSavedStatus(courseId: Int, hasLike: Boolean) {
        updateFavouriteStatus(
            updateFavouriteStatusByCourseIdUseCase = updateFavouriteStatusByCourseIdUseCase,
            courseId = courseId,
            hasLike = hasLike
        )
    }

    fun updateInput(newInput: String) {
        _input.value = newInput
    }

    fun sortCoursesByPublishDate() {
        val sortedCourses = _courses.value.sortedByDescending {
            parseDate(it.publishDate)
        }
        _courses.value = sortedCourses
    }

    private fun parseDate(date: String): Date? {
        return try {
            SimpleDateFormat("dd MMMM yyyy", Locale("ru", "RU")).parse(date)
        } catch (e: Exception) {
            _error.value = "Ошибка парсинга даты"
            null
        }
    }
}