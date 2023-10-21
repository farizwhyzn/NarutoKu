package com.example.narutoku.model

data class Character(
    val id: String,
    val name: String,
    val images: List<String?>?,
    val jutsu: List<String?>?,
    val personal: Personal?,
    val debut: Debut?,
)
