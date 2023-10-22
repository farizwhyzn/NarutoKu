package com.example.narutoku.data.repository.searchResults

import com.example.narutoku.data.mapper.CharacterSearchResultsMapper
import com.example.narutoku.data.source.remote.CharacterNetworkDataSource
import com.example.narutoku.common.Result
import com.example.narutoku.di.IoDispatcher
import com.example.narutoku.model.SearchCharacter
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class CharacterSearchResultsRepositoryImpl @Inject constructor(
    private val characterNetworkDataSource: CharacterNetworkDataSource,
    private val characterSearchResultsMapper: CharacterSearchResultsMapper,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : CharacterSearchResultsRepository {
    override suspend fun getCharacterSearchResults(searchQuery: String): Result<SearchCharacter> {
        return try {
            withContext(ioDispatcher) {
                val response = characterNetworkDataSource.getCharacterSearchResults(searchQuery = searchQuery)
                val body = response.body()

                if (response.isSuccessful && body != null) {
                    val characterSearchResults = characterSearchResultsMapper.mapApiModelToModel(body)
                    Result.Success(characterSearchResults)
                } else {
                    Timber.e(
                        "getCharacterSearchResults unsuccessful retrofit response ${response.message()}"
                    )
                    Result.Error("Unable to fetch character search results")
                }
            }
        } catch (e: Exception) {
            Timber.e("getCharacterSearchResults error ${e.message}")
            Result.Error("Unable to fetch character search results")
        }
    }
}
