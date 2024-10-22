package com.android.puc.agenda.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

/**
 * Using java.util.Date is one option, likely won't cause problems.
 * However there is another, java.time.LocalTime, newer, working from API 26 or greater
 */
@Entity(tableName = "event_table")
public class Event {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private Date date;
    private String description;

    // Constructor
    public Event(String title, Date date, String description) {
        this.title = title;
        this.date = date;
        this.description = description;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}

