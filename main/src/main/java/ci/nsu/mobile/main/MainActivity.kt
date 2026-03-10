package ci.nsu.mobile.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ci.nsu.mobile.main.ui.theme.PracticeTheme
import ci.nsu.mobile.main.TemperatureViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PracticeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TemperatureConverterScreen()
                }
            }
        }
    }
}

@Composable
fun TemperatureConverterScreen(
    viewModel: TemperatureViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // LBL
        Text(
            text = "Temperature converter",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Celsius textbox
        OutlinedTextField(
            value = uiState.celsius,
            onValueChange = { viewModel.onCelsiusChanged(it) },
            label = { Text("°C") },
            placeholder = { Text("36,6") },
            isError = uiState.celsius.isNotBlank() && !uiState.isCelsiusValid,
            supportingText = {
                if (uiState.celsius.isNotBlank() && !uiState.isCelsiusValid) {
                    Text("Entewr a valid number!")
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Convert symbol
        Text(
            text = "⇅",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Fhrnht textbox
        OutlinedTextField(
            value = uiState.fahrenheit,
            onValueChange = { viewModel.onFahrenheitChanged(it) },
            label = { Text("°F") },
            placeholder = { Text("400") },
            isError = uiState.fahrenheit.isNotBlank() && !uiState.isFahrenheitValid,
            supportingText = {
                if (uiState.fahrenheit.isNotBlank() && !uiState.isFahrenheitValid) {
                    Text("Entewr a valid number!")
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))


        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Input State:",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Celsius: ${if (uiState.isCelsiusValid) "✓" else if (uiState.celsius.isNotBlank()) "✗" else "—"}"
                )
                Text(
                    text = "Fahrenheit: ${if (uiState.isFahrenheitValid) "✓" else if (uiState.fahrenheit.isNotBlank()) "✗" else "—"}"
                )
            }
        }
    }
}