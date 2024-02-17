package com.sench.financetracker.ui.component.dialog

import com.sench.financetracker.data.financialaction.FinancialAction
import com.sench.financetracker.data.financialaction.FinancialActionType

data class FinancialActionDialogModel(
    val amount: String? = null,
    val type: FinancialActionType? = null,
    val title: String? = null,
    val description: String? = null,
    val position: Long? = null,
    val id: Long? = null
) {
    fun toFinancialAction() = FinancialAction(
        amount = amount?.toDoubleOrNull()!!,
        type = type!!,
        title = title!!,
        description = description ?: "",
        nullableId = id,
        position = position ?: -1
    )

    fun isValid(): Boolean = amount?.toDoubleOrNull() != null && type != null && !title.isNullOrEmpty()

    fun isExpense(): Boolean? = type?.let { it == FinancialActionType.EXPENSE }

    companion object {
        fun FinancialAction?.toFinancialActionDialogModel(): FinancialActionDialogModel {
            return FinancialActionDialogModel(
                amount = this?.amount?.toString(),
                type = this?.type,
                title = this?.title,
                description = this?.description,
                id = this?.nullableId,
                position = this?.position
            )
        }
    }
}