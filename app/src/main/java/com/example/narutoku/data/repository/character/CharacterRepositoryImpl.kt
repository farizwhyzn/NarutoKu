package com.example.narutoku.data.repository.character

import com.example.narutoku.common.Result
import com.example.narutoku.data.mapper.CharacterMapper
import com.example.narutoku.data.source.remote.CharacterNetworkDataSource
import com.example.narutoku.di.IoDispatcher
import com.example.narutoku.model.Character
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val characterNetworkDataSource: CharacterNetworkDataSource,
    private val characterMapper: CharacterMapper,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : CharacterRepository {
    override fun getCharacters(): Flow<Result<List<Character>>> = flow {
        val response = characterNetworkDataSource.getCharacters()
        val body = response.body()

        if (response.isSuccessful && body?.characters != null) {
            val characters = characterMapper.mapApiModelToModel(body)
            emit(Result.Success(characters))
        } else {
            Timber.e("getCharacters unsuccessful retrofit response ${response.message()}")
            emit(Result.Error("Unable to fetch characters list"))
        }
    }.catch { e ->
        Timber.e("getCharacters error ${e.message}")
        emit(Result.Error("Unable to fetch characters list"))
    }.flowOn(ioDispatcher)
}