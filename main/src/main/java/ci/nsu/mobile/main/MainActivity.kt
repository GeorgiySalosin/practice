package ci.nsu.mobile.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import ci.nsu.mobile.main.data.AuthRepository
import ci.nsu.mobile.main.data.TokenManager
import ci.nsu.mobile.main.network.RetrofitClient

class MainActivity : ComponentActivity() {

    private lateinit var tokenManager: TokenManager
    private lateinit var authRepository: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        tokenManager = TokenManager(this)
        val apiService = RetrofitClient.getApiService() // убедитесь, что этот метод существует
        authRepository = AuthRepository(apiService, tokenManager)

        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AuthApp()
                }
            }
        }
    }

    @Composable
    fun AuthApp() {
        // Состояние: залогинен ли пользователь (проверяем SharedPreferences)
        var isLoggedIn by rememberSaveable { mutableStateOf(tokenManager.isLoggedIn()) }
        var isLoginScreen by rememberSaveable { mutableStateOf(true) }

        if (isLoggedIn) {
            // Показываем главный экран (заглушка)
            // TODO: заменить на реальный MainScreen с BottomNavigation
            androidx.compose.material3.Text("Главный экран (список пользователей и расчёты)")
        } else if (isLoginScreen) {
            val viewModel = LoginViewModel(authRepository)
            LoginScreen(
                viewModel = viewModel,
                onNavigateToRegister = { isLoginScreen = false },
                onLoginSuccess = {
                    // Обновляем состояние при успешном входе
                    isLoggedIn = tokenManager.isLoggedIn()
                }
            )
        } else {
            val viewModel = RegisterViewModel(authRepository)
            RegisterScreen(
                viewModel = viewModel,
                onNavigateToLogin = { isLoginScreen = true },
                onRegisterSuccess = {
                    isLoginScreen = true
                }
            )
        }
    }
}