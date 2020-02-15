package ru.sergeykozhukhov.habits;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.base.presentation.view.AddHabitFragment;
import ru.sergeykozhukhov.habits.base.presentation.view.AuthenticationFragment;
import ru.sergeykozhukhov.habits.base.presentation.view.HabitsListFragment;
import ru.sergeykozhukhov.habits.base.presentation.view.ProgressFragment;
import ru.sergeykozhukhov.habits.base.presentation.view.RegistrationFragment;
import ru.sergeykozhukhov.habits.base.presentation.view.SettingsFragment;
import ru.sergeykozhukhov.habits.base.presentation.view.StatisticsFragment;
import ru.sergeykozhukhov.habits.notes.database.HabitsDatabase;

public class MainActivity extends AppCompatActivity implements HabitsListFragment.ProgressHolder,
        HabitsListFragment.OnAddClickListener, SettingsFragment.OnClientClickListener {

    private Button open_statistics_button;

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
        open_statistics_button = findViewById(R.id.open_statistics_button);
    }

    public void initListeners(){

        open_statistics_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.host_fragment_frame_layout, StatisticsFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
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
        if (item.getItemId() == R.id.open_settings_item_menu) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.host_fragment_frame_layout, SettingsFragment.newInstance())
                    .addToBackStack(null)
                    .commit();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showProgress(@NonNull ru.sergeykozhukhov.habits.base.model.domain.Habit habit) {
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
    public void onRegistrationClick() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.host_fragment_frame_layout, RegistrationFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onAuthenticationClick() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.host_fragment_frame_layout, AuthenticationFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }
}
