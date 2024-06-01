package com.example.labtab.ui.Diary;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.labtab.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DiaryFragment extends Fragment {
    private LinearLayout entriesContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diary, container, false);

        EditText diaryEntry = view.findViewById(R.id.diary_entry);
        Button addTextButton = view.findViewById(R.id.add_text_button);
        Button saveEntryButton = view.findViewById(R.id.save_entry_button);
        entriesContainer = view.findViewById(R.id.entries_container);

        saveEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEntry(diaryEntry.getText().toString(), null); // null for image for simplicity
            }
        });

        return view;
    }

    private void addEntry(String text, Bitmap image) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View entryView = inflater.inflate(R.layout.diary_entry_layout, entriesContainer, false);

        TextView entryText = entryView.findViewById(R.id.entry_text);
        ImageView entryImage = entryView.findViewById(R.id.entry_image);
        TextView entryDate = entryView.findViewById(R.id.entry_date);
        View separator = entryView.findViewById(R.id.entry_separator);

        entryText.setText(text);
        if (image != null) {
            entryImage.setImageBitmap(image);
            entryImage.setVisibility(View.VISIBLE);
        } else {
            entryImage.setVisibility(View.GONE);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        String currentDateAndTime = sdf.format(new Date());
        entryDate.setText(currentDateAndTime);

        entriesContainer.addView(entryView, 0); // add at the top
    }
}
