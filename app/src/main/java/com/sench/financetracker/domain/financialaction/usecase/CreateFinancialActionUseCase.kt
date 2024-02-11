package com.sench.financetracker.domain.financialaction.usecase

import com.sench.financetracker.data.financialaction.FinancialAction
import com.sench.financetracker.domain.financialaction.repository.FinancialActionsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateFinancialActionUseCase @Inject constructor(
    private val repository: FinancialActionsRepository
) {
    suspend operator fun invoke(financialAction: FinancialAction): Long = repository.create(financialAction)
}