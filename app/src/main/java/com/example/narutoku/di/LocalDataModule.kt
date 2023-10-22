package com.example.narutoku.di

import android.content.Context
import androidx.room.Room
import com.example.narutoku.common.Constants.CHARACTER_DATABASE_NAME
import com.example.narutoku.data.repository.favoriteCharacter.FavoriteCharacterRepository
import com.example.narutoku.data.repository.favoriteCharacter.FavoriteCharacterRepositoryImpl
import com.example.narutoku.data.source.local.CharacterDatabase
import com.example.narutoku.data.source.local.CharacterLocalDataSource
import com.example.narutoku.data.source.local.FavoriteCharacterDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {

    @Provides
    @Singleton
    fun provideFavoriteCharacterRepository(
        characterLocalDataSource: CharacterLocalDataSource,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): FavoriteCharacterRepository {
        return FavoriteCharacterRepositoryImpl(
            characterLocalDataSource = characterLocalDataSource,
            ioDispatcher = ioDispatcher
        )
    }

    @Provides
    @Singleton
    fun provideCharacterLocalDataSource(favoriteCharacterDao: FavoriteCharacterDao): CharacterLocalDataSource {
        return CharacterLocalDataSource(favoriteCharacterDao = favoriteCharacterDao)
    }

    @Provides
    @Singleton
    fun provideFavoriteCharacterDao(database: CharacterDatabase): FavoriteCharacterDao {
        return database.favoriteCharacterDao()
    }

    @Provides
    @Singleton
    fun provideCharacterDatabase(@ApplicationContext context: Context): CharacterDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            CharacterDatabase::class.java,
            CHARACTER_DATABASE_NAME
        ).build()
    }
}