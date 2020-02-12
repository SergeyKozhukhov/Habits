package ru.sergeykozhukhov.habits.notes.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.notes.database.HabitsDatabase;
import ru.sergeykozhukhov.habits.notes.database.habit.Habit;
import ru.sergeykozhukhov.habits.notes.database.habit.HabitDao;
import ru.sergeykozhukhov.habits.notes.database.user.UserDao;

public class AddHabitFragment extends Fragment {

    private HabitsDatabase habitsDB;

    private UserDao userDao;
    private HabitDao habitDao;

    private Executor executor;

    private EditText title_habit_edit_text;
    private EditText description_habit_edit_text;
    private EditText date_start_edit_text;
    private EditText duration_description_edit_text;

    private Button add_habit_button;

    public static Fragment newInstance() {
        return new AddHabitFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_habit_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        title_habit_edit_text = view.findViewById(R.id.title_habit_edit_text);
        description_habit_edit_text = view.findViewById(R.id.description_habit_edit_text);
        date_start_edit_text = view.findViewById(R.id.date_start_habit_edit_text);
        duration_description_edit_text = view.findViewById(R.id.duration_habit_edit_text);

        add_habit_button = view.findViewById(R.id.add_habit_button);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initData();
        initViewListeners();
    }

    private void initData(){
        habitsDB = HabitsDatabase.getInstance(requireContext());

        userDao = habitsDB.getUserDao();
        habitDao = habitsDB.getHabitDao();

        executor = Executors.newSingleThreadExecutor();
    }

    private void initViewListeners(){
        add_habit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Habit habit = new Habit(
                        title_habit_edit_text.getText().toString(),
                        description_habit_edit_text.getText().toString(),
                        new Date(),
                        Integer.valueOf(duration_description_edit_text.getText().toString())
                );
                insertHabit(habit);
                Toast.makeText(requireContext(), "insertHabitList success", Toast.LENGTH_LONG).show();


            }
        });
    }

    private void insertHabit(final Habit habit){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                habitDao.insert(habit);
            }
        });
    }

}
