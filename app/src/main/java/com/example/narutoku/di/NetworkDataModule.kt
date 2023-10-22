package com.example.narutoku.di

import com.example.narutoku.data.mapper.CharacterDetailMapper
import com.example.narutoku.data.mapper.CharacterMapper
import com.example.narutoku.data.mapper.CharacterSearchResultsMapper
import com.example.narutoku.data.repository.character.CharacterRepository
import com.example.narutoku.data.repository.character.CharacterRepositoryImpl
import com.example.narutoku.data.repository.detail.CharacterDetailRepository
import com.example.narutoku.data.repository.detail.CharacterDetailRepositoryImpl
import com.example.narutoku.data.repository.searchResults.CharacterSearchResultsRepository
import com.example.narutoku.data.repository.searchResults.CharacterSearchResultsRepositoryImpl
import com.example.narutoku.data.source.remote.CharacterApi
import com.example.narutoku.data.source.remote.CharacterNetworkDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkDataModule {

    @Provides
    @Singleton
    fun provideCharacterRepository(
        characterNetworkDataSource: CharacterNetworkDataSourceImpl,
        characterMapper: CharacterMapper,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): CharacterRepository {
        return CharacterRepositoryImpl(
            characterNetworkDataSource = characterNetworkDataSource,
            ioDispatcher = ioDispatcher,
            characterMapper = characterMapper
        )
    }

    @Provides
    @Singleton
    fun provideCharacterDetailRepository(
        characterNetworkDataSource: CharacterNetworkDataSourceImpl,
        characterDetailMapper: CharacterDetailMapper,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): CharacterDetailRepository {
        return CharacterDetailRepositoryImpl(
            characterNetworkDataSource = characterNetworkDataSource,
            characterDetailMapper = characterDetailMapper,
            ioDispatcher = ioDispatcher
        )
    }

    @Provides
    @Singleton
    fun provideCharacterSearchResultsRepository(
        characterNetworkDataSource: CharacterNetworkDataSourceImpl,
        characterSearchResultsMapper: CharacterSearchResultsMapper,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): CharacterSearchResultsRepository {
        return CharacterSearchResultsRepositoryImpl(
            characterNetworkDataSource = characterNetworkDataSource,
            characterSearchResultsMapper = characterSearchResultsMapper,
            ioDispatcher = ioDispatcher
        )
    }

    @Provides
    @Singleton
    fun provideCharacterNetworkDataSource(characterApi: CharacterApi): CharacterNetworkDataSourceImpl {
        return CharacterNetworkDataSourceImpl(characterApi = characterApi)
    }
}