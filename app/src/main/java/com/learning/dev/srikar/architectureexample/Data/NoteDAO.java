package com.learning.dev.srikar.architectureexample.Data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDAO {

    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("delete from note_table")
    void deleteAllNotes();

    @Query("select * from note_table order by priority asc")
        LiveData<List<Note>> getAllNotes();

}
