package com.sench.financetracker.di

import com.sench.financetracker.data.financialaction.DefaultFinancialActionsRepository
import com.sench.financetracker.domain.financialaction.repository.FinancialActionsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindFinancialActionsRepository(
        defaultFinancialActionsRepository: DefaultFinancialActionsRepository
    ): FinancialActionsRepository
}