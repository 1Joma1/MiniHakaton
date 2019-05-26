package com.geektech.notes.room;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

//our dao class to access the database
@Dao
public interface NoteDao {
    @Query("SELECT * FROM Note")
    List<Note> getAll();

    @Insert
    void insert(Note employee);

    @Update
    void update(Note employee);

    @Delete
    void delete(Note employee);
}