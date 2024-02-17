package com.sench.financetracker.ui.component.model

import com.sench.financetracker.data.financialaction.FinancialActionType

enum class FinancialActionTypeModel {
    INCOME,
    EXPENSE;

    companion object {
        fun FinancialActionType.toModel(): FinancialActionTypeModel {
            return when (this) {
                FinancialActionType.INCOME -> INCOME
                FinancialActionType.EXPENSE -> EXPENSE
            }
        }
    }
}