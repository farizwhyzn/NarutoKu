package com.example.narutoku.model

data class Personal(
    val birthDate: String? = null,
    val sex: String? = null,
    val status: String? = null,
    val occupation: String? = null,
    val affiliation: List<String?>? = null,
    val clan: String? = null,
)
