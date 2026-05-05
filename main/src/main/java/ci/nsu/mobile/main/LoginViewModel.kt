package ci.nsu.mobile.auth.ui.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel : ViewModel() {

    private val _login = MutableStateFlow("")
    val login: StateFlow<String> = _login.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    fun updateLogin(value: String) {
        _login.update { value }
    }

    fun updatePassword(value: String) {
        _password.update { value }
    }

    fun onLoginClick() {
        // TODO: реализовать вход в систему
    }
}