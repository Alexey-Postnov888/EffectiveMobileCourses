package ru.alexeypostnov.effectivemobilecourses.feature.account.presentation

import ru.alexeypostnov.effectivemobilecourses.core.domain.usecase.GetCoursesListUseCase
import ru.alexeypostnov.effectivemobilecourses.core.domain.usecase.UpdateFavouriteStatusByCourseIdUseCase
import ru.alexeypostnov.effectivemobilecourses.core.ui.base.BaseViewModel

class AccountViewModel(
    private val getCoursesListUseCase: GetCoursesListUseCase,
    private val updateFavouriteStatusByCourseIdUseCase: UpdateFavouriteStatusByCourseIdUseCase
): BaseViewModel() {
    init {
        loadCourses(
            getCoursesListUseCase = getCoursesListUseCase
        )
    }

    fun updateSavedStatus(courseId: Int, hasLike: Boolean) {
        updateFavouriteStatus(
            updateFavouriteStatusByCourseIdUseCase = updateFavouriteStatusByCourseIdUseCase,
            courseId = courseId,
            hasLike = hasLike
        )
    }
}