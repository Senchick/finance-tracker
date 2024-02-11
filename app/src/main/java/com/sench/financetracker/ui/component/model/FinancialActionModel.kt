package com.sench.financetracker.ui.component.model

import androidx.compose.runtime.Stable

@Stable
data class FinancialActionModel (
    val title: String,
    val description: String,
    val sum: String,
    val type: FinancialActionTypeModel
) {
    fun isIncome() = type == FinancialActionTypeModel.INCOME
    fun isExpense() = type == FinancialActionTypeModel.EXPENSE
}