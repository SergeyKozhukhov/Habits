package ru.sergeykozhukhov.habits.base.presentation.view;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.savvi.rangedatepicker.CalendarPickerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.base.model.domain.Habit;
import ru.sergeykozhukhov.habits.base.model.domain.Progress;
import ru.sergeykozhukhov.habits.base.presentation.HabitsViewModelFactory;
import ru.sergeykozhukhov.habits.base.presentation.ProgressViewModel;

public class ProgressFragment extends Fragment {

    private static final String TAG = "ProgressFragment";

    private static final String ARG_HABIT = "ARG_HABIT";

    private ProgressViewModel progressViewModel;

    private DatePickerDialog.OnDateSetListener setProgressDateCallBack;

    private CalendarView progressListCalendarView;
    private CalendarPickerView calendarPickerView;

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

        progressListCalendarView = view.findViewById(R.id.progress_list_calendar_view);
        calendarPickerView = view.findViewById(R.id.calendar_piker_view);

        progressListTextView = view.findViewById(R.id.progress_list_text_view);
        newProgressTextView = view.findViewById(R.id.new_progress_text_view);

        addProgressButton = view.findViewById(R.id.add_progress_button);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initData();
        initListeners();
        setupMvvm();


    }

    private void initListeners() {

        addProgressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Calendar> calendarList = progressListCalendarView.getSelectedDates();

                List<Progress> progresses = new ArrayList<>(calendarList.size());
                for (Calendar calendar : calendarList){
                    progresses.add(
                     new Progress(getHabitFromArgs().getIdHabit(), calendar.getTime())
                    );
                }
                //progressViewModel.insertProgressList(progresses);

                List<Date> dateList = calendarPickerView.getSelectedDates();

                List<Progress> progressList = new ArrayList<>(dateList.size());
                for(Date date : dateList){
                    progressList.add(new Progress(getHabitFromArgs().getIdHabit(), date));
                }

                progressViewModel.insertProgressList(progressList);

                /*GregorianCalendar calendar = new GregorianCalendar();
                calendar.setTime(new Date());
                new DatePickerDialog(requireContext(), setProgressDateCallBack,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();*/
            }
        });

        setProgressDateCallBack = new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                GregorianCalendar calendar = new GregorianCalendar();
                calendar.set(year, monthOfYear, dayOfMonth);

                SimpleDateFormat dateFormat = new SimpleDateFormat(
                        "dd-MM-yyyy", // шаблон форматирования
                        Locale.getDefault() // язык отображения (получение языка по-умолчанию)
                );

                Habit habit = getHabitFromArgs();

                String dateString = dateFormat.format(calendar.getTime());
                newProgressTextView.setText(dateString);
                progressViewModel.insertProgress(
                        new Progress(
                                habit.getIdHabit(),
                                calendar.getTime()
                        )

                );

            }
        };

        progressListCalendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                Toast.makeText(requireContext(), String.valueOf(eventDay.isEnabled()) + " "+eventDay.getCalendar().getTime().toString(), Toast.LENGTH_SHORT).show();
            }
        });


        calendarPickerView.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                Toast.makeText(requireContext(), "true: "+date.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDateUnselected(Date date) {
                Toast.makeText(requireContext(), "false: "+date.toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void initData() {



        Calendar maxDate = Calendar.getInstance();
        maxDate.set(2020, 3, 1);
        Calendar minDate = Calendar.getInstance();
        minDate.set(2020, 1, 1);

        Calendar markDate = Calendar.getInstance();
        markDate.set(2020, 2, 1);

        calendarPickerView.init(minDate.getTime(), maxDate.getTime());


// highlight dates in red color, mean they are aleady used.
               // .withHighlightedDate(gregorianCalendar2.getTime());

        newProgressTextView.setText(
                getHabitFromArgs().getIdHabit() + " "
                + getHabitFromArgs().getTitle());


    }

    private void setupMvvm() {
        progressViewModel = ViewModelProviders.of(this, new HabitsViewModelFactory(requireContext()))
                .get(ProgressViewModel.class);
        progressViewModel.getProgressListLoadedSingleLiveEvent().observe(getViewLifecycleOwner(), new Observer<List<Progress>>() {
            @Override
            public void onChanged(List<Progress> progresses) {
                StringBuilder stringBuilder = new StringBuilder();
                for (Progress progress : progresses){
                    stringBuilder.append(progress.toString());
                    stringBuilder.append("\n");
                }

                progressListTextView.setText(stringBuilder);
                List<Calendar> calendars = new ArrayList<>(progresses.size());
                List<Date> dateList = new ArrayList<>(progresses.size());
                for(Progress progress : progresses){
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(progress.getDate());
                    calendars.add(calendar);
                    dateList.add(progress.getDate());
                    //Log.d(TAG, calendar.toString());
                }

                progressListCalendarView.setSelectedDates(calendars);

                Date min = getHabitFromArgs().getStartDate();

                GregorianCalendar gregorianCalendar = new GregorianCalendar();
                gregorianCalendar.setTime(min);
                gregorianCalendar.add(Calendar.DAY_OF_MONTH, getHabitFromArgs().getDuration());

                Log.d(TAG, min.toString()+" + days "+getHabitFromArgs().getDuration()+" = "+gregorianCalendar.getTime().toString());



                calendarPickerView.init(min, gregorianCalendar.getTime()) //
                        .inMode(CalendarPickerView.SelectionMode.MULTIPLE)
                        .withSelectedDates(dateList);

                Toast.makeText(requireContext(), "success", Toast.LENGTH_SHORT).show();


            }
        });
        progressViewModel.loadProgressListByHabit(getHabitFromArgs().getIdHabit());
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
