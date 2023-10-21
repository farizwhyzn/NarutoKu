package com.example.narutoku.data.source.remote.model

import com.google.gson.annotations.SerializedName

data class CharactersApiModel(
    @SerializedName("characters")
    val characters: List<CharacterApiModel?>?
)

data class CharacterApiModel(
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("images")
    val images: List<String?>?,
    @SerializedName("personal")
    val personal: Personal?,
    @SerializedName("debut")
    val debut: Debut?,
)

data class Personal(
    @SerializedName("occupation")
    val occupation: String?,
    @SerializedName("affiliation")
    val affiliation: List<String?>?,
    )

data class Debut(
    @SerializedName("anime")
    val anime: String?
)