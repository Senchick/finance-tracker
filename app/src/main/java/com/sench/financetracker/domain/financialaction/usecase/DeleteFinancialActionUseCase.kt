package com.sench.financetracker.domain.financialaction.usecase

import com.sench.financetracker.data.financialaction.FinancialAction
import com.sench.financetracker.domain.financialaction.repository.FinancialActionsRepository
import javax.inject.Inject

class DeleteFinancialActionUseCase @Inject constructor(
    private val repository: FinancialActionsRepository
) {
    suspend operator fun invoke(financialAction: FinancialAction): Unit = repository.delete(financialAction)
}