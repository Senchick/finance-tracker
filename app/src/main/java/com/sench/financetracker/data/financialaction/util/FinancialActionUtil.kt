package com.sench.financetracker.data.financialaction.util

import com.sench.financetracker.data.financialaction.FinancialAction

fun MutableList<FinancialAction>.swap(
    index1: Int,
    index2: Int,
    result: (from: FinancialAction, to: FinancialAction) -> Unit = { _, _ -> }
): MutableList<FinancialAction> {
    val tmp = this[index1].copy(position = this[index2].position)
    this[index1] = this[index2].copy(position = this[index1].position)
    this[index2] = tmp

    result(this[index1], this[index2])
    return this
}