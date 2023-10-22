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
    val personal: Personal?,
    @SerializedName("debut")
    val debut: Debut?,
)

data class Personal(
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

data class Debut(
    @SerializedName("anime")
    val anime: String?
)
