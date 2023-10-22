package com.example.narutoku.data.repository.searchResults

import com.example.narutoku.common.Result
import com.example.narutoku.model.SearchCharacter

interface CharacterSearchResultsRepository {
    suspend fun getCharacterSearchResults(searchQuery: String): Result<SearchCharacter>
}
