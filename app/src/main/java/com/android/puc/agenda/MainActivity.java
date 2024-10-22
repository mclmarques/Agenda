package com.android.puc.agenda;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.puc.agenda.data.Event;
import com.android.puc.agenda.data.EventDB;
import com.android.puc.agenda.data.EventDao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private EventDB eventDB;
    private EventDao eventDao;
    private ExecutorService executorService = Executors.newSingleThreadExecutor(); //Used for background OP

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Initialize database
        eventDB = EventDB.getInstance(this);
        eventDao = eventDB.eventDao();

        //Initialize ui
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    //Methods to perform IO operations to the DB

    public void addNewEvent(String eventName, Date eventDate, String description) {
        executorService.execute(() -> {
            Event event = new Event(eventName, eventDate, description);
            eventDao.upsert(event);
        });
    }

    public void deleteEvent(Event event) {
        executorService.execute(() -> {
            eventDao.delete(event);
        });
    }

    public void deleteAll() {
        executorService.execute(() -> {
            eventDao.deleteAll();
        });
    }

    public void getAllEvents(Callback<List<Event>> callback) {
        executorService.execute(() -> {
            List<Event> events = eventDao.getAllEvents();
            // Pass the result back to the main thread via the callback
            runOnUiThread(() -> callback.onResult(events));
        });
    }

    public void getEvent(String title, Date eventDate, Callback<Event> callback) {
        executorService.execute(() -> {
            // Convert date to string to pass to DAO
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateStr = sdf.format(eventDate);

            // Perform the query
            Event event = eventDao.getEvent(title, dateStr);

            // Pass the result back to the main thread via the callback
            runOnUiThread(() -> callback.onResult(event));
        });
    }

    // Callback interface for async operations
    public interface Callback<T> {
        void onResult(T result);
    }
}