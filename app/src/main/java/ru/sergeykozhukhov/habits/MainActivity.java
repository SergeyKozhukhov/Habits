package ru.sergeykozhukhov.habits;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.presentation.element.NetworkChangeReceiver;
import ru.sergeykozhukhov.habits.presentation.view.AddHabitFragment;
import ru.sergeykozhukhov.habits.presentation.view.account.AuthenticationFragment;
import ru.sergeykozhukhov.habits.presentation.view.HabitsListFragment;
import ru.sergeykozhukhov.habits.presentation.view.ProgressFragment;
import ru.sergeykozhukhov.habits.presentation.view.account.RegistrationFragment;
import ru.sergeykozhukhov.habits.presentation.view.SettingsFragment;
import ru.sergeykozhukhov.habits.presentation.view.StatisticsFragment;

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
                    .addToBackStack(null)
                    .commit();
        }

        initViews();
        initListeners();

        NetworkChangeReceiver networkChangeReceiver = new NetworkChangeReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        this.registerReceiver(networkChangeReceiver, filter);
    }

    private void initViews(){

        bottomNavigationView = findViewById(R.id.bottom_navigation);
    }

    public void initListeners(){


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.open_habit_list_item_menu:
                        getSupportFragmentManager().beginTransaction()
                                .setCustomAnimations(R.anim.fragment_fade_enter, R.anim.fragment_fade_exit)
                                .replace(R.id.host_fragment_frame_layout, HabitsListFragment.newInstance())
                                .addToBackStack(null)
                                .commit();
                        break;
                    case R.id.open_statistics_item_menu:
                        getSupportFragmentManager().beginTransaction()
                                .setCustomAnimations(R.anim.fragment_fade_enter, R.anim.fragment_fade_exit)
                                .replace(R.id.host_fragment_frame_layout, StatisticsFragment.newInstance())
                                .addToBackStack(null)
                                .commit();
                        break;
                    case R.id.open_settings_item_menu:
                        getSupportFragmentManager().beginTransaction()
                                .setCustomAnimations(R.anim.fragment_fade_enter, R.anim.fragment_fade_exit)
                                .replace(R.id.host_fragment_frame_layout, SettingsFragment.newInstance())
                                .addToBackStack(null)
                                .commit();
                        break;
                }
                return true;
            }
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
            /*getSupportFragmentManager().beginTransaction()
                    .replace(R.id.host_fragment_frame_layout, SettingsFragment.newInstance())
                    .addToBackStack(null)
                    .commit();*/
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
    }


    @Override
    public void onAccountClick() {
        Intent intent = new Intent(MainActivity.this, AccountActivity.class);
        startActivity(intent);
    }
}
