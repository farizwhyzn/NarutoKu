package com.example.narutoku.domain

import com.example.narutoku.common.Result
import com.example.narutoku.data.repository.detail.CharacterDetailRepository
import com.example.narutoku.model.CharacterDetail
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharacterDetailUseCase @Inject constructor(
    private val characterDetailRepository: CharacterDetailRepository
) {
    operator fun invoke(characterId: String): Flow<Result<CharacterDetail>> {
        return getCharacterDetail(characterId = characterId)
    }

    private fun getCharacterDetail(characterId: String): Flow<Result<CharacterDetail>> {
        return characterDetailRepository.getCharacterDetail(characterId = characterId)
    }
}
