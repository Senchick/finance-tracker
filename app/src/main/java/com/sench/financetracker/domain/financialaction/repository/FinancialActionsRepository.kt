package com.sench.financetracker.domain.financialaction.repository

import com.sench.financetracker.data.financialaction.FinancialAction
import kotlinx.coroutines.flow.Flow

interface FinancialActionsRepository {
    fun getAll(): Flow<List<FinancialAction>>
    fun sum(): Flow<Double>
    suspend fun create(financialAction: FinancialAction): Long
    suspend fun update(financialAction: FinancialAction): Int
    suspend fun delete(financialAction: FinancialAction)
    suspend fun updateTwoEntities(first: FinancialAction, second: FinancialAction)
}