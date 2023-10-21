package com.example.narutoku.data.source.local

import com.example.narutoku.data.source.local.model.FavoriteCharacter
import kotlinx.coroutines.flow.Flow

class CharacterLocalDataSource(private val favoriteCharacterDao: FavoriteCharacterDao) {
    fun getFavoriteCharacters(): Flow<List<FavoriteCharacter>> {
        return favoriteCharacterDao.getFavoriteCharacters()
    }
    fun isCharacterFavorite(charactedId: String): Flow<Boolean> {
        return favoriteCharacterDao.isCharacterFavorite(charactedId = charactedId)
    }
    suspend fun insert(favoriteCharacter: FavoriteCharacter) {
        favoriteCharacterDao.insert(favoriteCharacter)
    }
    suspend fun delete(favoriteCharacter: FavoriteCharacter) {
        favoriteCharacterDao.delete(favoriteCharacter)
    }

}