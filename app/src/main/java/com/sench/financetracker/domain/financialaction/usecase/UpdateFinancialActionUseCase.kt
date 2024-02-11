package com.sench.financetracker.domain.financialaction.usecase

import com.sench.financetracker.data.financialaction.FinancialAction
import com.sench.financetracker.domain.financialaction.repository.FinancialActionsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateFinancialActionUseCase @Inject constructor(
    private val repository: FinancialActionsRepository
) {
    suspend operator fun invoke(financialAction: FinancialAction): Int = repository.update(financialAction)
}