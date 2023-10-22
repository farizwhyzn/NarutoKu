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
)