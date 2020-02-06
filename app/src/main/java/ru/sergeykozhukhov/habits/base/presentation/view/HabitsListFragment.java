package ru.sergeykozhukhov.habits.base.presentation.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.base.domain.model.Habit;
import ru.sergeykozhukhov.habits.base.presentation.HabitsViewModel;
import ru.sergeykozhukhov.habits.base.presentation.HabitsViewModelFactory;
import ru.sergeykozhukhov.habits.base.presentation.view.adapter.HabitsListAdapter;

public class HabitsListFragment extends Fragment{

    private HabitsViewModel habitsViewModel;

    private HabitsListAdapter habitsListAdapter;
    private RecyclerView habitsListRecyclerView;

    private Button testBackupButton;
    private Button testReplicationButton;

    private Button deleteAllHabitsButton;


    public static Fragment newInstance() {
        return new HabitsListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.habits_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        habitsListRecyclerView = view.findViewById(R.id.habits_recycler_view);
        habitsListRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        testBackupButton = view.findViewById(R.id.backup_button);
        testReplicationButton = view.findViewById(R.id.replication_button);
        deleteAllHabitsButton = view.findViewById(R.id.delete_all_habits_button);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initData();
        initListeners();
        initViews();

        setupMvvm();


    }

    private void initViews() {
        habitsListAdapter = new HabitsListAdapter();

    }

    private void initListeners(){
        testBackupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Habit habit = new Habit();
                habit.setIdHabit(1);
                habit.setTitle("222");
                habit.setDescription("333");
                habit.setStartDate(new Date());
                habit.setDuration(10);

                List<Habit> habitList = new ArrayList<>();
                habitList.add(habit);
                habitList.add(habit);
                habitList.add(habit);

                habitsViewModel.insertWebHabits(habitsViewModel.getHabitListLiveData().getValue());
            }
        });

        testReplicationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                habitsViewModel.loadListHabitsWeb();
            }
        });

        deleteAllHabitsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                habitsViewModel.deleteAllHabits();
            }
        });



    }

    private void initData(){
        habitsListAdapter = new HabitsListAdapter();

        habitsListRecyclerView.setAdapter(habitsListAdapter);
    }

    private void setupMvvm(){
        habitsViewModel = ViewModelProviders.of(this, new HabitsViewModelFactory(requireContext()))
                .get(HabitsViewModel.class);

        Habit habitInsert = new Habit(
                5,
                "try",
                "one",
                new Date(),
                30);

        Habit habitInsert1 = new Habit(
                4,
                "try1",
                "one1",
                new Date(),
                31);

        Habit habitUpdate = new Habit(
                2,
                12,
                "little",
                "big",
                new Date(),
                100);

        //habitsViewModel.insertHabit(habitInsert);
        //habitsViewModel.insertHabit(habitInsert1);

        habitsViewModel.getHabitListLiveData().observe(getViewLifecycleOwner(), new Observer<List<Habit>>() {
            @Override
            public void onChanged(List<Habit> habitList) {
                ((HabitsListAdapter)habitsListRecyclerView.getAdapter()).setHabitList(habitList);

            }
        });



        habitsViewModel.loadHabits();
        habitsViewModel.loadJwt();

    }

}
