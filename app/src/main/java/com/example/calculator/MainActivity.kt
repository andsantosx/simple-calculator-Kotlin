package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IPhoneCalculatorTheme {
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
fun IPhoneCalculatorTheme(content: @Composable () -> Unit) {
    MaterialTheme(content = content)
}

@Composable
fun CalculatorScreen() {
    var displayValue by remember { mutableStateOf("0") }
    var history by remember { mutableStateOf("") }
    var operand1 by remember { mutableStateOf<Double?>(null) }
    var operator by remember { mutableStateOf<String?>(null) }
    var isNewNumber by remember { mutableStateOf(true) }

    // Cores do iPhone
    val orange = Color(0xFFFF9F0A)
    val darkGray = Color(0xFF333333)
    val lightGray = Color(0xFFA5A5A5)

    fun calculate() {
        val op1 = operand1
        val op2 = displayValue.replace(",", ".").toDoubleOrNull()
        val currentOp = operator
        if (op1 != null && op2 != null && currentOp != null) {
            val result = when (currentOp) {
                "+" -> op1 + op2
                "-" -> op1 - op2
                "×" -> op1 * op2
                "÷" -> if (op2 != 0.0) op1 / op2 else Double.NaN
                else -> op2
            }
            displayValue = if (result.isNaN()) "Erro"
            else if (result % 1 == 0.0) result.toLong().toString().replace(".", ",")
            else result.toString().replace(".", ",")
            operand1 = null
            operator = null
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(bottom = 16.dp)
    ) {
        // Display com Histórico
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End
        ) {
            // Histórico (Ex: 1+2+4)
            Text(
                text = history,
                fontSize = 24.sp,
                color = Color.Gray,
                textAlign = TextAlign.End,
                maxLines = 1
            )
            // Valor Principal (Número atual)
            Text(
                text = displayValue,
                fontSize = 80.sp,
                color = Color.White,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.End,
                maxLines = 1,
                softWrap = false
            )
        }

        // Teclado
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Linha 1: AC, DEL, %, ÷
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                IPhoneButton("AC", Modifier.weight(1f), lightGray, Color.Black) {
                    displayValue = "0"
                    history = ""
                    operand1 = null
                    operator = null
                    isNewNumber = true
                }
                IPhoneButton("DEL", Modifier.weight(1f), lightGray, Color.Black) {
                    if (displayValue != "0" && !isNewNumber) {
                        if (displayValue.length > 1) {
                            displayValue = displayValue.dropLast(1)
                            if (history.isNotEmpty()) history = history.dropLast(1)
                        } else {
                            displayValue = "0"
                            if (history.isNotEmpty()) history = history.dropLast(1)
                            isNewNumber = true
                        }
                    }
                }
                IPhoneButton("%", Modifier.weight(1f), lightGray, Color.Black) {
                    val current = displayValue.replace(",", ".").toDoubleOrNull() ?: 0.0
                    displayValue = (current / 100).toString().replace(".", ",")
                    history = displayValue
                }
                IPhoneButton("÷", Modifier.weight(1f), orange) {
                    if (operator != null) calculate()
                    operand1 = displayValue.replace(",", ".").toDoubleOrNull()
                    operator = "÷"
                    history += " ÷ "
                    isNewNumber = true
                }
            }

            // Linha 2: 7, 8, 9, ×
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                IPhoneButton("7", Modifier.weight(1f), darkGray) {
                    if (isNewNumber) { displayValue = "7"; isNewNumber = false } else { displayValue += "7" }
                    history += "7"
                }
                IPhoneButton("8", Modifier.weight(1f), darkGray) {
                    if (isNewNumber) { displayValue = "8"; isNewNumber = false } else { displayValue += "8" }
                    history += "8"
                }
                IPhoneButton("9", Modifier.weight(1f), darkGray) {
                    if (isNewNumber) { displayValue = "9"; isNewNumber = false } else { displayValue += "9" }
                    history += "9"
                }
                IPhoneButton("×", Modifier.weight(1f), orange) {
                    if (operator != null) calculate()
                    operand1 = displayValue.replace(",", ".").toDoubleOrNull()
                    operator = "×"
                    history += " × "
                    isNewNumber = true
                }
            }

            // Linha 3: 4, 5, 6, -
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                IPhoneButton("4", Modifier.weight(1f), darkGray) {
                    if (isNewNumber) { displayValue = "4"; isNewNumber = false } else { displayValue += "4" }
                    history += "4"
                }
                IPhoneButton("5", Modifier.weight(1f), darkGray) {
                    if (isNewNumber) { displayValue = "5"; isNewNumber = false } else { displayValue += "5" }
                    history += "5"
                }
                IPhoneButton("6", Modifier.weight(1f), darkGray) {
                    if (isNewNumber) { displayValue = "6"; isNewNumber = false } else { displayValue += "6" }
                    history += "6"
                }
                IPhoneButton("-", Modifier.weight(1f), orange) {
                    if (operator != null) calculate()
                    operand1 = displayValue.replace(",", ".").toDoubleOrNull()
                    operator = "-"
                    history += " - "
                    isNewNumber = true
                }
            }

            // Linha 4: 1, 2, 3, +
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                IPhoneButton("1", Modifier.weight(1f), darkGray) {
                    if (isNewNumber) { displayValue = "1"; isNewNumber = false } else { displayValue += "1" }
                    history += "1"
                }
                IPhoneButton("2", Modifier.weight(1f), darkGray) {
                    if (isNewNumber) { displayValue = "2"; isNewNumber = false } else { displayValue += "2" }
                    history += "2"
                }
                IPhoneButton("3", Modifier.weight(1f), darkGray) {
                    if (isNewNumber) { displayValue = "3"; isNewNumber = false } else { displayValue += "3" }
                    history += "3"
                }
                IPhoneButton("+", Modifier.weight(1f), orange) {
                    if (operator != null) calculate()
                    operand1 = displayValue.replace(",", ".").toDoubleOrNull()
                    operator = "+"
                    history += " + "
                    isNewNumber = true
                }
            }

            // Linha 5: 0 (Duplo), , , =
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Box(
                    modifier = Modifier
                        .weight(2.1f)
                        .height(80.dp)
                        .clip(RoundedCornerShape(40.dp))
                        .background(darkGray)
                        .clickable {
                            if (isNewNumber) { displayValue = "0"; isNewNumber = false } 
                            else if (displayValue != "0") { displayValue += "0" }
                            history += "0"
                        },
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = "0",
                        color = Color.White,
                        fontSize = 32.sp,
                        modifier = Modifier.padding(start = 32.dp)
                    )
                }
                IPhoneButton(",", Modifier.weight(1f), darkGray) {
                    if (!displayValue.contains(",")) { 
                        displayValue += ","
                        history += ","
                    }
                }
                IPhoneButton("=", Modifier.weight(1f), orange) {
                    calculate()
                    history = displayValue // Mostra o resultado no histórico ao finalizar
                    isNewNumber = true
                }
            }
        }
    }
}

@Composable
fun IPhoneButton(
    text: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    contentColor: Color = Color.White,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clip(CircleShape)
            .background(backgroundColor)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = contentColor,
            fontSize = if (text.length > 2) 20.sp else 32.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
