package com.example.narutoku.data.source.remote.model

import com.google.gson.annotations.SerializedName

data class CharacterSearchResultsApiModel(
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("images")
    val images: List<String?>?,
    @SerializedName("personal")
    val personal: PersonalSearchResults?,
    @SerializedName("debut")
    val debut: DebutSearchResults?,
)

data class PersonalSearchResults(
    @SerializedName("occupation")
    val occupation: String?,
    @SerializedName("affiliation")
    val affiliation: List<String?>?,
)

data class DebutSearchResults(
    @SerializedName("anime")
    val anime: String?
)
