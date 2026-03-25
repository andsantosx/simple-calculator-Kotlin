package com.example.calculator.domain.usecase

import java.util.Locale

class FormatResultUseCase {
    operator fun invoke(result: Double): String {
        return when {
            result.isNaN() -> "Erro"
            result % 1 == 0.0 -> String.format(Locale.getDefault(), "%.0f", result)
            else -> String.format(Locale.getDefault(), "%.2f", result).trimEnd('0').trimEnd('.')
        }
    }
}
