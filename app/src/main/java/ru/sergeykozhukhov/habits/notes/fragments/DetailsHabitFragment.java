package ru.sergeykozhukhov.habits.notes.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.notes.database.HabitsDatabase;
import ru.sergeykozhukhov.habits.notes.database.habit.Habit;
import ru.sergeykozhukhov.habits.notes.database.habit.HabitDao;
import ru.sergeykozhukhov.habits.notes.database.progress.Progress;
import ru.sergeykozhukhov.habits.notes.database.progress.ProgressDao;
import ru.sergeykozhukhov.habits.notes.database.user.UserDao;

public class DetailsHabitFragment extends Fragment {

    private static final String ARG_HABIT = "ARG_HABIT";

    private HabitsDatabase habitsDB;

    private UserDao userDao;
    private HabitDao habitDao;
    private ProgressDao progressDao;

    private Executor executor;

    private EditText details_title_habit_edit_text;
    private EditText details_description_habit_edit_text;
    private EditText details_date_start_edit_text;
    private EditText details_duration_description_edit_text;

    private Button details_update_habit_button;
    private Button details_delete_habit_button;

    private TextView details_progress_date_text_view;
    private Button details_add_progress_button;

    private TextView details_progresses_text_view;

    public static DetailsHabitFragment newInstance(@NonNull Habit habit) {
        DetailsHabitFragment detailsHabitFragment = new DetailsHabitFragment();
        Bundle args = new Bundle(); // создание объекта для передачи данных
        args.putParcelable(ARG_HABIT, habit); // запись данных по лекции lecture c ключом ARG_LECTURE в bundle
        detailsHabitFragment.setArguments(args); // закрепление данных за данных фрагментом с сохранением их при повороте экрана
        return detailsHabitFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.details_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        details_title_habit_edit_text = view.findViewById(R.id.details_title_habit_edit_text_detail);
        details_description_habit_edit_text = view.findViewById(R.id.details_description_habit_edit_text);
        details_date_start_edit_text = view.findViewById(R.id.details_date_start_habit_edit_text);
        details_duration_description_edit_text = view.findViewById(R.id.details_duration_habit_edit_text);

        details_update_habit_button = view.findViewById(R.id.details_update_habit_button);
        details_delete_habit_button = view.findViewById(R.id.details_delete_habit_button);

        details_progress_date_text_view = view.findViewById(R.id.details_progress_date_text_view);
        details_add_progress_button = view.findViewById(R.id.details_add_progress_button);
        details_progresses_text_view = view.findViewById(R.id.details_progresses_text_view);

        Habit habit = getHabitFromArgs();

        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy", // шаблон форматирования
                Locale.getDefault() // язык отображения (получение языка по-умолчанию)
        );

        String dateStartString = dateFormat.format(habit.getDate_start().getTime());

