package ru.sergeykozhukhov.habits.base.presentation.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import java.util.Date;

import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.base.domain.model.Habit;
import ru.sergeykozhukhov.habits.base.presentation.HabitsViewModel;
import ru.sergeykozhukhov.habits.base.presentation.HabitsViewModelFactory;

public class AddHabitFragment extends Fragment {

    private HabitsViewModel habitsViewModel;

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
        setupMvvm();
    }

    private void initData(){

    }

    private void initViewListeners(){
        add_habit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Habit habit = new Habit();
                habit.setIdHabit(1);
                habit.setTitle(title_habit_edit_text.getText().toString());
                habit.setDescription(description_habit_edit_text.getText().toString());
                habit.setStartDate(new Date());
                habit.setDuration(Integer.valueOf(duration_description_edit_text.getText().toString()));

                habitsViewModel.insertWebHabit(habit);

            }
        });
    }

    private void setupMvvm(){
        habitsViewModel = ViewModelProviders.of(this, new HabitsViewModelFactory(requireContext()))
                .get(HabitsViewModel.class);

    }

}
