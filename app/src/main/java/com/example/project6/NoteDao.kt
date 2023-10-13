package com.example.project6

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteDao {
    @Insert
    suspend fun insert(note: Note)
    @Update
    suspend fun update(note: Note)
    @Delete
    suspend fun delete(note: Note)
    @Query("SELECT * FROM task_table WHERE noteId = :key")
    fun get(key: Long): LiveData<Note>
    @Query("SELECT * FROM task_table ORDER BY noteId DESC")
    fun getAll(): LiveData<List<Note>>
}
