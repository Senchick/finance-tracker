package com.sench.financetracker.ui.screen

import com.sench.financetracker.data.financialaction.FinancialAction

sealed interface MainUiState {
    data object Loading : MainUiState
    data class Success(
        val financialActions: List<FinancialAction>,
        val sum: Double
    ) : MainUiState

    data class Error(val error: Throwable) : MainUiState
}