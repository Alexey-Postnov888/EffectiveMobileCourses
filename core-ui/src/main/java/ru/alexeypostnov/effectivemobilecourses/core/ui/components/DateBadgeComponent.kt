package ru.alexeypostnov.effectivemobilecourses.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eygraber.compose.placeholder.PlaceholderHighlight
import com.eygraber.compose.placeholder.material3.fade
import com.eygraber.compose.placeholder.placeholder
import ru.alexeypostnov.effectivemobilecourses.core.ui.theme.DarkGray
import ru.alexeypostnov.effectivemobilecourses.core.ui.theme.LightGray
import ru.alexeypostnov.effectivemobilecourses.core.ui.theme.White

@Composable
fun DateBadgeComponent(modifier: Modifier = Modifier, isLoading: Boolean, date: String) {
    Text(
        text = date,
        fontSize = 12.sp,
        color = White,
        modifier = modifier
            .height(22.dp)
            .widthIn(min = 70.dp)
            .background(
                shape = RoundedCornerShape(28.dp),
                color = DarkGray.copy(alpha = 0.6f)
            )
            .padding(vertical = 4.dp, horizontal = 6.dp)
            .placeholder(
                visible = isLoading,
                highlight = PlaceholderHighlight.fade(),
                color = LightGray.copy(alpha = 0.5f),
                shape = RoundedCornerShape(28.dp)
            ),
        lineHeight = 14.sp,
        letterSpacing = 0.4.sp

    )
}