package ci.nsu.moble.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout. *

import androidx.compose.material3. *
import androidx.compose.runtime. *
import androidx.compose.ui.Modifier
import ci.nsu.moble.main.ui.theme.PracticeTheme

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp




class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PracticeTheme {
                ColorSearchScreen()
            }
        }
    }
}


@Composable
fun ColorSearchScreen() {
    val colorMap = mapOf(
        "red" to Color.Red,
        "orange" to Color(0xFFFF9800),
        "yellow" to Color.Yellow,
        "green" to Color.Green,
        "cyan" to Color.Cyan,
        "blue" to Color.Blue,
        "magenta" to Color.Magenta,
    )


    val defaultButtonColor = Color.LightGray
    var buttonBackgroundColor  by remember { mutableStateOf(defaultButtonColor) }

    var inputText by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                TextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))


                Button(
                    onClick = {
                        val searchColor = inputText.trim().lowercase()
                        val foundColor = colorMap[searchColor]

                        if (foundColor != null)
                        {
                            buttonBackgroundColor = foundColor
                        }
                        else
                        {
                            buttonBackgroundColor = defaultButtonColor

                            val availableColors = colorMap.keys.joinToString(separator = ", ") { "'$it'" }
                            Log.d(
                                "ColorSearch",
                                "The color '$inputText' was not found. Available colors: $availableColors.")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(buttonBackgroundColor),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = buttonBackgroundColor
                    )
                )
                {
                    Text(
                        text = "Apply color",
                        fontSize = 18.sp,
                    //    color = if (buttonBackgroundColor == defaultButtonColor) Color.White else Color.Black
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                ColorPalette(colorMap = colorMap)

            }
        }
    )
}

@Composable
fun ColorPalette(colorMap: Map<String, Color>) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {

        colorMap.forEach { (colorName, colorValue) ->
            ColorItem(
                colorName = colorName,
                colorValue = colorValue
            )
        }
    }
}

@Composable
fun ColorItem(
    colorName: String,
    colorValue: Color
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(
                color = colorValue,
                shape = RoundedCornerShape(12.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = colorName,
            fontSize = 24.sp,
            color = Color.Black,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
