package com.example.labtab.ui.Calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.labtab.R;

import java.util.Calendar;

/**
 * A {@link Fragment} subclass that represents a calendar view.
 * It displays a {@link DatePicker} and a {@link TextView} showing the selected date.
 */
public class CalendarFragment extends Fragment {
    private TextView selectedDateTextView;
    private DatePicker datePicker;

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     *                  The fragment should not add the view itself, but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return The View for the fragment's UI, or null.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        datePicker = view.findViewById(R.id.date_picker);
        EditText eventDescription = view.findViewById(R.id.event_description);
        selectedDateTextView = new TextView(getContext()); // Initialize TextView programmatically
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        params.addRule(RelativeLayout.BELOW, R.id.event_description);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        ((RelativeLayout) view).addView(selectedDateTextView, params);


        Calendar c = Calendar.getInstance();
        updateDateTextView(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

        datePicker.init(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH),
                (view1, year, monthOfYear, dayOfMonth) -> updateDateTextView(year, monthOfYear, dayOfMonth));

        return view;
    }

    /**
     * Updates the TextView to display the selected date.
     *
     * @param year The year as an integer.
     * @param month The month as an integer (0-11).
     * @param day The day as an integer.
     */
    private void updateDateTextView(int year, int month, int day) {
        String date = day + "/" + (month + 1) + "/" + year;
        selectedDateTextView.setText(date);
    }
}