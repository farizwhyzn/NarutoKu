package com.example.narutoku.domain

import com.example.narutoku.common.Result
import com.example.narutoku.model.Character
import com.example.narutoku.data.repository.character.CharacterRepository
import com.example.narutoku.data.repository.favoriteCharacter.FavoriteCharacterRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFavoriteCharactersUseCase @Inject constructor(
    private val favoriteCharacterRepository: FavoriteCharacterRepository,
    private val characterRepository: CharacterRepository
) {
    operator fun invoke(): Flow<Result<List<Character>>> {
        return getFavoriteCharacters()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun getFavoriteCharacters(): Flow<Result<List<Character>>> {
        val favoriteCharactersFlow = favoriteCharacterRepository.getFavoriteCharacters()

        return favoriteCharactersFlow.flatMapLatest { favoriteCharactersResult ->
            when (favoriteCharactersResult) {
                is Result.Success -> {
                    val favoriteCharacterIds = favoriteCharactersResult.data.map { it.id }

                    if (favoriteCharacterIds.isNotEmpty()) {
                        characterRepository.getCharacters()
                    } else {
                        flow {
                            emit(Result.Success(emptyList()))
                        }
                    }
                }

                is Result.Error -> {
                    flow {
                        emit(Result.Error(favoriteCharactersResult.message))
                    }
                }
            }
        }
    }
}
