package com.example.calculator.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.calculator.domain.CalculatorEngine
import com.example.calculator.domain.CalculatorOperation

class CalculatorViewModel : ViewModel() {
    private val engine = CalculatorEngine()

    var displayValue by mutableStateOf("0")
        private set

    var history by mutableStateOf("")
        private set

    private var operand1: Double? = null
    private var operator: CalculatorOperation? = null
    private var isNewNumber = true

    fun onNumberClick(number: String) {
        if (isNewNumber) {
            displayValue = number
            isNewNumber = false
        } else {
            displayValue = if (displayValue == "0") number else displayValue + number
        }
        history += number
    }

    fun onOperatorClick(opSymbol: String) {
        val operation = when (opSymbol) {
            "+" -> CalculatorOperation.Add
            "-" -> CalculatorOperation.Subtract
            "×" -> CalculatorOperation.Multiply
            "÷" -> CalculatorOperation.Divide
            else -> null
        }

        if (operation != null) {
            if (operator != null) calculate()
            operand1 = displayValue.replace(",", ".").toDoubleOrNull()
            operator = operation
            history += " $opSymbol "
            isNewNumber = true
        }
    }

    fun calculate() {
        val op1 = operand1
        val op2 = displayValue.replace(",", ".").toDoubleOrNull()
        val currentOp = operator

        if (op1 != null && op2 != null && currentOp != null) {
            val result = engine.calculate(op1, op2, currentOp)
            displayValue = engine.formatResult(result)
            history = displayValue
            operand1 = null
            operator = null
            isNewNumber = true
        }
    }

    fun onClearClick() {
        displayValue = "0"
        history = ""
        operand1 = null
        operator = null
        isNewNumber = true
    }

    fun onDeleteClick() {
        if (history.isNotEmpty()) {
            val lastChar = history.last()
            if (lastChar == ' ') {
                history = history.dropLast(3) // Remove " op "
                operator = null
            } else {
                history = history.dropLast(1)
            }
        }
        
        if (displayValue.length > 1) {
            displayValue = displayValue.dropLast(1)
        } else {
            displayValue = "0"
            isNewNumber = true
        }
    }

    fun onPercentageClick() {
        val current = displayValue.replace(",", ".").toDoubleOrNull() ?: 0.0
        displayValue = engine.formatResult(current / 100)
        history = displayValue
    }

    fun onCommaClick() {
        if (!displayValue.contains(",")) {
            displayValue += ","
            history += ","
            isNewNumber = false
        }
    }
}
