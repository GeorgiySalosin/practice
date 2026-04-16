package ci.nsu.mobile.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ci.nsu.mobile.main.ui.theme.PracticeTheme

class Stage01Activity : ComponentActivity() {

    // Регистрируем launcher для получения результата от Stage02Activity
    private val stage02Launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            // Получаем данные обратно из Stage02Activity
            val returnedDeposit = result.data?.getStringExtra("RETURNED_DEPOSIT")
            val returnedTerm = result.data?.getStringExtra("RETURNED_TERM")

            // Обновляем состояние через intent, чтобы compose их подхватил
            intent.putExtra("RETURNED_DEPOSIT", returnedDeposit)
            intent.putExtra("RETURNED_TERM", returnedTerm)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PracticeTheme {
                Stage01Screen(
                    onNavigateToStage02 = { deposit, term ->
                        val intent = Intent(this, Stage02Activity::class.java).apply {
                            putExtra("INITIAL_DEPOSIT", deposit)
                            putExtra("TERM_MONTHS", term)
                        }
                        stage02Launcher.launch(intent)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Stage01Screen(
    onNavigateToStage02: (Double, Int) -> Unit = { _, _ -> }
) {
    val context = LocalContext.current

    // TO GET DATA BACK IF RETURNING FROM STAGE 2
    val activity = context as? ComponentActivity
    val savedDeposit = activity?.intent?.getStringExtra("RETURNED_DEPOSIT")
    val savedTerm = activity?.intent?.getStringExtra("RETURNED_TERM")

    var initialDeposit by remember { mutableStateOf(savedDeposit ?: "") }
    var termMonths by remember { mutableStateOf(savedTerm ?: "") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Расчёт вкладов",
                        fontSize = 20.sp
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = androidx.compose.material3.MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = androidx.compose.material3.MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Deposit
            OutlinedTextField(
                value = initialDeposit,
                onValueChange = { initialDeposit = it },
                label = { Text("Стартовый взнос") },
                placeholder = { Text("Введите сумму") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                isError = initialDeposit.isNotEmpty() && initialDeposit.toDoubleOrNull() == null
            )

            // On error
            if (initialDeposit.isNotEmpty() && initialDeposit.toDoubleOrNull() == null) {
                ErrorMessage()
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Duration
            OutlinedTextField(
                value = termMonths,
                onValueChange = { termMonths = it },
                label = { Text("Срок вклада (месяцы)") },
                placeholder = { Text("Введите количество месяцев") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                isError = termMonths.isNotEmpty() && termMonths.toIntOrNull() == null
            )

            // On error
            if (termMonths.isNotEmpty() && termMonths.toIntOrNull() == null) {
                ErrorMessage()
            }

            Spacer(modifier = Modifier.height(32.dp))


            Button(
                onClick = {
                    if (initialDeposit.isNotEmpty() && termMonths.isNotEmpty() &&
                        initialDeposit.toDoubleOrNull() != null && termMonths.toIntOrNull() != null) {

                        onNavigateToStage02(
                            initialDeposit.toDouble(),
                            termMonths.toInt()
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = initialDeposit.isNotEmpty() && termMonths.isNotEmpty() &&
                        initialDeposit.toDoubleOrNull() != null && termMonths.toIntOrNull() != null
            ) {
                Text(text = "Далее", fontSize = 16.sp)
            }



            Spacer(modifier = Modifier.height(16.dp))


            // Navigation
            Button(
                onClick = {
                    val intent = Intent(context, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    context.startActivity(intent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(text = "Назад", fontSize = 16.sp)
            }


        }
    }
}

@Composable
fun ErrorMessage() {
    Text(
        text = "Введите корректное значение",
        color = androidx.compose.material3.MaterialTheme.colorScheme.error,
        fontSize = 12.sp,
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
fun Stage01ScreenPreview() {
    PracticeTheme {
        Stage01Screen()
    }
}