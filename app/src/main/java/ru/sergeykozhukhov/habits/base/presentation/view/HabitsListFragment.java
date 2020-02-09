package ru.sergeykozhukhov.habits.base.presentation.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

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
import ru.sergeykozhukhov.habits.base.model.domain.Habit;
import ru.sergeykozhukhov.habits.base.presentation.BackupViewModel;
import ru.sergeykozhukhov.habits.base.presentation.HabitsListViewModel;
import ru.sergeykozhukhov.habits.base.presentation.HabitsViewModelFactory;
import ru.sergeykozhukhov.habits.base.presentation.view.adapter.HabitsListAdapter;

public class HabitsListFragment extends Fragment{

    private HabitsListViewModel habitsListViewModel;
    private BackupViewModel backupViewModel;

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

                backupViewModel.insertWebHabits();

                //backupViewModel.insertWebHabits();
                //habitsListViewModel .insertWebHabits(habitsListViewModel.getHabitListLiveData().getValue());
            }
        });

        testReplicationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //habitsListViewModel.loadListHabitsWeb();
                backupViewModel.loadListHabitsWeb();
            }
        });

        deleteAllHabitsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                habitsListViewModel.deleteAllHabits();
            }
        });



    }

    private void initData(){
        habitsListAdapter = new HabitsListAdapter();

        habitsListRecyclerView.setAdapter(habitsListAdapter);
    }

    private void setupMvvm(){
        habitsListViewModel = ViewModelProviders.of(this, new HabitsViewModelFactory(requireContext()))
                .get(HabitsListViewModel.class);



        habitsListViewModel.getHabitListLiveData().observe(getViewLifecycleOwner(), new Observer<List<Habit>>() {
            @Override
            public void onChanged(List<Habit> habitList) {
                ((HabitsListAdapter)habitsListRecyclerView.getAdapter()).setHabitList(habitList);

            }
        });

        habitsListViewModel.loadHabits();

        backupViewModel = ViewModelProviders.of(this, new HabitsViewModelFactory(requireContext()))
                .get(BackupViewModel.class);

        backupViewModel.getIsLoadedSingleLiveEvent().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoaded) {
                if(isLoaded)
                    Toast.makeText(requireContext(), "success", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(requireContext(), "fail", Toast.LENGTH_SHORT).show();
            }
        });

        backupViewModel.getIsInsertedSingleLiveEvent().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isInserted) {
                if (isInserted)
                    Toast.makeText(requireContext(), "success", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(requireContext(), "fail", Toast.LENGTH_SHORT).show();
            }
        });


    }

}
