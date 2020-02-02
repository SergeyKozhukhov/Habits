package ru.sergeykozhukhov.habits.base.presentation.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.base.data.HabitConverter;
import ru.sergeykozhukhov.habits.base.data.repository.HabitsRepository;
import ru.sergeykozhukhov.habits.base.domain.LoadHabitsInteractor;
import ru.sergeykozhukhov.habits.base.domain.model.Habit;
import ru.sergeykozhukhov.habits.base.presentation.HabitsPresenterViewModel;
import ru.sergeykozhukhov.habits.base.presentation.LoadHabitsView;
import ru.sergeykozhukhov.habits.base.presentation.view.adapter.HabitsListAdapter;

public class HabitsListFragment extends Fragment implements LoadHabitsView {

    private HabitsPresenterViewModel habitsPresenterViewModel;

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
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initData();
        initListeners();
        initViews();
        updateHabitsAdapterRxJava();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        HabitConverter habitConverter = new HabitConverter();
        habitsPresenterViewModel = new HabitsPresenterViewModel(
                new LoadHabitsInteractor(
                        new HabitsRepository(requireContext(),executorService, habitConverter)
                )
        );
        habitsPresenterViewModel.setLoadHabitsView(this);
        habitsPresenterViewModel.loadHabits();

    }

    private void initViews(){
        habitsListAdapter = new HabitsListAdapter();
    }

    private void initListeners(){

    }

    private void initData(){

    }

    private void updateHabitsListAdapter(){

    }

    private void updateHabitsAdapterRxJava()
    {

    }


    @Override
    public void onHabitsLoaded(List<Habit> habitList) {
        Toast.makeText(requireContext(), "success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError() {
        Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show();
    }
}
