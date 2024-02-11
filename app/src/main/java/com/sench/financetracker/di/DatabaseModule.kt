package com.sench.financetracker.di

import android.content.Context
import androidx.room.Room
import com.sench.financetracker.data.financialaction.FinancialActionsDao
import com.sench.financetracker.data.financialaction.FinancialActionsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesFinancialActionsDatabase(
        @ApplicationContext
        context: Context
    ): FinancialActionsDatabase {
        return Room.databaseBuilder(
            context,
            FinancialActionsDatabase::class.java,
            "financial_actions"
        ).build()
    }

    @Provides
    @Singleton
    fun providesFinancialActionsDao(database: FinancialActionsDatabase): FinancialActionsDao {
        return database.dao()
    }
}