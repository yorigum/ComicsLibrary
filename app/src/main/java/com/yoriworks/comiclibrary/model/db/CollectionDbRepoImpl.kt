package com.yoriworks.comiclibrary.model.db

import kotlinx.coroutines.flow.Flow

class CollectionDbRepoImpl(private val characterDao: CharacterDao) : CollectionDbRepo {
    override suspend fun getCharactersFromRepo(): Flow<List<DbCharacter>> =
        characterDao.getCharacters()

    override suspend fun getCharacter(characterId: Int): Flow<DbCharacter> =
        characterDao.getCharacter(characterId)

    override suspend fun addCharacterToRepo(character: DbCharacter) =
        characterDao.addCharacter(character)

    override suspend fun deleteCharacterFromRepo(character: DbCharacter) =
        characterDao.deleteCharacter(character)

}