package com.example.narutoku.data.repository.detail

import com.example.narutoku.data.mapper.CharacterDetailMapper
import com.example.narutoku.data.source.remote.CharacterNetworkDataSourceImpl
import com.example.narutoku.di.IoDispatcher
import com.example.narutoku.common.Result
import com.example.narutoku.model.CharacterDetail
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject

class CharacterDetailRepositoryImpl @Inject constructor(
    private val characterNetworkDataSource: CharacterNetworkDataSourceImpl,
    private val characterDetailMapper: CharacterDetailMapper,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : CharacterDetailRepository {
    override fun getCharacterDetail(characterId: String): Flow<Result<CharacterDetail>> = flow {
        val response = characterNetworkDataSource.getCharacterDetail(characterId = characterId)
        val body = response.body()

        if (response.isSuccessful && body != null) {
            val characterDetail = characterDetailMapper.mapApiModelToModel(body)
            emit(Result.Success(characterDetail))
        } else {
            Timber.e("getCharacterDetail unsuccessful retrofit response ${response.message()}")
            emit(Result.Error("Unable to fetch character details"))
        }
    }.catch { e ->
        Timber.e("getCharacterDetail exception ${e.message}")
        emit(Result.Error("Unable to fetch character details"))
    }.flowOn(ioDispatcher)
}
