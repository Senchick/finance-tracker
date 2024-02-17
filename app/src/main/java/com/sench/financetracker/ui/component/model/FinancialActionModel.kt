package com.sench.financetracker.ui.component.model

import androidx.compose.runtime.Stable
import com.sench.financetracker.data.financialaction.FinancialAction
import com.sench.financetracker.ui.component.model.FinancialActionTypeModel.Companion.toModel

@Stable
data class FinancialActionModel(
    val title: String,
    val description: String,
    val sum: String,
    val type: FinancialActionTypeModel
) {
    fun isIncome() = type == FinancialActionTypeModel.INCOME

    companion object {
        fun FinancialAction.toModel() = FinancialActionModel(
            title = title,
            description = description,
            sum = amount.toString(),
            type = type.toModel()
        )
    }
}