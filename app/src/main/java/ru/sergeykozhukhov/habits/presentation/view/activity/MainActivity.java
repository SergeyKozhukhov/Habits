package ru.sergeykozhukhov.habits.presentation.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.model.domain.Habit;
import ru.sergeykozhukhov.habits.presentation.view.fragment.AddHabitFragment;
import ru.sergeykozhukhov.habits.presentation.view.fragment.HabitsListFragment;
import ru.sergeykozhukhov.habits.presentation.view.fragment.ProgressFragment;
import ru.sergeykozhukhov.habits.presentation.view.fragment.RegistrationFragment;
import ru.sergeykozhukhov.habits.presentation.view.fragment.SettingsFragment;
import ru.sergeykozhukhov.habits.presentation.view.fragment.StatisticsFragment;

/**
 * Activity - контейнер для Fragments работы с привычками и настройками
 */
public class MainActivity extends AppCompatActivity implements
        HabitsListFragment.OnViewsClickListener,
        SettingsFragment.OnViewsClickListener {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.host_fragment_frame_layout, HabitsListFragment.newInstance())
                    .commit();
        }

        initListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void initListeners() {
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.open_habit_list_item_menu:
                    replaceFragment(HabitsListFragment.newInstance(), false);
                    break;
                case R.id.open_statistics_item_menu:
                    replaceFragment(StatisticsFragment.newInstance(), false);
                    break;
                case R.id.open_settings_item_menu:
                    replaceFragment(SettingsFragment.newInstance(), false);
                    break;
            }
            return true;
        });
    }

    /**
     * Обработчик нажатия на элементы menu
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.open_account) {
            Intent intent = new Intent(MainActivity.this, AccountActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Обработчик нажатия на кнопку перехода к добавлению новой привычки
     */
    @Override
    public void onAddHabitClick(View view) {
        getSupportFragmentManager()
                .beginTransaction()
                .addSharedElement(view, Objects.requireNonNull(ViewCompat.getTransitionName(view)))
                .replace(R.id.host_fragment_frame_layout, AddHabitFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    /**
     * Обработчик нажатия на элемента списка привычек
     *
     * @param habit привычка
     */
    @Override
    public void onItemHabitListClick(@NonNull Habit habit, @NonNull View view) {
        String string = ViewCompat.getTransitionName(view);
        getSupportFragmentManager()
                .beginTransaction()
                .addSharedElement(view, Objects.requireNonNull(ViewCompat.getTransitionName(view)))
                .replace(R.id.host_fragment_frame_layout, ProgressFragment.newInstance(habit, string))
                .addToBackStack(null)
                .commit();
    }

    /**
     * Обработчик нажатия на кнопку входа в аккаунт
     */
    @Override
    public void onOpenAccountClick() {
        Intent intent = new Intent(MainActivity.this, AccountActivity.class);
        startActivity(intent);
    }

    private void replaceFragment(@NonNull Fragment newFragment, boolean addToBackStack) {
        if (addToBackStack) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.fragment_fade_enter, R.anim.fragment_fade_exit)
                    .replace(R.id.host_fragment_frame_layout, newFragment)
                    .addToBackStack(null)
                    .commit();
            return;
        }
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.fragment_fade_enter, R.anim.fragment_fade_exit)
                .replace(R.id.host_fragment_frame_layout, newFragment)
                .commit();
    }
}