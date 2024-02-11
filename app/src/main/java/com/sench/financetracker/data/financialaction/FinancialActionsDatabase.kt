package com.sench.financetracker.data.financialaction

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [FinancialAction::class],
    version = 0,
    exportSchema = false
)
@TypeConverters(FinancialActionTypeConverter::class)
abstract class FinancialActionsDatabase : RoomDatabase() {
    abstract fun dao(): FinancialActionsDao
}