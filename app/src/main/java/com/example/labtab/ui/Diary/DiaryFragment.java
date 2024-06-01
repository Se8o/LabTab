package com.example.labtab.ui.Diary;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.labtab.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DiaryFragment extends Fragment {
    private static final int PICK_IMAGE_REQUEST = 1;
    private LinearLayout entriesContainer;
    private Bitmap selectedImage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diary, container, false);

        EditText diaryEntry = view.findViewById(R.id.diary_entry);
        Button addTextButton = view.findViewById(R.id.add_text_button);
        Button addImageButton = view.findViewById(R.id.add_image_button);
        Button saveEntryButton = view.findViewById(R.id.save_entry_button);
        entriesContainer = view.findViewById(R.id.entries_container);

        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser();
            }
        });

        saveEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEntry(diaryEntry.getText().toString(), selectedImage);
            }
        });

        return view;
    }

    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            try {
                selectedImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void addEntry(String text, Bitmap image) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View entryView = inflater.inflate(R.layout.diary_entry_layout, entriesContainer, false);

        TextView entryText = entryView.findViewById(R.id.entry_text);
        ImageView entryImage = entryView.findViewById(R.id.entry_image);
        TextView entryDate = entryView.findViewById(R.id.entry_date);

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