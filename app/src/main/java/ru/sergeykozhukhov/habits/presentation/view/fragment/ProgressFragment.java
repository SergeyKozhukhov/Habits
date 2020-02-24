package ru.sergeykozhukhov.habits.presentation.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.savvi.rangedatepicker.CalendarPickerView;
import com.savvi.rangedatepicker.SubTitle;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.model.domain.Habit;
import ru.sergeykozhukhov.habits.presentation.factory.ViewModelFactory;
import ru.sergeykozhukhov.habits.presentation.viewmodel.ProgressViewModel;

public class ProgressFragment extends Fragment {

    private static final String TAG = "ProgressFragment";

    private static final String ARG_HABIT = "ARG_HABIT";

    private ProgressViewModel progressViewModel;

    private CalendarPickerView calendarProgressPickerView;

    private TextView titleHabitTextView;
    private TextView startDateHabitTextView;
    private TextView durationHabitTextView;
    private TextView descriptionHabitTextView;


    public static ProgressFragment newInstance() {
        return new ProgressFragment();
    }

    public static ProgressFragment newInstance(@NonNull Habit habit) {

        ProgressFragment progressFragment = new ProgressFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_HABIT, habit);
        progressFragment.setArguments(args);
        return progressFragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.progress_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        titleHabitTextView = view.findViewById(R.id.title_habit_text_view);
        startDateHabitTextView = view.findViewById(R.id.start_date_habit_text_view);
        durationHabitTextView = view.findViewById(R.id.duration_habit_text_view);
        descriptionHabitTextView = view.findViewById(R.id.description_habit_text_view);

        calendarProgressPickerView = view.findViewById(R.id.calendar_piker_view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initData();
        setupMvvm();
        initListeners();


    }

    @Override
    public void onStop() {
        progressViewModel.saveProgressList();
        super.onStop();
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }
    private void initData() {

        Date min = getHabitFromArgs().getStartDate();
        Date max = getHabitFromArgs().getEndDate();

        calendarProgressPickerView.init(min, max);

        Habit habit = getHabitFromArgs();

        titleHabitTextView.setText(habit.getTitle());
        startDateHabitTextView.setText(habit.getStartDate().toString());
        durationHabitTextView.setText("Продолжительность: " + String.valueOf(habit.getDuration()));
        descriptionHabitTextView.setText(habit.getDescription());


    }

    private void setupMvvm() {

        progressViewModel = new ViewModelProvider(this, new ViewModelFactory(requireContext())).get(ProgressViewModel.class);

        progressViewModel.getDateListLoadedSingleLiveEvent().observe(getViewLifecycleOwner(), new Observer<List<Date>>() {
            @Override
            public void onChanged(List<Date> dateList) {

                Habit habit = getHabitFromArgs();

                Calendar min = habit.getStartDateCalendar();
                Calendar max = habit.getEndDateCalendar();
                max.add(Calendar.DATE, -1);

                SubTitle startDateSubTitle = new SubTitle(min.getTime(), "Старт");
                SubTitle endDateSubTitle = new SubTitle(max.getTime(), "Финиш");

                ArrayList<SubTitle> subTitleArrayList = new ArrayList<>(2);
                subTitleArrayList.add(startDateSubTitle);
                subTitleArrayList.add(endDateSubTitle);

                calendarProgressPickerView.init(min.getTime(), habit.getEndDate()) //
                        .inMode(CalendarPickerView.SelectionMode.MULTIPLE)
                        .withSelectedDates(dateList)
                        .withSubTitles(subTitleArrayList);
            }
        });

        progressViewModel.initChangeProgressList(getHabitFromArgs().getIdHabit());

        progressViewModel.getSuccessSingleLiveEvent().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer idRes) {
                Toast.makeText(getContext(), getString(idRes), Toast.LENGTH_SHORT).show();
            }
        });

        progressViewModel.getErrorSingleLiveEvent().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer idRes) {
                Toast.makeText(getContext(), getString(idRes), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initListeners() {

        calendarProgressPickerView.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                progressViewModel.addProgress(date);
            }

            @Override
            public void onDateUnselected(Date date) {
                progressViewModel.deleteProgress(date);
            }
        });
    }

    private Habit getHabitFromArgs(){
        Bundle arguments = getArguments();
        if (arguments == null)
            throw new IllegalStateException("Arguments must be set");
        Habit habit = arguments.getParcelable(ARG_HABIT);
        if (habit == null)
            throw new IllegalStateException("Habits must be set");
        return habit;
    }
}