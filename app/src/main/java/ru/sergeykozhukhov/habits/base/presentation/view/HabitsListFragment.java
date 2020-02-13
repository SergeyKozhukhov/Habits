package ru.sergeykozhukhov.habits.base.presentation.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.base.model.domain.Habit;
import ru.sergeykozhukhov.habits.base.presentation.BackupViewModel;
import ru.sergeykozhukhov.habits.base.presentation.HabitsListViewModel;
import ru.sergeykozhukhov.habits.base.presentation.ViewModelFactory;
import ru.sergeykozhukhov.habits.base.presentation.view.adapter.HabitsListAdapter;

public class HabitsListFragment extends Fragment{

    private HabitsListViewModel habitsListViewModel;
    private BackupViewModel backupViewModel;

    private HabitsListAdapter.IHabitClickListener habitClickListener;

    private HabitsListAdapter habitsListAdapter;
    private RecyclerView habitsListRecyclerView;


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
        //habitsListRecyclerView.setLayoutManager(new CircleLayoutManager(view.getContext()));
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

        habitClickListener = new HabitsListAdapter.IHabitClickListener() {
            @Override
            public void onItemClick(Habit habit) {
                FragmentActivity activity = getActivity();
                if (activity instanceof ProgressHolder){
                    ((ProgressHolder)activity).showProgress(habit);

                }
            }
        };
        habitsListAdapter.setHabitClickListener(habitClickListener);



    }

    private void initData(){
        habitsListAdapter = new HabitsListAdapter();

        habitsListRecyclerView.setAdapter(habitsListAdapter);
    }

    private void setupMvvm(){
        habitsListViewModel = ViewModelProviders.of(this, new ViewModelFactory(requireContext()))
                .get(HabitsListViewModel.class);



        habitsListViewModel.getHabitListLiveData().observe(getViewLifecycleOwner(), new Observer<List<Habit>>() {
            @Override
            public void onChanged(List<Habit> habitList) {
                ((HabitsListAdapter)habitsListRecyclerView.getAdapter()).setHabitList(habitList);

            }
        });

        habitsListViewModel.loadHabitList();

    }

    public interface ProgressHolder{
        void showProgress(@NonNull Habit habit);
    }

}
