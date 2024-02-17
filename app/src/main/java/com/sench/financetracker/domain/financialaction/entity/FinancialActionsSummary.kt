package com.sench.financetracker.domain.financialaction.entity

import com.sench.financetracker.data.financialaction.FinancialAction

data class FinancialActionsSummary(
    val sum: Double,
    val financialActions: List<FinancialAction>
)
