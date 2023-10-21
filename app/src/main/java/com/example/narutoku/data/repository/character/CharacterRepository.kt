package com.example.narutoku.data.repository.character

import com.example.narutoku.common.Result
import com.example.narutoku.model.Character
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    fun getCharacters(): Flow<Result<List<Character>>>
}