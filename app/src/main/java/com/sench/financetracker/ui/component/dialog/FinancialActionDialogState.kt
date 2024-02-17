package com.sench.financetracker.ui.component.dialog

import com.sench.financetracker.data.financialaction.FinancialAction

sealed interface FinancialActionDialogState {
    data object Add : FinancialActionDialogState
    data class Edit(val financialAction: FinancialAction) : FinancialActionDialogState
}
