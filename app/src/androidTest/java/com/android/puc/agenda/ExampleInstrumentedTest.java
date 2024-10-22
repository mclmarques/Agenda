package com.android.puc.agenda;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import android.content.Context;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.android.puc.agenda.data.Event;
import com.android.puc.agenda.data.EventDB;
import com.android.puc.agenda.data.EventDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.Date;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private EventDB database;
    private EventDao eventDao;

    @Before
    public void setUp() {
        // Create an in-memory version of the database for testing
        Context context = ApplicationProvider.getApplicationContext();
        database = Room.inMemoryDatabaseBuilder(context, EventDB.class)
                .allowMainThreadQueries() // Allow queries on the main thread during tests
                .build();

        eventDao = database.eventDao();
    }

    @After
    public void tearDown() {
        database.close(); // Close the database after each test
    }

    @Test
    public void insertAndGetEvent() {
        // Create an event and insert it
        Event event = new Event("Test Event", new Date(), "This is a test event");
        eventDao.upsert(event);

        // Fetch all events from the database
        List<Event> events = eventDao.getAllEvents();

        // Check if the event was successfully inserted
        assertEquals(1, events.size());
        assertEquals("Test Event", events.get(0).getTitle());
    }

    @Test
    public void deleteEvent() {
        eventDao.deleteAll();
        List<Event> events = eventDao.getAllEvents();
        // Insert an event, delete it, and verify it no longer exists
        Event event = new Event("Delete Test", new Date(), "To be deleted");
        eventDao.upsert(event);
        eventDao.delete(event);
        assertTrue(events.isEmpty());
    }

    @Test
    public void searchEventByTitleOrDate() {
        // Insert two events
        Event event1 = new Event("Search Test", new Date(), "Find me");
        Event event2 = new Event("Another Event", new Date(), "Don't find me");
        eventDao.upsert(event1);
        eventDao.upsert(event2);

        // Search for event by title
        Event foundEvent = eventDao.getEvent("Search Test", null);
        assertNotNull(foundEvent);
        assertEquals("Search Test", foundEvent.getTitle());
    }

    @Test
    public void clearAllEvents() {
        // Insert an event
        Event event = new Event("Clear Test", new Date(), "This will be cleared");
        eventDao.upsert(event);

        // Clear the database
        eventDao.deleteAll();

        // Verify the database is empty
        List<Event> events = eventDao.getAllEvents();
        assertTrue(events.isEmpty());
    }
}