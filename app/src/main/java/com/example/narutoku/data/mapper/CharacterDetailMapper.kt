package com.example.narutoku.data.mapper

import com.example.narutoku.common.Mapper
import com.example.narutoku.data.source.remote.model.CharacterDetailApiModel
import com.example.narutoku.model.CharacterDetail
import com.example.narutoku.model.Personal
import com.example.narutoku.model.Debut
import javax.inject.Inject

class CharacterDetailMapper @Inject constructor() :
    Mapper<CharacterDetailApiModel, CharacterDetail> {

    override fun mapApiModelToModel(from: CharacterDetailApiModel): CharacterDetail {
        val characterDetail = from

        return CharacterDetail(
            id = characterDetail?.id.orEmpty(),
            name = characterDetail?.name.orEmpty(),
            images = characterDetail?.images.orEmpty(),
            jutsu = characterDetail.jutsu.orEmpty(),
            personal = Personal(
                birthDate = characterDetail?.personal?.birthDate,
                sex = characterDetail?.personal?.sex,
                status = characterDetail?.personal?.status,
                occupation = characterDetail?.personal?.occupation,
                affiliation = characterDetail?.personal?.affiliation,
                clan = characterDetail?.personal?.clan,
                ),
            debut = Debut(anime = characterDetail.debut?.anime),
        )
    }
}
