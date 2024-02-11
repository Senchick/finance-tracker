package com.sench.financetracker.data.financialaction

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "financial_actions")
data class FinancialAction(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val sum: Double,
    val type: FinancialActionType,
    val title: String,
    val description: String,
    val position: Long
)
