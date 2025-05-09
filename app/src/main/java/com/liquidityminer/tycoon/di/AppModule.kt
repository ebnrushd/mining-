package com.liquidityminer.tycoon.di

import android.content.Context
import androidx.room.Room
import com.liquidityminer.tycoon.data.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    
    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "liquidity_miner_db"
    ).build()
    
    @Provides
    @Singleton
    fun provideUserDao(db: AppDatabase) = db.userDao()
}