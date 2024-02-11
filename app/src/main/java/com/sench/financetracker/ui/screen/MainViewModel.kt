package com.sench.financetracker.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sench.financetracker.domain.financialaction.usecase.CreateFinancialActionUseCase
import com.sench.financetracker.domain.financialaction.usecase.GetFinancialActionsUseCase
import com.sench.financetracker.domain.financialaction.usecase.SumFinancialActionsUseCase
import com.sench.financetracker.domain.financialaction.usecase.UpdateFinancialActionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val createFinancialActionUseCase: CreateFinancialActionUseCase,
    private val getFinancialActionsUseCase: GetFinancialActionsUseCase,
    private val updateFinancialActionUseCase: UpdateFinancialActionUseCase,
    private val sumFinancialActionsUseCase: SumFinancialActionsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<MainUiState>(MainUiState.Loading)
    val uiState: StateFlow<MainUiState> = _uiState

    init {
        getAllFinancialActions()
    }

    private fun getAllFinancialActions() {
        viewModelScope.launch {
            val flow = getFinancialActionsUseCase()
                .catch { error ->
                    _uiState.update {
                        MainUiState.Error(error)
                    }
                }

            _uiState.update {
                MainUiState.Success(financialActionsFlow = flow)
            }
        }
    }
}