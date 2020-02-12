package ru.sergeykozhukhov.habits;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.base.presentation.view.AddHabitFragment;
import ru.sergeykozhukhov.habits.base.presentation.view.AuthenticationFragment;
import ru.sergeykozhukhov.habits.base.presentation.view.HabitsListFragment;
import ru.sergeykozhukhov.habits.base.presentation.view.ProgressFragment;
import ru.sergeykozhukhov.habits.notes.database.HabitsDatabase;

public class MainActivity extends AppCompatActivity implements HabitsListFragment.ProgressHolder {

    private HabitsDatabase habitsDB;

    private Button open_auth_button;
    private Button open_habits_button;
    private Button open_add_habit_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        habitsDB = HabitsDatabase.getInstance(this);

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.host_fragment_frame_layout, HabitsListFragment.newInstance())
                    .commit();
        }

        initViews();
    }

    private void initViews(){
        open_auth_button = findViewById(R.id.open_auth_button);
        open_habits_button = findViewById(R.id.open_habits_button);
        open_add_habit_button = findViewById(R.id.open_add_habit_button);

        open_auth_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.host_fragment_frame_layout, AuthenticationFragment.newInstance())
                        .commit();
            }
        });


        open_habits_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.host_fragment_frame_layout, HabitsListFragment.newInstance())
                        .commit();
            }
        });

        open_add_habit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.host_fragment_frame_layout, AddHabitFragment.newInstance())
                        .commit();
            }
        });
    }

    @Override
    public void showProgress(@NonNull ru.sergeykozhukhov.habits.base.model.domain.Habit habit) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.host_fragment_frame_layout, ProgressFragment.newInstance(habit))
                .addToBackStack(null)
                .commit();
    }
}
