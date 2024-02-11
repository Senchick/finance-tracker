package com.sench.financetracker.domain.financialaction.usecase

import androidx.paging.PagingData
import com.sench.financetracker.data.financialaction.FinancialAction
import com.sench.financetracker.domain.financialaction.repository.FinancialActionsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFinancialActionsUseCase @Inject constructor(
    private val repository: FinancialActionsRepository
) {
    operator fun invoke(): Flow<PagingData<FinancialAction>> = repository.getAll()
}