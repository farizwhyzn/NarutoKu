package com.example.narutoku.model

data class CharacterDetail(
    val id: String,
    val name: String,
    val images: List<String?>?,
    val jutsu: List<String?>?,
    val personal: Personal?,
    val debut: Debut?,
)
