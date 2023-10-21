package com.example.narutoku.data.repository.favoriteCharacter

import com.example.narutoku.common.Result
import com.example.narutoku.data.source.local.model.FavoriteCharacter
import kotlinx.coroutines.flow.Flow

interface FavoriteCharacterRepository {
    fun getFavoriteCharacters(): Flow<Result<List<FavoriteCharacter>>>
    fun isCharacterFavorite(characterId : String): Flow<Result<Boolean>>
    suspend fun insertFavoriteCharacter(favoriteCharacter: FavoriteCharacter)
    suspend fun deleteFavoriteCharacter(favoriteCharacter: FavoriteCharacter)
}