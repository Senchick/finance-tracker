package com.sench.financetracker.data.financialaction

import androidx.room.TypeConverter

class FinancialActionTypeConverter {
    @TypeConverter
    fun fromOrdinal(value: Int): FinancialActionType {
        return FinancialActionType.entries.find { it.ordinal == value }
            ?: throw IllegalArgumentException("No FinancialActionType with ordinal: $value")
    }

    @TypeConverter
    fun typeToOrdinal(type: FinancialActionType): Int {
        return type.ordinal
    }
}