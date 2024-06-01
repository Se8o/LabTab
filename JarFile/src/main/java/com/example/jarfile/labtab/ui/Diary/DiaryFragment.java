package com.example.jarfile.labtab.ui.Diary;

import android.content.Intent;
import android.graphics.Bitmap;
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

/**
 * A {@link Fragment} subclass that represents a diary entry view.
 * Allows users to add text and image entries to their diary.
 */
public class DiaryFragment extends Fragment {
    private static final int PICK_IMAGE_REQUEST = 1;
    private LinearLayout entriesContainer;
    private Bitmap selectedImage;

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

    /**
     * Opens the image chooser to select an image from the device's gallery.
     */
    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    /**
     * Called when an activity you launched exits, giving you the requestCode you started it with,
     * the resultCode it returned, and any additional data from it.
     *
     * @param requestCode The integer request code originally supplied to startActivityForResult(),
     *                    allowing you to identify who this result came from.
     * @param resultCode The integer result code returned by the child activity through its setResult().
     * @param data An Intent, which can return result data to the caller (various data can be attached to Intent "extras").
     */
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

    /**
     * Adds a new entry to the diary with the provided text and image.
     *
     * @param text The text content of the diary entry.
     * @param image The image content of the diary entry (optional).
     */
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

        entriesContainer.addView(entryView, 0);
    }
}