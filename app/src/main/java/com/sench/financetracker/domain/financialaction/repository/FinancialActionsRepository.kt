package com.sench.financetracker.domain.financialaction.repository

import androidx.paging.PagingData
import com.sench.financetracker.data.financialaction.FinancialAction
import kotlinx.coroutines.flow.Flow

interface FinancialActionsRepository {
    fun getAll(): Flow<PagingData<FinancialAction>>
    suspend fun create(financialAction: FinancialAction): Long
    suspend fun update(financialAction: FinancialAction): Int
    suspend fun sum(): Double
}