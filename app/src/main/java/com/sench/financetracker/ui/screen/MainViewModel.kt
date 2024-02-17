package com.sench.financetracker.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sench.financetracker.domain.financialaction.usecase.CreateFinancialActionUseCase
import com.sench.financetracker.domain.financialaction.usecase.DeleteFinancialActionUseCase
import com.sench.financetracker.domain.financialaction.usecase.GetFinancialActionsSummaryUseCase
import com.sench.financetracker.domain.financialaction.usecase.UpdateFinancialActionUseCase
import com.sench.financetracker.domain.financialaction.usecase.UpdateFinancialActionsPositionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val createFinancialActionUseCase: CreateFinancialActionUseCase,
    private val getFinancialActionsSummaryUseCase: GetFinancialActionsSummaryUseCase,
    private val updateFinancialActionUseCase: UpdateFinancialActionUseCase,
    private val deleteFinancialActionUseCase: DeleteFinancialActionUseCase,
    private val updateFinancialActionsPositionsUseCase: UpdateFinancialActionsPositionsUseCase
) : ViewModel() {

    private val intents = MutableSharedFlow<MainIntent>()
    private val _state: MutableStateFlow<MainUiState> = MutableStateFlow(MainUiState.Loading)
    val state: StateFlow<MainUiState> = _state

    init {
        handleIntent()
        handleSummaryFlow()
    }

    private fun handleSummaryFlow() {
        viewModelScope.launch {
            getFinancialActionsSummaryUseCase()
                .catch { _state.emit(MainUiState.Error(it)) }
                .collect { summary ->
                    _state.emit(
                        MainUiState.Success(
                            financialActions = summary.financialActions,
                            sum = summary.sum
                        )
                    )
                }
        }
    }

    private fun handleIntent() {
        viewModelScope.launch {
            intents.collect { intent ->
                when (intent) {
                    is MainIntent.CreateFinancialAction -> createFinancialAction(intent)
                    is MainIntent.UpdateFinancialAction -> updateFinancialAction(intent)
                    is MainIntent.DeleteFinancialAction -> deleteFinancialAction(intent)
                    is MainIntent.UpdateFinancialActionsPositions -> updateFinancialActions(intent)
                }
            }
        }
    }

    private fun updateFinancialActions(intent: MainIntent.UpdateFinancialActionsPositions) {
        viewModelScope.launch {
            updateFinancialActionsPositionsUseCase(intent.financialActionFrom, intent.financialActionTo)
        }
    }

    fun sendIntent(intent: MainIntent) {
        viewModelScope.launch {
            intents.emit(intent)
        }
    }

    private fun createFinancialAction(intent: MainIntent.CreateFinancialAction) {
        viewModelScope.launch {
            createFinancialActionUseCase(intent.financialAction)
        }
    }

    private fun updateFinancialAction(intent: MainIntent.UpdateFinancialAction) {
        viewModelScope.launch {
            updateFinancialActionUseCase(intent.financialAction)
        }
    }

    private fun deleteFinancialAction(intent: MainIntent.DeleteFinancialAction) {
        viewModelScope.launch {
            deleteFinancialActionUseCase(intent.financialAction)
        }
    }
}