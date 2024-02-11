package com.sench.financetracker.data.financialaction

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert

@Dao
interface FinancialActionsDao {
    @Query("select * from financial_actions")
    fun getAll(): PagingSource<Int, FinancialAction>

    @Upsert
    suspend fun upsert(financialAction: FinancialAction): Long

    @Insert
    suspend fun insert(financialAction: FinancialAction): Long

    @Update
    suspend fun update(financialAction: FinancialAction): Int

    @Query("select sum(sum) from financial_actions")
    suspend fun sum(): Double
}