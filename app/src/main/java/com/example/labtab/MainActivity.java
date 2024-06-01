package com.example.labtab;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.labtab.ui.Calendar.CalendarFragment;
import com.example.labtab.ui.Diary.DiaryFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.view.MenuItem;

/**
 * The main activity of the application, which hosts fragments for diary and calendar functionalities.
 * It uses a {@link BottomNavigationView} to switch between the {@link DiaryFragment} and {@link CalendarFragment}.
 */
public class MainActivity extends AppCompatActivity {

    private static final int MENU_ITEM_DIARY = R.id.navigation_diary;
    private static final int MENU_ITEM_CALENDAR = R.id.navigation_calendar;

    /**
     * Called when the activity is starting. This is where most initialization should go.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                int itemId = item.getItemId();
                if (itemId == MENU_ITEM_DIARY) {
                    selectedFragment = new DiaryFragment();
                } else if (itemId == MENU_ITEM_CALENDAR) {
                    selectedFragment = new CalendarFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, selectedFragment).commit();
                return true;
            }
        });


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new DiaryFragment()).commit();
        }
    }
}