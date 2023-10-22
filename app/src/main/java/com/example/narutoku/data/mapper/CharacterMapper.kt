package com.example.narutoku.data.mapper

import com.example.narutoku.common.Mapper
import com.example.narutoku.data.source.remote.model.CharactersApiModel
import com.example.narutoku.model.Character
import com.example.narutoku.model.Debut
import com.example.narutoku.model.Personal
import javax.inject.Inject

class CharacterMapper @Inject constructor() : Mapper<CharactersApiModel, List<Character>> {
    override fun mapApiModelToModel(from: CharactersApiModel): List<Character> {
        val validCharacters = from.characters
            .orEmpty()
            .filterNotNull()
            .filter { it.id != null}

        return validCharacters.map { characterApiModel ->
            Character(
                id = characterApiModel.id!!,
                name = characterApiModel.name.orEmpty(),
                images = characterApiModel.images.orEmpty(),
            )
        }
    }
}