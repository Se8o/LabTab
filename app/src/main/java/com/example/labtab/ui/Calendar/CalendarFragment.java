package com.example.labtab.ui.Calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.labtab.R;

import java.util.Calendar;

public class CalendarFragment extends Fragment {
    private TextView selectedDateTextView;
    private DatePicker datePicker;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        selectedDateTextView = view.findViewById(R.id.selected_date);
        datePicker = view.findViewById(R.id.date_picker);
        EditText eventDescription = view.findViewById(R.id.event_description);

        // Set initial date in the TextView
        Calendar c = Calendar.getInstance();
        updateDateTextView(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

        // Set a listener to update the TextView when the date is changed
        datePicker.init(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH),
                (view1, year, monthOfYear, dayOfMonth) -> updateDateTextView(year, monthOfYear, dayOfMonth));

        return view;
    }

    private void updateDateTextView(int year, int month, int day) {
        String date = day + "/" + (month + 1) + "/" + year;
        selectedDateTextView.setText(date);
    }
}