package com.sench.financetracker.data.financialaction

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "financial_actions")
data class FinancialAction(
    val amount: Double,
    val type: FinancialActionType,
    val title: String,
    val description: String,
    val position: Long = -1,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val nullableId: Long? = null
) {

    @get:Ignore
    val id: Long get() = nullableId ?: throw NullPointerException("Id = null, impossible")
}
