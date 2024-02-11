package com.sench.financetracker.data.financialaction

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sench.financetracker.domain.financialaction.repository.FinancialActionsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultFinancialActionsRepository @Inject constructor(
    private val dao: FinancialActionsDao
) : FinancialActionsRepository {
    override fun getAll(): Flow<PagingData<FinancialAction>> {
        val pager = Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                dao.getAll()
            }
        )

        return pager.flow
    }

    override suspend fun update(financialAction: FinancialAction): Int {
        return dao.update(financialAction)
    }

    override suspend fun sum(): Double {
        return dao.sum()
    }

    override suspend fun create(financialAction: FinancialAction): Long {
        return dao.insert(financialAction)
    }
}