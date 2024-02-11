package com.sench.financetracker.ui.screen

import androidx.paging.PagingData
import com.sench.financetracker.data.financialaction.FinancialAction
import kotlinx.coroutines.flow.Flow

sealed class MainUiState {
    data object Loading : MainUiState()
    data class Success(val financialActionsFlow: Flow<PagingData<FinancialAction>>) : MainUiState()
    data class Error(val error: Throwable) : MainUiState()
}