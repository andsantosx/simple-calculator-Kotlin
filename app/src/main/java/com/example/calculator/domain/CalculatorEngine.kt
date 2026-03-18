package com.example.calculator.domain

sealed class CalculatorOperation(val symbol: String) {
    object Add : CalculatorOperation("+")
    object Subtract : CalculatorOperation("-")
    object Multiply : CalculatorOperation("×")
    object Divide : CalculatorOperation("÷")
}

class CalculatorEngine {
    fun calculate(num1: Double, num2: Double, operation: CalculatorOperation): Double {
        return when (operation) {
            is CalculatorOperation.Add -> num1 + num2
            is CalculatorOperation.Subtract -> num1 - num2
            is CalculatorOperation.Multiply -> num1 * num2
            is CalculatorOperation.Divide -> if (num2 != 0.0) num1 / num2 else Double.NaN
        }
    }

    fun formatResult(result: Double): String {
        return if (result.isNaN()) "Erro"
        else if (result % 1 == 0.0) result.toLong().toString().replace(".", ",")
        else result.toString().replace(".", ",")
    }
}
