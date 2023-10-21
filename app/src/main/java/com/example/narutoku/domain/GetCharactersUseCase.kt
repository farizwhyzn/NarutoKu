package com.example.narutoku.domain

import com.example.narutoku.common.Result
import com.example.narutoku.data.repository.character.CharacterRepository
import com.example.narutoku.model.Character
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
){
    operator fun invoke(): Flow<Result<List<Character>>> {
        return getCharacters()
    }

    private fun getCharacters(): Flow<Result<List<Character>>> {
        return characterRepository.getCharacters()
    }
}