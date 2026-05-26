package ci.nsu.mobile.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        val apiService = RetrofitClient.getApiService()
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
        var isLoginScreen by rememberSaveable { mutableStateOf(true) }

        if (isLoginScreen) {
            val viewModel = LoginViewModel(authRepository)
            LoginScreen(
                viewModel = viewModel,
                onNavigateToRegister = { isLoginScreen = false },
                onLoginSuccess = {
                    // После успешного входа показываем главный экран
                    isLoginScreen = false
                }
            )
        } else {
            // Проверяем, есть ли токен (пользователь вошел)
            val isLoggedIn = tokenManager.isLoggedIn()

            if (isLoggedIn) {
                // Главный экран
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Вы вошли в систему", fontSize = 20.sp)
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            authRepository.logout()
                            isLoginScreen = true
                        }
                    ) {
                        Text("Выйти")
                    }
                }
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
}