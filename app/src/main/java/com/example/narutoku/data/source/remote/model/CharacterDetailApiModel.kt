package com.example.narutoku.data.source.remote.model

import com.google.gson.annotations.SerializedName

data class CharacterDetailApiModel(
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("images")
    val images: List<String?>?,
    @SerializedName("jutsu")
    val jutsu: List<String>?,
    @SerializedName("personal")
    val personal: PersonalDetail?,
    @SerializedName("debut")
    val debut: DebutDetail?,
)

data class PersonalDetail(
    @SerializedName("birthdate")
    val birthDate: String?,
    @SerializedName("sex")
    val sex: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("occupation")
    val occupation: String?,
    @SerializedName("affiliation")
    val affiliation: List<String?>?,
    @SerializedName("clan")
    val clan: String?,
)

data class DebutDetail(
    @SerializedName("anime")
    val anime: String?
)
