package com.geektech.notes.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

//specify the database (table, version, exportSchema)
@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract NoteDao noteDao();
}
