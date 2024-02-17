package com.sench.financetracker.data.financialaction

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface FinancialActionsDao {
    @Query("select * from financial_actions order by position desc")
    fun getAll(): Flow<List<FinancialAction>>

    @Upsert
    suspend fun upsert(financialAction: FinancialAction): Long

    @Insert
    suspend fun insert(financialAction: FinancialAction): Long

    @Update
    suspend fun update(financialAction: FinancialAction): Int

    @Query("select sum(case when type = 0 then amount else -amount end) from financial_actions")
    fun sum(): Flow<Double>

    @Delete
    suspend fun delete(financialAction: FinancialAction)

    @Transaction
    suspend fun createAndUpdatePosition(financialAction: FinancialAction): Long {
        val id = insert(financialAction)
        update(financialAction.copy(position = id, nullableId = id))

        return id
    }

    @Transaction
    suspend fun updateTwoEntities(first: FinancialAction, second: FinancialAction) {
        update(first)
        update(second)
    }
}