package com.example.narutoku.data.source.remote

import com.example.narutoku.data.source.remote.model.CharacterDetailApiModel
import com.example.narutoku.data.source.remote.model.CharacterSearchResultsApiModel
import com.example.narutoku.data.source.remote.model.CharactersApiModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterApi {
    @GET("character")
    suspend fun getCharacters(
        @Query("limit") limit: String = "2000"
    ): Response<CharactersApiModel>

    @GET("character/{characterId}")
    suspend fun getCharacterDetail(
        @Path("characterId") characterId: String
    ): Response<CharacterDetailApiModel>

    @GET("search")
    suspend fun getCharacterSearchResults(
        @Query("name") searchQuery: String = ""
    ): Response<CharacterSearchResultsApiModel>
}