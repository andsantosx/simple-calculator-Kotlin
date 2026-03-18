package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculator.presentation.CalculatorViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: CalculatorViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IPhoneCalculatorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black
                ) {
                    CalculatorScreen(viewModel)
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
fun CalculatorScreen(viewModel: CalculatorViewModel) {
    // Cores do iPhone
    val orange = Color(0xFFFF9F0A)
    val darkGray = Color(0xFF333333)
    val lightGray = Color(0xFFA5A5A5)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(bottom = 16.dp)
    ) {
        // Display Area
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = viewModel.history,
                fontSize = 24.sp,
                color = Color.Gray,
                textAlign = TextAlign.End,
                maxLines = 1,
                fontWeight = FontWeight.Light
            )
            Text(
                text = viewModel.displayValue,
                fontSize = 80.sp,
                color = Color.White,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.End,
                maxLines = 1,
                softWrap = false
            )
        }

        // Keyboard Area
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Row 1
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                IPhoneButton("AC", Modifier.weight(1f), lightGray, Color.Black) { viewModel.onClearClick() }
                IPhoneButton("DEL", Modifier.weight(1f), lightGray, Color.Black) { viewModel.onDeleteClick() }
                IPhoneButton("%", Modifier.weight(1f), lightGray, Color.Black) { viewModel.onPercentageClick() }
                IPhoneButton("÷", Modifier.weight(1f), orange) { viewModel.onOperatorClick("÷") }
            }

            // Row 2
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                listOf("7", "8", "9").forEach { num ->
                    IPhoneButton(num, Modifier.weight(1f), darkGray) { viewModel.onNumberClick(num) }
                }
                IPhoneButton("×", Modifier.weight(1f), orange) { viewModel.onOperatorClick("×") }
            }

            // Row 3
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                listOf("4", "5", "6").forEach { num ->
                    IPhoneButton(num, Modifier.weight(1f), darkGray) { viewModel.onNumberClick(num) }
                }
                IPhoneButton("-", Modifier.weight(1f), orange) { viewModel.onOperatorClick("-") }
            }

            // Row 4
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                listOf("1", "2", "3").forEach { num ->
                    IPhoneButton(num, Modifier.weight(1f), darkGray) { viewModel.onNumberClick(num) }
                }
                IPhoneButton("+", Modifier.weight(1f), orange) { viewModel.onOperatorClick("+") }
            }

            // Row 5
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Box(
                    modifier = Modifier
                        .weight(2.1f)
                        .height(80.dp)
                        .clip(RoundedCornerShape(40.dp))
                        .background(darkGray)
                        .clickable { viewModel.onNumberClick("0") },
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = "0",
                        color = Color.White,
                        fontSize = 32.sp,
                        modifier = Modifier.padding(start = 32.dp)
                    )
                }
                IPhoneButton(",", Modifier.weight(1f), darkGray) { viewModel.onCommaClick() }
                IPhoneButton("=", Modifier.weight(1f), orange) { viewModel.calculate() }
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
