package com.yoriworks.comiclibrary.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yoriworks.comiclibrary.comicsToString
import com.yoriworks.comiclibrary.model.CharacterResult
import com.yoriworks.comiclibrary.model.db.Constants.CHARACTER_TABLE

@Entity(tableName = CHARACTER_TABLE)
data class DbCharacter(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val apiId: Int?,
    val name: String?,
    val thumbnail: String?,
    val comics: String?,
    val description: String?
) {
    companion object {
        fun fromCharacter(character: CharacterResult) = DbCharacter(
            id = 0,
            apiId = character.id,
            name = character.name,
            thumbnail = character.thumbnail?.path + "." + character.thumbnail?.extension,
            comics = character.comics?.items?.mapNotNull { it.name }?.comicsToString()
                ?: "no comics",
            description = character.description
        )
    }
}