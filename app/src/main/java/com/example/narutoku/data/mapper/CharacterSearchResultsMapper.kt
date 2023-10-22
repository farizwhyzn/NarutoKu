package com.example.narutoku.data.mapper

import com.example.narutoku.common.Mapper
import com.example.narutoku.data.source.remote.model.CharacterSearchResultsApiModel
import com.example.narutoku.model.SearchCharacter
import javax.inject.Inject

class CharacterSearchResultsMapper @Inject constructor() :
    Mapper<CharacterSearchResultsApiModel, SearchCharacter> {

    override fun mapApiModelToModel(from: CharacterSearchResultsApiModel): SearchCharacter {
        val validSearchResultsCharacters = from

        return SearchCharacter(
            id = validSearchResultsCharacters.id!!,
            name = validSearchResultsCharacters.name.orEmpty(),
            images = validSearchResultsCharacters.images.orEmpty(),
        )
    }
}