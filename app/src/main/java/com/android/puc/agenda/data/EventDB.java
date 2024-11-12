package com.android.puc.agenda.data;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import android.content.Context;

//Version 2 removes the title property from the Event
@Database(entities = {Event.class}, version = 2, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class EventDB extends RoomDatabase {
    private static EventDB instance;
    public abstract EventDao eventDao();

    // Singleton to prevent multiple instances of the database
    public static synchronized EventDB getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            EventDB.class, "event_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
