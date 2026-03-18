package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black
                ) {
                    CalculatorScreen()
                }
            }
        }
    }
}

@Composable
fun CalculatorTheme(content: @Composable () -> Unit) {
    val colorScheme = darkColorScheme(
        primary = Color(0xFF4B61A8),
        onPrimary = Color.White,
        background = Color.Black,
        surface = Color(0xFF333333),
        onSurface = Color.White
    )
    MaterialTheme(colorScheme = colorScheme, content = content)
}

@Composable
fun CalculatorScreen() {
    var displayValue by remember { mutableStateOf("0") }
    var operand1 by remember { mutableStateOf<Double?>(null) }
    var operator by remember { mutableStateOf<String?>(null) }
    var isNewNumber by remember { mutableStateOf(true) }

    fun calculate() {
        val op1 = operand1
        val op2 = displayValue.toDoubleOrNull()
        val currentOp = operator
        if (op1 != null && op2 != null && currentOp != null) {
            val result = when (currentOp) {
                "+" -> op1 + op2
                "-" -> op1 - op2
                "*" -> op1 * op2
                "/" -> if (op2 != 0.0) op1 / op2 else Double.NaN
                else -> op2
            }
            displayValue = if (result.isNaN()) "Erro" 
                          else if (result % 1 == 0.0) result.toInt().toString() 
                          else result.toString()
            operand1 = null
            operator = null
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Display area
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 48.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            Text(
                text = displayValue,
                fontSize = 80.sp,
                color = Color.White,
                textAlign = TextAlign.End,
                maxLines = 1
            )
        }

        // Buttons area
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF333333)) // Fundo cinza como na imagem
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            val buttons = listOf(
                listOf("7", "8", "9", "/"),
                listOf("4", "5", "6", "*"),
                listOf("1", "2", "3", "-"),
                listOf("0", "C", "=", "+")
            )

            buttons.forEach { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    row.forEach { label ->
                        CalculatorButton(
                            label = label,
                            modifier = Modifier.weight(1f),
                            onClick = {
                                when {
                                    label in "0".."9" -> {
                                        if (isNewNumber) {
                                            displayValue = label
                                            isNewNumber = false
                                        } else {
                                            displayValue = if (displayValue == "0") label else displayValue + label
                                        }
                                    }
                                    label == "C" -> {
                                        displayValue = "0"
                                        operand1 = null
                                        operator = null
                                        isNewNumber = true
                                    }
                                    label == "=" -> {
                                        calculate()
                                        isNewNumber = true
                                    }
                                    else -> { // Operadores +, -, *, /
                                        if (operator != null) calculate()
                                        operand1 = displayValue.toDoubleOrNull()
                                        operator = label
                                        isNewNumber = true
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CalculatorButton(
    label: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .aspectRatio(0.7f) // Proporção para o formato de pílula vertical
            .fillMaxWidth(),
        shape = RoundedCornerShape(50.dp), // Totalmente arredondado
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF4B61A8),
            contentColor = Color.White
        ),
        contentPadding = PaddingValues(0.dp)
    ) {
        Text(
            text = label,
            fontSize = 32.sp,
            fontWeight = FontWeight.Normal
        )
    }
}
