package com.example.narutoku.data.source.remote

import com.example.narutoku.data.source.remote.model.CharacterDetailApiModel
import com.example.narutoku.data.source.remote.model.CharacterSearchResultsApiModel
import com.example.narutoku.data.source.remote.model.CharactersApiModel
import retrofit2.Response

interface CharacterNetworkDataSource {
    suspend fun getCharacters(): Response<CharactersApiModel>

    suspend fun getCharacterDetail(characterIds: String): Response<CharacterDetailApiModel>

    suspend fun getCharacterSearchResults(searchQuery: String): Response<CharacterSearchResultsApiModel>
}