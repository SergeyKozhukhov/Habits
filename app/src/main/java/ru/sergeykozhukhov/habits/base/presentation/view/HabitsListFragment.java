package ru.sergeykozhukhov.habits.base.presentation.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.base.data.HabitConverter;
import ru.sergeykozhukhov.habits.base.data.HabitsConverter;
import ru.sergeykozhukhov.habits.base.domain.model.Habit;
import ru.sergeykozhukhov.habits.base.presentation.HabitsViewModel;
import ru.sergeykozhukhov.habits.base.presentation.HabitsViewModelFactory;
import ru.sergeykozhukhov.habits.base.presentation.view.adapter.HabitsListAdapter;
import ru.sergeykozhukhov.habits.notes.fragments.adapter.HabitsAdapter;

public class HabitsListFragment extends Fragment{

    private HabitsViewModel habitsViewModel;

    private HabitsListAdapter habitsListAdapter;
    private RecyclerView habitsListRecyclerView;

    Disposable disposableLoadHabits;

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
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initData();
        initListeners();
        initViews();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        HabitConverter habitConverter = new HabitConverter();
        HabitsConverter habitsConverter = new HabitsConverter();

        setupMvvm();


    }

    private void initViews(){
        habitsListAdapter = new HabitsListAdapter();
    }

    private void initListeners(){

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
                "title",
                "descr",
                new Date(),
                30);

        Habit habitUpdate = new Habit(
                2,
                10,
                "new title",
                "new descr",
                new Date(),
                100);



        disposableLoadHabits = habitsViewModel.loadHabits()
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Habit>>() {
                    @Override
                    public void accept(List<Habit> habitList) throws Exception {
                        //habitsListAdapter.setHabitList(new ArrayList<>(habitList));
                        ((HabitsListAdapter)habitsListRecyclerView.getAdapter()).setHabitList(habitList);
                        //habitsListRecyclerView.setAdapter(habitsListAdapter);
                        Log.d("fragment", "succes. size: "+habitList.size());

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show();
                        Log.d("fragment", "error");
                    }
                });



        // habitsViewModel.insertHabit(habitInsert);
        Disposable disposableUpdateHabit = habitsViewModel.updateHabit(habitUpdate)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Habit>() {
                    @Override
                    public void accept(Habit habit) throws Exception {
                        Log.d("fragment", "update");
                        //Toast.makeText(requireContext(), "update", Toast.LENGTH_SHORT).show();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });



        /*for (Habit habitTest : habitList){
            Log.d("fragment", habitTest.toString() + "\n");
        }*/
    }

}
