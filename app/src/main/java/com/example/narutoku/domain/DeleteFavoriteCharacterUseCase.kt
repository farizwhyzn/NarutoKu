package com.example.narutoku.domain

import com.example.narutoku.data.repository.favoriteCharacter.FavoriteCharacterRepository
import com.example.narutoku.data.source.local.model.FavoriteCharacter
import javax.inject.Inject

class DeleteFavoriteCharacterUseCase @Inject constructor(
    private val favoriteCharacterRepository: FavoriteCharacterRepository
) {
    suspend operator fun invoke(favoriteCharacter: FavoriteCharacter) {
        return deletefavoriteCharacter(favoriteCharacter = favoriteCharacter)
    }

    private suspend fun deletefavoriteCharacter(favoriteCharacter: FavoriteCharacter) {
        return favoriteCharacterRepository.deleteFavoriteCharacter(favoriteCharacter = favoriteCharacter)
    }
}