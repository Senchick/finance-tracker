package com.sench.financetracker.domain.financialaction.usecase

import com.sench.financetracker.domain.financialaction.repository.FinancialActionsRepository
import javax.inject.Inject

class SumFinancialActionsUseCase @Inject constructor(
    private val repository: FinancialActionsRepository
) {
    suspend operator fun invoke(): Double = repository.sum()
}