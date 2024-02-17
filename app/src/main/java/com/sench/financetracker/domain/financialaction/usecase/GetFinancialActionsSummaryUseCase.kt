package com.sench.financetracker.domain.financialaction.usecase

import com.sench.financetracker.domain.financialaction.entity.FinancialActionsSummary
import com.sench.financetracker.domain.financialaction.repository.FinancialActionsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetFinancialActionsSummaryUseCase @Inject constructor(
    private val repository: FinancialActionsRepository
) {
    operator fun invoke(): Flow<FinancialActionsSummary> = combine(
        repository.getAll(),
        repository.sum()
    ) { financialActions, sum ->
        FinancialActionsSummary(
            sum = sum,
            financialActions = financialActions
        )
    }

}