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
import ci.nsu.mobile.main.LoginScreen
import ci.nsu.mobile.main.RegisterScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AuthApp()
                }
            }
        }
    }
}

@Composable
fun AuthApp() {
    // rememberSaveable сохраняет состояние при повороте
    var isLoginScreen by rememberSaveable { mutableStateOf(true) }

    if (isLoginScreen) {
        LoginScreen(
            onNavigateToRegister = { isLoginScreen = false }
        )
    } else {
        RegisterScreen(
            onNavigateToLogin = { isLoginScreen = true }
        )
    }
}