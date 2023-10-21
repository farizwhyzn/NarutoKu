package com.example.narutoku.data.repository.favoriteCharacter

import com.example.narutoku.common.Result
import com.example.narutoku.data.source.local.CharacterLocalDataSource
import com.example.narutoku.data.source.local.model.FavoriteCharacter
import com.example.narutoku.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

class FavoriteCharacterRepositoryImpl @Inject constructor(
    private val characterLocalDataSource: CharacterLocalDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : FavoriteCharacterRepository {
    override fun getFavoriteCharacters(): Flow<Result<List<FavoriteCharacter>>> {
        return characterLocalDataSource.getFavoriteCharacters()
            .map { Result.Success(it) }
            .catch { e ->
                Timber.e("getFavoriteCharacters error ${e.message}")
                Result.Error<List<FavoriteCharacter>>("Unable to fetch favorite characters")
            }
            .flowOn(ioDispatcher)
    }

    override fun isCharacterFavorite(characterId: String): Flow<Result<Boolean>> {
        return characterLocalDataSource.isCharacterFavorite(characterId)
            .map { Result.Success(it) }
            .catch { e ->
                Timber.e("getFavoriteCharacters error ${e.message}")
                Result.Error<Boolean>("Unable to fetch character favorite status")
            }
            .flowOn(ioDispatcher)
    }

    override suspend fun insertFavoriteCharacter(favoriteCharacter: FavoriteCharacter) {
        try {
            characterLocalDataSource.insert(favoriteCharacter)
        } catch (e: Exception) {
            Timber.e("insertFavoriteCharacter error ${e.message}")
        }
    }

    override suspend fun deleteFavoriteCharacter(favoriteCharacter: FavoriteCharacter) {
        try {
            characterLocalDataSource.delete(favoriteCharacter)
        } catch (e: Exception) {
            Timber.e("deleteFavoriteCharacter error ${e.message}")
        }
    }
}