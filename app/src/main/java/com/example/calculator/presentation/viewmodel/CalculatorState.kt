package com.example.calculator.presentation.viewmodel

import com.example.calculator.domain.model.CalculatorOperation

data class CalculatorState(
    val displayValue: String = "0",
    val history: String = "",
    val operand1: Double? = null,
    val operator: CalculatorOperation? = null,
    val isNewNumber: Boolean = true
)
