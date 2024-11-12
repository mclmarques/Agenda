package com.android.puc.agenda.data;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Delete;
import androidx.room.Upsert;

import java.util.List;

@Dao
public interface EventDao {
    //Upsert will add if not existing or update if already exists
    @Upsert
    void upsert(Event event);

    @Delete
    void delete(Event event);

    @Query("DELETE FROM event_table")
    void deleteAll();

    @Query("SELECT * FROM event_table ORDER BY date DESC")
    List<Event> getAllEvents();

    @Query("SELECT * FROM event_table WHERE date = :date LIMIT 1")
    Event getEventbyDate(String date);
}
