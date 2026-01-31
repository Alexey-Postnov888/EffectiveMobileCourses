package ru.alexeypostnov.effectivemobilecourses.feature.login.presentation

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class LoginViewModel(

): ViewModel() {
    private val _email = MutableStateFlow<String>("")
    val email: StateFlow<String> get() = _email

    private val _isEmailValid = MutableStateFlow<Boolean>(false)

    private val _password = MutableStateFlow<String>("")
    val password: StateFlow<String> get() = _password

    val isValid = combine(_email, _isEmailValid, _password) { email, isEmailValid, password ->
        email.isNotBlank() && isEmailValid && password.isNotBlank()
    }.stateIn(
        initialValue = false,
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000)
    )
    val emailPattern = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$")
    val emailInputRegex = Regex("[A-Za-z0-9._%+-@]")

    fun updateEmail(newEmail: String) {
        val filteredEmail = newEmail.filter {
            emailInputRegex.matches(it.toString())
        }

        _email.value = filteredEmail

        _isEmailValid.value = emailPattern.matches(filteredEmail)
    }

    fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }

    fun openVkInBrowser(context: Context) {
        openUrlInBrowser(context, "https://vk.ru/")
    }

    fun openOkInBrowser(context: Context) {
        openUrlInBrowser(context, "https://ok.ru/")
    }

    private fun openUrlInBrowser(context: Context, url: String) {
        val intent = Intent(Intent.ACTION_VIEW, url.toUri())
        context.startActivity(intent)
    }
}