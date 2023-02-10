package com.yoriworks.comiclibrary.model.db

import kotlinx.coroutines.flow.Flow

interface CollectionDbRepo {
    suspend fun getCharactersFromRepo(): Flow<List<DbCharacter>>
    suspend fun getCharacter(characterId:Int):Flow<DbCharacter>
    suspend fun addCharacterToRepo(character: DbCharacter)
    suspend fun deleteCharacterFromRepo(character: DbCharacter)
}