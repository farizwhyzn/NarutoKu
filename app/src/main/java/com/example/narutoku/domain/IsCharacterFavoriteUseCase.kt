package com.example.narutoku.domain

import com.example.narutoku.common.Result
import com.example.narutoku.data.repository.favoriteCharacter.FavoriteCharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IsCharacterFavoriteUseCase @Inject constructor(
    private val favoriteCharacterRepository: FavoriteCharacterRepository
) {
    operator fun invoke(characterId: String): Flow<Result<Boolean>> {
        return isCharacterFavorite(characterId = characterId)
    }

    private fun isCharacterFavorite(characterId: String): Flow<Result<Boolean>> {
        return favoriteCharacterRepository.isCharacterFavorite(characterId = characterId)
    }
}
