package com.sench.financetracker.data.financialaction

import com.sench.financetracker.domain.financialaction.repository.FinancialActionsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultFinancialActionsRepository @Inject constructor(
    private val dao: FinancialActionsDao
) : FinancialActionsRepository {
    override fun getAll(): Flow<List<FinancialAction>> = dao.getAll()
    override suspend fun update(financialAction: FinancialAction): Int = dao.update(financialAction)
    override suspend fun delete(financialAction: FinancialAction): Unit = dao.delete(financialAction)
    override suspend fun updateTwoEntities(
        first: FinancialAction,
        second: FinancialAction
    ): Unit = dao.updateTwoEntities(
        first,
        second
    )

    override fun sum(): Flow<Double> = dao.sum()

    override suspend fun create(financialAction: FinancialAction): Long {
        return dao.createAndUpdatePosition(financialAction)
    }
}