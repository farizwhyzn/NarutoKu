package com.example.narutoku.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.narutoku.data.source.local.model.FavoriteCharacter
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteCharacterDao {
    @Query("SELECT * FROM FavoriteCharacter")
    fun getFavoriteCharacters(): Flow<List<FavoriteCharacter>>

    @Query("SELECT COUNT(1) FROM FavoriteCharacter WHERE id = :characterId")
    fun isCharacterFavorite(charactedId: String): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favoriteCharacter: FavoriteCharacter)

    @Delete
    suspend fun delete(favoriteCharacter: FavoriteCharacter)
}