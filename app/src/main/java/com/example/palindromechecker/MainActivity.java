package com.example.palindromechecker;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.palindromechecker.ui.dashboard.DashboardFragment;
import com.example.palindromechecker.ui.home.HomeFragment;
import com.example.palindromechecker.ui.notifications.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    HomeFragment checker = new HomeFragment();
    DashboardFragment game = new DashboardFragment();
    NotificationsFragment entries = new NotificationsFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setOnItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, checker)
                .add(R.id.container, game)
                .add(R.id.container, entries)
                .hide(game)
                .hide(entries)
                .commit();
    }

    private final NavigationBarView.OnItemSelectedListener navListener = item -> {
        if (item.getItemId() == R.id.navigation_checker) {
            if (checker.isHidden()) {
                getSupportFragmentManager().beginTransaction()
                        .show(checker)
                        .hide(game)
                        .hide(entries)
                        .commit();
            }

            return true;
        }
        if (item.getItemId() == R.id.navigation_game) {
            if (game.isHidden()) {
                getSupportFragmentManager().beginTransaction()
                        .show(game)
                        .hide(checker)
                        .hide(entries)
                        .commit();
            }
            return true;
        }
        if (item.getItemId() == R.id.navigation_entries) {
            if (entries.isHidden()) {
                getSupportFragmentManager().beginTransaction()
                        .show(entries)
                        .hide(checker)
                        .hide(game)
                        .commit();
            }
            return true;
        }
        return false;
    };

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }


}