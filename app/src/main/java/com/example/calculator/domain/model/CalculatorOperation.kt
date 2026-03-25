package com.example.calculator.domain.model

sealed interface CalculatorOperation {
    val symbol: String

    object Add : CalculatorOperation {
        override val symbol: String = "+"
    }

    object Subtract : CalculatorOperation {
        override val symbol: String = "-"
    }

    object Multiply : CalculatorOperation {
        override val symbol: String = "×"
    }

    object Divide : CalculatorOperation {
        override val symbol: String = "÷"
    }
}
