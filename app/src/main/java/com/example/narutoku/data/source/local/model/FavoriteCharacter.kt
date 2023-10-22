package com.example.narutoku.data.source.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteCharacter(
    @PrimaryKey
    val id: String
)
