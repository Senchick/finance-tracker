package com.sench.financetracker.ui.screen

import com.sench.financetracker.data.financialaction.FinancialAction

sealed interface MainIntent {
    data class CreateFinancialAction(val financialAction: FinancialAction) : MainIntent
    data class UpdateFinancialAction(val financialAction: FinancialAction) : MainIntent
    data class DeleteFinancialAction(val financialAction: FinancialAction) : MainIntent
    data class UpdateFinancialActionsPositions(
        val financialActionFrom: FinancialAction,
        val financialActionTo: FinancialAction
    ) : MainIntent
}