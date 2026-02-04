package ru.alexeypostnov.effectivemobilecourses.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eygraber.compose.placeholder.PlaceholderHighlight
import com.eygraber.compose.placeholder.material3.fade
import com.eygraber.compose.placeholder.placeholder
import ru.alexeypostnov.effectivemobilecourses.core.ui.R
import ru.alexeypostnov.effectivemobilecourses.core.ui.theme.DarkGray
import ru.alexeypostnov.effectivemobilecourses.core.ui.theme.Green
import ru.alexeypostnov.effectivemobilecourses.core.ui.theme.LightGray
import ru.alexeypostnov.effectivemobilecourses.core.ui.theme.White

@Composable
fun RateBadgeComponent(modifier: Modifier = Modifier, rate: String, isLoading: Boolean) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
            .height(22.dp)
            .background(
                shape = RoundedCornerShape(28.dp),
                color = DarkGray.copy(alpha = 0.6f)
            )
            .padding(vertical = 4.dp, horizontal = 6.dp)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.star_fill),
            contentDescription = stringResource(R.string.rate),
            tint = Green
        )
        Text(
            text = rate,
            fontSize = 12.sp,
            color = White,
            lineHeight = 14.sp,
            letterSpacing = 0.4.sp,
            modifier = Modifier
                .widthIn(min = 15.dp)
                .placeholder(
                    visible = isLoading,
                    highlight = PlaceholderHighlight.fade(),
                    color = LightGray.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(28.dp)
                )
        )
    }
}