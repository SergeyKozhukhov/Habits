package ru.sergeykozhukhov.habits.presentation.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.leochuan.CarouselLayoutManager;
import com.leochuan.CenterSnapHelper;

import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.model.domain.Habit;
import ru.sergeykozhukhov.habits.presentation.viewmodel.HabitsListViewModel;
import ru.sergeykozhukhov.habits.presentation.factory.ViewModelFactory;
import ru.sergeykozhukhov.habits.presentation.view.adapter.HabitsListAdapter;

/**
 * Fragment для получения списка всех привычек из базы данных
 */
public class HabitsListFragment extends Fragment {

    private HabitsListViewModel habitsListViewModel;

    private HabitsListAdapter.IHabitClickListener habitClickListener;

    private HabitsListAdapter habitsListAdapter;
    private RecyclerView habitsListRecyclerView;
    private FloatingActionButton openAddFragmentFloatingActionButton;


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
        habitsListRecyclerView.setLayoutManager(
                new CarouselLayoutManager.Builder(requireContext(), 100)
                        .setMaxVisibleItemCount(3)
                        .setOrientation(CarouselLayoutManager.VERTICAL)
                        .setMinScale(0.001f)
                        .setMoveSpeed(0.25f)
                        .build()

        );
        new CenterSnapHelper().attachToRecyclerView(habitsListRecyclerView);

        openAddFragmentFloatingActionButton = view.findViewById(R.id.add_habit_floating_action_button);
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

    private void initListeners() {

        habitClickListener = habit -> {
            FragmentActivity activity = getActivity();
            if (activity instanceof OnViewsClickListener)
                ((OnViewsClickListener) activity).onItemHabitListClick(habit);
        };
        habitsListAdapter.setHabitClickListener(habitClickListener);

        openAddFragmentFloatingActionButton.setOnClickListener(v -> {
            FragmentActivity activity = getActivity();
            if (activity instanceof OnViewsClickListener)
                ((OnViewsClickListener) activity).onAddHabitClick();
        });
    }

    private void initData() {
        habitsListAdapter = new HabitsListAdapter();
        habitsListRecyclerView.setAdapter(habitsListAdapter);
    }

    private void setupMvvm() {
        habitsListViewModel = new ViewModelProvider(this, new ViewModelFactory(requireContext())).get(HabitsListViewModel.class);

        habitsListViewModel.getHabitListLiveData().observe(getViewLifecycleOwner(), habitList -> {
            if (habitsListRecyclerView.getAdapter() != null)
                ((HabitsListAdapter) habitsListRecyclerView.getAdapter()).setHabitList(habitList);
        });

        habitsListViewModel.getErrorSingleLiveEvent().observe(getViewLifecycleOwner(),
                idRes -> Toast.makeText(requireContext(), getString(idRes), Toast.LENGTH_SHORT).show());

        habitsListViewModel.loadHabitList();
    }

    /**
     * Слушатель нажатий на элементы фрагмента
     */
    public interface OnViewsClickListener {

        /**
         * Обработчик нажатия на кнопку добавления новой привычки
         */
        void onAddHabitClick();

        /**
         * Обработчик нажатия на элемент из списка привычек
         *
         * @param habit привычка
         */
        void onItemHabitListClick(@NonNull Habit habit);
    }

}