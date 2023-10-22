package com.example.narutoku.domain

import com.example.narutoku.data.repository.favoriteCharacter.FavoriteCharacterRepository
import com.example.narutoku.data.source.local.model.FavoriteCharacter
import javax.inject.Inject

class InsertFavoriteCharacterUseCase @Inject constructor(
    private val favoriteCharacterRepository: FavoriteCharacterRepository
) {
    suspend operator fun invoke(favoriteCharacter: FavoriteCharacter) {
        return insertFavoriteCharacter(favoriteCharacter = favoriteCharacter)
    }

    private suspend fun insertFavoriteCharacter(favoriteCharacter: FavoriteCharacter) {
        return favoriteCharacterRepository.insertFavoriteCharacter(favoriteCharacter = favoriteCharacter)
    }
}
