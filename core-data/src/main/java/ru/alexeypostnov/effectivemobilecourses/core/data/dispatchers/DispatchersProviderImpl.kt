package ru.alexeypostnov.effectivemobilecourses.core.data.dispatchers

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import ru.alexeypostnov.effectivemobilecourses.core.domain.dispatchers.DispatchersProvider


class DispatchersProviderImpl: DispatchersProvider {
    override val io: CoroutineDispatcher
        get() = Dispatchers.IO
    override val main: CoroutineDispatcher
        get() = Dispatchers.Main
    override val default: CoroutineDispatcher
        get() = Dispatchers.Default
}