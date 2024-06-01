package com.example.labtab.ui.Calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.labtab.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A fragment representing a calendar interface where users can add and view events for specific dates.
 */
public class CalendarFragment extends Fragment {
    private TextView selectedDateTextView;
    private DatePicker datePicker;
    private EditText eventDescription;
    private Button addEventButton;
    private ListView eventsListView;
    private List<Event> eventList;
    private ArrayAdapter<String> eventsAdapter;

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container          If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return The View for the fragment's UI, or null.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        datePicker = view.findViewById(R.id.date_picker);
        eventDescription = view.findViewById(R.id.event_description);
        addEventButton = view.findViewById(R.id.add_event_button);
        selectedDateTextView = new TextView(getContext());
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        params.addRule(RelativeLayout.BELOW, R.id.event_description);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        ((RelativeLayout) view).addView(selectedDateTextView, params);

        eventsListView = view.findViewById(R.id.events_list_view);
        eventList = new ArrayList<>();
        eventsAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1);
        eventsListView.setAdapter(eventsAdapter);

        Calendar c = Calendar.getInstance();
        updateDateTextView(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

        datePicker.init(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH),
                (view1, year, monthOfYear, dayOfMonth) -> {
                    updateDateTextView(year, monthOfYear, dayOfMonth);
                    updateEventsListView(year, monthOfYear, dayOfMonth);
                });

        addEventButton.setOnClickListener(v -> {
            int year = datePicker.getYear();
            int month = datePicker.getMonth();
            int day = datePicker.getDayOfMonth();
            String description = eventDescription.getText().toString();

            if (!description.isEmpty()) {
                addEvent(year, month, day, description);
                eventDescription.setText("");
                updateEventsListView(year, month, day);
            }
        });

        return view;
    }

    /**
     * Updates the selected date TextView with the specified date.
     *
     * @param year  The year to display.
     * @param month The month to display.
     * @param day   The day to display.
     */
    private void updateDateTextView(int year, int month, int day) {
        String date = day + "/" + (month + 1) + "/" + year;
        selectedDateTextView.setText(date);
    }

    /**
     * Adds a new event to the event list.
     *
     * @param year        The year of the event.
     * @param month       The month of the event.
     * @param day         The day of the event.
     * @param description The description of the event.
     */
    private void addEvent(int year, int month, int day, String description) {
        Event event = new Event(year, month, day, description);
        eventList.add(event);
    }

    /**
     * Updates the events ListView with events that match the specified date.
     *
     * @param year  The year to filter events.
     * @param month The month to filter events.
     * @param day   The day to filter events.
     */
    private void updateEventsListView(int year, int month, int day) {
        eventsAdapter.clear();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date selectedDate = new Date(year - 1900, month, day);
        for (Event event : eventList) {
            Date eventDate = new Date(event.getYear() - 1900, event.getMonth(), event.getDay());
            if (sdf.format(selectedDate).equals(sdf.format(eventDate))) {
                eventsAdapter.add(event.getDescription());
            }
        }
    }

    /**
     * A simple class representing an event with a date and description.
     */
    private static class Event {
        private final int year;
        private final int month;
        private final int day;
        private final String description;

        /**
         * Constructs a new Event with the specified year, month, day, and description.
         *
         * @param year        The year of the event.
         * @param month       The month of the event.
         * @param day         The day of the event.
         * @param description The description of the event.
         */
        public Event(int year, int month, int day, String description) {
            this.year = year;
            this.month = month;
            this.day = day;
            this.description = description;
        }

        /**
         * Gets the year of the event.
         *
         * @return The year of the event.
         */
        public int getYear() {
            return year;
        }

        /**
         * Gets the month of the event.
         *
         * @return The month of the event.
         */
        public int getMonth() {
            return month;
        }

        /**
         * Gets the day of the event.
         *
         * @return The day of the event.
         */
        public int getDay() {
            return day;
        }

        /**
         * Gets the description of the event.
         *
         * @return The description of the event.
         */
        public String getDescription() {
            return description;
        }
    }
}
