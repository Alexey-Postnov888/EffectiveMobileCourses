package ru.alexeypostnov.effectivemobilecourses.core.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import ru.alexeypostnov.effectivemobilecourses.core.ui.R
import ru.alexeypostnov.effectivemobilecourses.core.ui.theme.OnDarkGray

@Composable
fun ErrorComponent(icon: ImageVector = Icons.Rounded.Info, error: String, modifier: Modifier) {
    ConstraintLayout(
        modifier = modifier
            .wrapContentSize()
    ) {
        val (errorIcon, label) = createRefs()
        Icon(
            imageVector = icon,
            contentDescription = stringResource(R.string.error),
            tint = OnDarkGray,
            modifier = Modifier
                .size(50.dp)
                .constrainAs(errorIcon) {
                    centerHorizontallyTo(parent)
                    centerVerticallyTo(parent)
                }
        )
        Text(
            text = error,
            fontSize = 16.sp,
            color = OnDarkGray,
            modifier = Modifier
                .padding(top = 5.dp)
                .constrainAs(label) {
                    top.linkTo(errorIcon.bottom)
                    centerHorizontallyTo(parent)
                }
        )
    }
}