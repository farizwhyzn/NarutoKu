package com.example.narutoku.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.narutoku.data.source.local.model.FavoriteCharacter

@Database(entities = [FavoriteCharacter::class], version = 1, exportSchema = false)
abstract class CharacterDatabase : RoomDatabase() {
    abstract fun favoriteCharacterDao(): FavoriteCharacterDao
}