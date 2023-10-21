package com.example.narutoku.data.source.remote

import com.example.narutoku.data.source.remote.model.CharacterDetailApiModel
import com.example.narutoku.data.source.remote.model.CharacterSearchResultsApiModel
import com.example.narutoku.data.source.remote.model.CharactersApiModel
import retrofit2.Response
import javax.inject.Inject

class CharacterNetworkDataSourceImpl @Inject constructor(private val characterApi: CharacterApi) :
CharacterNetworkDataSource {
    override suspend fun getCharacters(): Response<CharactersApiModel> {
        return characterApi.getCharacters()
    }

    override suspend fun getCharacterDetail(characterId: String): Response<CharacterDetailApiModel> {
        return characterApi.getCharacterDetail(characterId = characterId)
    }

    override suspend fun getCharacterSearchResults(searchQuery: String): Response<CharacterSearchResultsApiModel> {
        return characterApi.getCharacterSearchResults()
    }
}