package com.example.narutoku.di

import com.example.narutoku.data.mapper.CharacterDetailMapper
import com.example.narutoku.data.mapper.CharacterMapper
import com.example.narutoku.data.mapper.CharacterSearchResultsMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {

    @Provides
    @Singleton
    fun provideCharacterMapper(): CharacterMapper {
        return CharacterMapper()
    }

    @Provides
    @Singleton
    fun provideCharacterDetailMapper(): CharacterDetailMapper {
        return CharacterDetailMapper()
    }

    @Provides
    @Singleton
    fun provideCharacterSearchResultsMapper(): CharacterSearchResultsMapper {
        return CharacterSearchResultsMapper()
    }
}