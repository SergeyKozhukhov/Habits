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

import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.base.presentation.BackupViewModel;
import ru.sergeykozhukhov.habits.base.presentation.ViewModelFactory;

public class SettingsFragment extends Fragment {

    private BackupViewModel backupViewModel;

    private Button testBackupButton;
    private Button testReplicationButton;
    private Button deleteAllHabitsButton;

    public static Fragment newInstance() {

        return new SettingsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.settings_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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

    }

    private void initListeners(){
        testBackupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backupViewModel.insertHabitWithProgressesList();
            }
        });

        testReplicationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                backupViewModel.LoadHabitWithProgressesList();
            }
        });

        deleteAllHabitsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backupViewModel.deleteAllHabits();
            }
        });

    }

    private void initData(){

    }

    private void setupMvvm(){

        backupViewModel = ViewModelProviders.of(this, new ViewModelFactory(requireContext()))
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
            }
        });

        backupViewModel.getErrorSingleLiveEvent().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer idRes) {
                Toast.makeText(requireContext(), getString(idRes), Toast.LENGTH_SHORT).show();
            }
        });


    }


}
