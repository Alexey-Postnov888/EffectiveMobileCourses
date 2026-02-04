package ru.alexeypostnov.effectivemobilecourses.navigation.tab

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import ru.alexeypostnov.effectivemobilecourses.R
import ru.alexeypostnov.effectivemobilecourses.core.ui.composition.LocalCourseClick
import ru.alexeypostnov.effectivemobilecourses.feature.account.presentation.AccountScreen

class AccountNavigationTab: Tab {
    @Composable
    override fun Content() {
        val onCourseClick = LocalCourseClick.current

        AccountScreen(onCourseClick = onCourseClick)
    }

    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(R.string.accountTabTitle)
            val icon = rememberVectorPainter(ImageVector.vectorResource(id = R.drawable.person))
            return remember {
                TabOptions(
                    index = 2u,
                    title = title,
                    icon = icon
                )
            }
        }
}