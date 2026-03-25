package com.example.calculator.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.calculator.domain.model.CalculatorOperation
import com.example.calculator.domain.usecase.CalculateUseCase
import com.example.calculator.domain.usecase.FormatResultUseCase

class CalculatorViewModel : ViewModel() {
    private val calculateUseCase = CalculateUseCase()
    private val formatResultUseCase = FormatResultUseCase()

    private val _state = mutableStateOf(CalculatorState())
    val state: State<CalculatorState> = _state

    fun onNumberClick(number: String) {
        val currentState = _state.value
        val (newDisplay, newHistory) = if (currentState.isNewNumber) {
            number to (currentState.history + number)
        } else {
            val updatedDisplay = if (currentState.displayValue == "0") number else currentState.displayValue + number
            updatedDisplay to (currentState.history + number)
        }

        _state.value = currentState.copy(
            displayValue = newDisplay,
            history = newHistory,
            isNewNumber = false
        )
    }

    fun onOperatorClick(symbol: String) {
        val currentState = _state.value
        val operation = when (symbol) {
            "+" -> CalculatorOperation.Add
            "-" -> CalculatorOperation.Subtract
            "×" -> CalculatorOperation.Multiply
            "÷" -> CalculatorOperation.Divide
            else -> null
        } ?: return

        if (currentState.operator != null) {
            calculate()
        }

        val updatedState = _state.value
        _state.value = updatedState.copy(
            operand1 = updatedState.displayValue.replace(",", ".").toDoubleOrNull(),
            operator = operation,
            history = updatedState.history + " $symbol ",
            isNewNumber = true
        )
    }

    fun calculate() {
        val currentState = _state.value
        val operand2 = currentState.displayValue.replace(",", ".").toDoubleOrNull()

        if (currentState.operand1 != null && operand2 != null && currentState.operator != null) {
            val result = calculateUseCase(currentState.operand1, operand2, currentState.operator)
            val formattedResult = formatResultUseCase(result)
            
            _state.value = currentState.copy(
                displayValue = formattedResult,
                history = formattedResult,
                operand1 = null,
                operator = null,
                isNewNumber = true
            )
        }
    }

    fun onClearClick() {
        _state.value = CalculatorState()
    }

    fun onDeleteClick() {
        val currentState = _state.value
        if (currentState.history.isEmpty()) return

        val lastChar = currentState.history.last()
        val newHistory = if (lastChar == ' ') {
            currentState.history.dropLast(3) // Remove " op "
        } else {
            currentState.history.dropLast(1)
        }

        val newDisplay = if (currentState.displayValue.length > 1) {
            currentState.displayValue.dropLast(1)
        } else {
            "0"
        }

        _state.value = currentState.copy(
            history = newHistory,
            displayValue = newDisplay,
            isNewNumber = newDisplay == "0",
            operator = if (lastChar == ' ') null else currentState.operator
        )
    }

    fun onPercentageClick() {
        val currentState = _state.value
        val current = currentState.displayValue.replace(",", ".").toDoubleOrNull() ?: 0.0
        val result = current / 100
        val formattedResult = formatResultUseCase(result)
        
        _state.value = currentState.copy(
            displayValue = formattedResult,
            history = formattedResult,
            isNewNumber = true
        )
    }

    fun onCommaClick() {
        val currentState = _state.value
        if (!currentState.displayValue.contains(",")) {
            _state.value = currentState.copy(
                displayValue = currentState.displayValue + ",",
                history = currentState.history + ",",
                isNewNumber = false
            )
        }
    }
}
