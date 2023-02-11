package com.yoriworks.comiclibrary.model.db

import androidx.room.*
import com.yoriworks.comiclibrary.utils.Constants
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM ${Constants.NOTE_TABLE} ORDER BY id")
    fun getAllNotes():Flow<List<DbNote>>

    @Query("SELECT * FROM ${Constants.NOTE_TABLE} WHERE characterId= :characterId ORDER BY id ASC")
    fun getNotes(characterId:Int):Flow<List<DbNote>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addNotes(note: DbNote)

    @Update
    fun updateNote(note: DbNote)

    @Delete
    fun deleteNote(note: DbNote)

    @Query("DELETE FROM ${Constants.NOTE_TABLE} WHERE characterId=:characterId")
    fun deleteAllNotes(characterId: Int)



}