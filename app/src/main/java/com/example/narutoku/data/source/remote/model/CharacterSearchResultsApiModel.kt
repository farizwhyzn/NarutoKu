package com.example.narutoku.data.source.remote.model

import com.google.gson.annotations.SerializedName

data class CharacterSearchResultsApiModel(
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("images")
    val images: List<String?>?,
)
