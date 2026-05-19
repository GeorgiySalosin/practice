package ci.nsu.mobile.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue

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
    var isLoginScreen by remember { mutableStateOf(true) }

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