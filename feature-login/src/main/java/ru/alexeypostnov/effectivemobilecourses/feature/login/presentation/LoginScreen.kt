package ru.alexeypostnov.effectivemobilecourses.feature.login.presentation

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import ru.alexeypostnov.effectivemobilecourses.core.ui.theme.DarkGray
import ru.alexeypostnov.effectivemobilecourses.core.ui.theme.Green
import ru.alexeypostnov.effectivemobilecourses.core.ui.theme.LightGray
import ru.alexeypostnov.effectivemobilecourses.core.ui.theme.OnLightGray
import ru.alexeypostnov.effectivemobilecourses.core.ui.theme.Stroke
import ru.alexeypostnov.effectivemobilecourses.core.ui.theme.White
import ru.alexeypostnov.effectivemobilecourses.feature.login.R

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit
) {
    val viewModel: LoginViewModel = koinViewModel()

    val email by viewModel.email.collectAsStateWithLifecycle()
    val password by viewModel.password.collectAsStateWithLifecycle()
    val isValid by viewModel.isValid.collectAsStateWithLifecycle()

    LoginScreenContent(
        email = email,
        password = password,
        isValid = isValid,
        onEmailChanged = viewModel::updateEmail,
        onPasswordChanged = viewModel::updatePassword,
        onLoginSuccess = onLoginSuccess,
        onVkClick = viewModel::openVkInBrowser,
        onOkClick = viewModel::openOkInBrowser
    )
}

@Composable
fun LoginScreenContent(
    email: String,
    password: String,
    isValid: Boolean,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onLoginSuccess: () -> Unit,
    onVkClick: (Context) -> Unit,
    onOkClick: (Context) -> Unit,
) {
    val context = LocalContext.current

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        val (loginScreenTitle,
            loginScreenEmailTextFieldLabel,
            loginScreenEmailTextField,
            loginScreenPasswordTextFieldLabel,
            loginScreenPasswordTextField,
            loginScreenLoginButton,
            loginScreenSignInContainer,
            loginScreenForgetPassword,
            loginScreenHorizontalDivider,
            loginScreenSocialNetworksContainer) = createRefs()
        Text(
            stringResource(R.string.login),
            modifier = Modifier
                .constrainAs(loginScreenTitle) {
                    top.linkTo(parent.top, margin = 100.dp)
                    start.linkTo(parent.start)
                },
            fontSize = 28.sp,
            color = White
        )
        Text(
            "Email",
            modifier = Modifier
                .constrainAs(loginScreenEmailTextFieldLabel) {
                    top.linkTo(loginScreenTitle.bottom, margin = 28.dp)
                    start.linkTo(parent.start)
                },
            fontSize = 16.sp,
            color = White
        )
        TextField(
            value = email,
            onValueChange = onEmailChanged,
            placeholder = {
                Text(
                    "example@gmail.com",
                    style = MaterialTheme.typography.bodyMedium,
                    lineHeight = 20.sp,
                    letterSpacing = 0.25.sp,
                    color = OnLightGray
                )
            },
            shape = RoundedCornerShape(30.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = LightGray,
                focusedContainerColor = LightGray,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
            ),
            textStyle = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .constrainAs(loginScreenEmailTextField) {
                    top.linkTo(loginScreenEmailTextFieldLabel.bottom, margin = 8.dp)
                    centerHorizontallyTo(parent)
                },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            )
        )
        Text(
            stringResource(R.string.password),
            modifier = Modifier
                .constrainAs(loginScreenPasswordTextFieldLabel) {
                    top.linkTo(loginScreenEmailTextField.bottom, margin = 16.dp)
                    start.linkTo(parent.start)
                },
            fontSize = 16.sp,
            color = White
        )

        var passwordVisible by remember { mutableStateOf(false) }

        TextField(
            value = password,
            onValueChange = onPasswordChanged,
            placeholder = {
                Text(
                    stringResource(R.string.enterPassword),
                    style = MaterialTheme.typography.bodyMedium,
                    lineHeight = 20.sp,
                    letterSpacing = 0.25.sp,
                    color = OnLightGray
                )
            },
            shape = RoundedCornerShape(30.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = LightGray,
                focusedContainerColor = LightGray,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
            ),
            textStyle = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .constrainAs(loginScreenPasswordTextField) {
                    top.linkTo(loginScreenPasswordTextFieldLabel.bottom, margin = 8.dp)
                    centerHorizontallyTo(parent)
                },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(
                    onClick = {
                        passwordVisible = !passwordVisible
                    }
                ) {
                    Icon(
                        imageVector =
                            if (passwordVisible)
                                ImageVector.vectorResource(R.drawable.visibility_24)
                            else
                                ImageVector.vectorResource(R.drawable.visibility_off_24),
                        contentDescription =
                            if (passwordVisible)
                                stringResource(R.string.showPassword)
                            else
                                stringResource(R.string.hidePassword),
                        tint = OnLightGray
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            )
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .constrainAs(loginScreenLoginButton) {
                    top.linkTo(loginScreenPasswordTextField.bottom, margin = 24.dp)
                    start.linkTo(parent.start)
                },
            onClick = { onLoginSuccess() },
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Green,
                disabledContainerColor = DarkGray
            ),
            contentPadding = PaddingValues(vertical = 10.dp),
            enabled = isValid
        ) {
            Text(
                text = stringResource(R.string.login),
                style = MaterialTheme.typography.labelLarge,
                color = White,
                lineHeight = 20.sp
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .wrapContentWidth()
                .constrainAs(loginScreenSignInContainer) {
                    top.linkTo(loginScreenLoginButton.bottom, margin = 16.dp)
                    centerHorizontallyTo(parent)
                }
        ) {
            Text(
                text = stringResource(R.string.haveNotAccount),
                fontSize = 12.sp,
                color = White
            )
            Text(
                text = stringResource(R.string.signIn),
                fontSize = 12.sp,
                color = Green,
            )
        }

        Text(
            text = stringResource(R.string.forgotPassword),
            modifier = Modifier
                .constrainAs(loginScreenForgetPassword) {
                    top.linkTo(loginScreenSignInContainer.bottom, margin = 8.dp)
                    centerHorizontallyTo(parent)
                },
            fontSize = 12.sp,
            color = Green,
        )

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(loginScreenHorizontalDivider) {
                    top.linkTo(loginScreenForgetPassword.bottom, margin = 32.dp)
                    centerHorizontallyTo(parent)
                },
            color = Stroke
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(loginScreenSocialNetworksContainer) {
                    top.linkTo(loginScreenHorizontalDivider.bottom, margin = 32.dp)
                    centerHorizontallyTo(parent)
                }
        ) {
            IconButton(
                onClick = { onVkClick(context) },
                modifier = Modifier
                    .height(40.dp)
                    .weight(1f),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color(0xFF2683ED)
                )
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.vk),
                    contentDescription = stringResource(R.string.vk),
                    tint = White
                )
            }

            IconButton(
                onClick = { onOkClick(context) },
                modifier = Modifier
                    .height(40.dp)
                    .weight(1f),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color(0xFFF98509)
                )
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ok),
                    contentDescription = stringResource(R.string.ok),
                    tint = White
                )
            }
        }
    }
}

@Composable
@Preview
fun LoginScreenPreview() {
    LoginScreenContent(
        email = "",
        password = "",
        isValid = false,
        onEmailChanged = { },
        onPasswordChanged = { },
        onLoginSuccess = { },
        onVkClick = { }
    ) { }
}