package com.example.calculator.domain.usecase

import com.example.calculator.domain.model.CalculatorOperation

class CalculateUseCase {
    operator fun invoke(num1: Double, num2: Double, operation: CalculatorOperation): Double {
        return when (operation) {
            is CalculatorOperation.Add -> num1 + num2
            is CalculatorOperation.Subtract -> num1 - num2
            is CalculatorOperation.Multiply -> num1 * num2
            is CalculatorOperation.Divide -> if (num2 != 0.0) num1 / num2 else Double.NaN
        }
    }
}
