package ru.sergeykozhukhov.habits.presentation.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.presentation.view.fragment.AddHabitFragment;
import ru.sergeykozhukhov.habits.presentation.view.fragment.HabitsListFragment;
import ru.sergeykozhukhov.habits.presentation.view.fragment.ProgressFragment;
import ru.sergeykozhukhov.habits.presentation.view.fragment.SettingsFragment;
import ru.sergeykozhukhov.habits.presentation.view.fragment.StatisticsFragment;

// https://www.flaticon.com/packs/landscapes-collection
public class MainActivity extends AppCompatActivity implements HabitsListFragment.ProgressHolder,
        HabitsListFragment.OnAddClickListener, SettingsFragment.OnAccountClickListener {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.host_fragment_frame_layout, HabitsListFragment.newInstance())
                    .commit();
        }
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                //bottomNavigationView.setVisibility(View.VISIBLE);
            }
        });
        getSupportFragmentManager().removeOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                bottomNavigationView.setVisibility(View.VISIBLE);
            }
        });


        initViews();
        initListeners();
    }

    private void initViews(){

        bottomNavigationView = findViewById(R.id.bottom_navigation);
    }

    public void initListeners(){


        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.open_habit_list_item_menu:
                    getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.fragment_fade_enter, R.anim.fragment_fade_exit)
                            .replace(R.id.host_fragment_frame_layout, HabitsListFragment.newInstance())
                            .commit();
                    break;
                case R.id.open_statistics_item_menu:
                    getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.fragment_fade_enter, R.anim.fragment_fade_exit)
                            .replace(R.id.host_fragment_frame_layout, StatisticsFragment.newInstance())
                            .commit();
                    break;
                case R.id.open_settings_item_menu:
                    getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.fragment_fade_enter, R.anim.fragment_fade_exit)
                            .replace(R.id.host_fragment_frame_layout, SettingsFragment.newInstance())
                            .commit();
                    break;
            }
            return true;
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.open_account) {
            Intent intent = new Intent(MainActivity.this, AccountActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showProgress(@NonNull ru.sergeykozhukhov.habits.model.domain.Habit habit) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.host_fragment_frame_layout, ProgressFragment.newInstance(habit))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onClick() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.host_fragment_frame_layout, AddHabitFragment.newInstance())
                .addToBackStack(null)
                .commit();
        //bottomNavigationView.setVisibility(View.GONE);
    }


    @Override
    public void onAccountClick() {
        Intent intent = new Intent(MainActivity.this, AccountActivity.class);
        startActivity(intent);
    }
}