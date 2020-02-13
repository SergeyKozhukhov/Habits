package ru.sergeykozhukhov.habits.base.presentation.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.savvi.rangedatepicker.CalendarPickerView;

import java.util.Date;
import java.util.List;

import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.base.model.domain.Habit;
import ru.sergeykozhukhov.habits.base.presentation.ViewModelFactory;
import ru.sergeykozhukhov.habits.base.presentation.ProgressViewModel;

public class ProgressFragment extends Fragment {

    private static final String TAG = "ProgressFragment";

    private static final String ARG_HABIT = "ARG_HABIT";

    private ProgressViewModel progressViewModel;

    private CalendarPickerView calendarProgressPickerView;

    private TextView progressListTextView;
    private TextView newProgressTextView;

    private Button addProgressButton;

    public static ProgressFragment newInstance() {
        return new ProgressFragment();
    }

    public static ProgressFragment newInstance(@NonNull Habit habit) {

        ProgressFragment progressFragment = new ProgressFragment();
        Bundle args = new Bundle(); // создание объекта для передачи данных
        args.putParcelable(ARG_HABIT, habit); // запись данных по лекции lecture c ключом ARG_LECTURE в bundle
        progressFragment.setArguments(args); // закрепление данных за данных фрагментом с сохранением их при повороте экрана
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

        calendarProgressPickerView = view.findViewById(R.id.calendar_piker_view);


        progressListTextView = view.findViewById(R.id.progress_list_text_view);
        newProgressTextView = view.findViewById(R.id.new_progress_text_view);

        addProgressButton = view.findViewById(R.id.add_progress_button);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initData();
        setupMvvm();
        initListeners();


    }

    @Override
    public void onDestroy() {
        progressViewModel.saveProgressList();
        super.onDestroy();
    }
    private void initData() {

        Date min = getHabitFromArgs().getStartDate();
        Date max = getHabitFromArgs().getEndDate();

        calendarProgressPickerView.init(min, max);
    }

    private void setupMvvm() {
        progressViewModel = ViewModelProviders.of(this, new ViewModelFactory(requireContext()))
                .get(ProgressViewModel.class);

        progressViewModel.getDateListLoadedSingleLiveEvent().observe(getViewLifecycleOwner(), new Observer<List<Date>>() {
            @Override
            public void onChanged(List<Date> dateList) {

                Date min = getHabitFromArgs().getStartDate();
                Date max = getHabitFromArgs().getEndDate();

                calendarProgressPickerView.init(min, max) //
                        .inMode(CalendarPickerView.SelectionMode.MULTIPLE)
                        .withSelectedDates(dateList);
            }
        });

        progressViewModel.initChangeProgressList(getHabitFromArgs().getIdHabit());

        progressViewModel.getProgressInsertedSingleLiveEvent().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isInserted) {
                if (isInserted)
                    Toast.makeText(requireContext(), "success", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(requireContext(), "fail", Toast.LENGTH_SHORT).show();
            }
        });
        progressViewModel.getProgressListInsertedSingleLiveEvent().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isInserted) {
                if (isInserted)
                    Toast.makeText(requireContext(), "success", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(requireContext(), "fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initListeners() {

        calendarProgressPickerView.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                progressViewModel.addProgress(date);
                Toast.makeText(requireContext(), "true: "+date.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDateUnselected(Date date) {
                progressViewModel.deleteProgress(date);
                Toast.makeText(requireContext(), "false: "+date.toString(), Toast.LENGTH_SHORT).show();
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
