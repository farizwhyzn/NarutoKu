package com.example.narutoku.data.repository.detail

import com.example.narutoku.common.Result
import com.example.narutoku.model.CharacterDetail
import kotlinx.coroutines.flow.Flow

interface CharacterDetailRepository {
    fun getCharacterDetail(characterId: String): Flow<Result<CharacterDetail>>

}