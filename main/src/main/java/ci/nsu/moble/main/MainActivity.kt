package ci.nsu.moble.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ci.nsu.moble.main.ui.theme.PracticeTheme

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp


import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PracticeTheme {
                SimpleNavigation()
                }
            }
        }
    }


//@Composable
//fun MainScreen() {
//    val context = LocalContext.current
//
//    Scaffold(
//        modifier = Modifier.fillMaxSize()
//    ) { innerPadding ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(innerPadding)
//                .padding(16.dp),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
//        ) {
//            Button(
//                onClick = {
//                    val intent = Intent(context, SecondActivity::class.java).apply {
//                        putExtra("key_data", "Hello from MainActivity!")
//                    }
//                    context.startActivity(intent)
//                }
//            ) {
//                Text("go to SecondActivity")
//            }
//        }
//    }
//}

@Composable
fun SimpleNavigation() {
    val navController = rememberNavController()

    // Список экранов из sealed class
    val screens = listOf(
        Screen.Home
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                screens.forEach { screen ->
                    NavigationBarItem(
                        // Без иконок, только текст
                        label = {
                            Text(
                                text = when (screen) {
                                    is Screen.Home -> "Главная"
                                }
                            )
                        },
                        // Для иконки используем пустой компонент, так как параметр обязателен
                        icon = {},
                        selected = currentRoute == screen.route,
                        onClick = {
                            // Навигация с использованием sealed class
                            navController.navigate(screen.route) {
                                // Очищаем стек до начального пункта назначения
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                // Избегаем множественных копий
                                launchSingleTop = true
                                // Восстанавливаем состояние
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen()
            }
        }
    }
}
