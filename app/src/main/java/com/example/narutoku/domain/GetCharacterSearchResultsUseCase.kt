package com.example.narutoku.domain

import com.example.narutoku.common.Result
import com.example.narutoku.data.repository.searchResults.CharacterSearchResultsRepository
import com.example.narutoku.model.SearchCharacter
import javax.inject.Inject

class GetCharacterSearchResultsUseCase @Inject constructor(
    private val characterSearchResultsRepository: CharacterSearchResultsRepository
) {
    suspend operator fun invoke(searchQuery: String): Result<SearchCharacter> {
        return getCharacterSearchResults(searchQuery = searchQuery)
    }

    private suspend fun getCharacterSearchResults(searchQuery: String): Result<SearchCharacter> {
        return characterSearchResultsRepository.getCharacterSearchResults(searchQuery = searchQuery)
    }
}