        details_title_habit_edit_text.setText(habit.getTitle());
        details_description_habit_edit_text.setText(habit.getDescription());
        details_date_start_edit_text.setText(dateStartString);
        details_duration_description_edit_text.setText(String.valueOf(habit.getDuration()));



    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initData();
        initViewListeners();

    }

    private void initData(){
        habitsDB = HabitsDatabase.getInstance(requireContext());

        userDao = habitsDB.getUserDao();
        habitDao = habitsDB.getHabitDao();
        progressDao = habitsDB.getProgressDao();

        executor = Executors.newSingleThreadExecutor();

        getProgressById(getHabitFromArgs().getId_habit());
    }

    private void initViewListeners(){

        details_date_start_edit_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCreateDialog(1, setDateStartCallBack).show();
            }
        });

        details_progress_date_text_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCreateDialog(2, setDateProgressCallBack).show();
            }
        });

        details_add_progress_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat dateFormat = new SimpleDateFormat(
                        "dd-MM-yyyy", // шаблон форматирования
                        Locale.getDefault() // язык отображения (получение языка по-умолчанию)
                );
                Date date;
                try {
                    date = dateFormat.parse(details_progress_date_text_view.getText().toString());
                } catch (ParseException e) {
                    date = new Date();
                }

                Progress progress = new Progress();
                progress.setId_habit(getHabitFromArgs().getId_habit());
                progress.setDate_completion(date);
                addProgress(progress);
                Toast.makeText(requireContext(), "Progress added", Toast.LENGTH_SHORT).show();

            }
        });

        details_update_habit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat dateFormat = new SimpleDateFormat(
                        "dd-MM-yyyy", // шаблон форматирования
                        Locale.getDefault() // язык отображения (получение языка по-умолчанию)
                );
                Date date;
                try {
                    date = dateFormat.parse(details_date_start_edit_text.getText().toString());
                } catch (ParseException e) {
                    date = new Date();
                }
                Habit habit = new Habit(
                        details_title_habit_edit_text.getText().toString(),
                        details_description_habit_edit_text.getText().toString(),
                        date,
                        Integer.valueOf(details_duration_description_edit_text.getText().toString())
                );

                habit.setId_habit(getHabitFromArgs().getId_habit());
                updateHabit(habit);
                Toast.makeText(requireContext(), "update success", Toast.LENGTH_LONG).show();
            }
        });


        details_delete_habit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteHabit(getHabitFromArgs());
            }
        });
    }

    private void updateHabit(final Habit habit){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                habitDao.update(habit);
            }
        });
    }

    private void deleteHabit(final Habit habit){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                habitDao.delete(habit);
            }
        });
    }

    private void addProgress(final Progress progress){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                progressDao.insert(progress);
            }
        });
    }

    private void getProgressById(final long idHabit){
        /*executor.execute(new Runnable() {
            @Override
            public void run() {
                List<Progress> progresses = null;
                try {
                    progresses = progressDao.getByHabit(idHabit);
                    StringBuilder stringBuilder = new StringBuilder();
                    for (Progress progress : progresses){
                        stringBuilder.append("date: ");
                        stringBuilder.append(progress.toString());
                        stringBuilder.append("\n");
                    }
                    details_progresses_text_view.setText(stringBuilder);
                } catch (Exception e) {
                    e.printStackTrace();
                    details_progresses_text_view.setText("null");
                }


            }
        });*/


        Disposable disposable;
        disposable = progressDao.getByHabitId(idHabit)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Progress>>() {
                    @Override
                    public void accept(List<Progress> progresses) throws Exception {
                        if (progresses != null){
                            StringBuilder stringBuilder = new StringBuilder();
                            for (Progress progress : progresses){
                                stringBuilder.append("date: ");
                                stringBuilder.append(progress.toString());
                                stringBuilder.append("\n");
                            }
                            details_progresses_text_view.setText(stringBuilder);
                        }

                    }
                });
    }

    @NonNull
    private Habit getHabitFromArgs(){
        Bundle arguments = getArguments();
        if (arguments == null)
            throw new IllegalStateException("Arguments must be set");
        Habit habit = arguments.getParcelable(ARG_HABIT);
        if (habit == null)
            throw new IllegalStateException("Habits must be set");
        return habit;
    }


    protected Dialog onCreateDialog(int id, DatePickerDialog.OnDateSetListener myCallBack) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        DatePickerDialog tpd = new DatePickerDialog(requireContext(), myCallBack, calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        return tpd;
    }

    DatePickerDialog.OnDateSetListener setDateStartCallBack = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.set(year, monthOfYear, dayOfMonth);

            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "dd-MM-yyyy", // шаблон форматирования
                    Locale.getDefault() // язык отображения (получение языка по-умолчанию)
            );

            String dateString = dateFormat.format(calendar.getTime());

            details_date_start_edit_text.setText(dateString);
        }
    };

    DatePickerDialog.OnDateSetListener setDateProgressCallBack = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.set(year, month, dayOfMonth);

            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "dd-MM-yyyy", // шаблон форматирования
                    Locale.getDefault() // язык отображения (получение языка по-умолчанию)
            );

            String dateString = dateFormat.format(calendar.getTime());

            details_progress_date_text_view.setText(dateString);
        }
    };

}
