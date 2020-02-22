package ru.sergeykozhukhov.habits.presentation.view;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.presentation.viewmodel.AddHabitViewModel;
import ru.sergeykozhukhov.habits.presentation.factory.ViewModelFactory;

public class AddHabitFragment extends Fragment {

    private AddHabitViewModel addHabitViewModel;

    private DatePickerDialog.OnDateSetListener setStartDateCallBack;

    private EditText title_habit_edit_text;
    private EditText description_habit_edit_text;
    private EditText date_start_edit_text;
    private EditText duration_description_edit_text;

    private Button add_habit_button;
    private ImageButton date_start_image_button;


    public static Fragment newInstance() {
        return new AddHabitFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_habit_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        title_habit_edit_text = view.findViewById(R.id.title_habit_edit_text);
        description_habit_edit_text = view.findViewById(R.id.description_habit_edit_text);
        date_start_edit_text = view.findViewById(R.id.date_start_habit_edit_text);
        duration_description_edit_text = view.findViewById(R.id.duration_habit_edit_text);

        add_habit_button = view.findViewById(R.id.add_habit_button);
        date_start_image_button = view.findViewById(R.id.date_start_habit_image_button);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initData();
        initViewListeners();
        setupMvvm();
    }

    private void initData(){

    }

    private void initViewListeners(){
        add_habit_button.setOnClickListener(v -> addHabitViewModel.insertHabit(
                title_habit_edit_text.getText().toString(),
                description_habit_edit_text.getText().toString(),
                date_start_edit_text.getText().toString(),
                duration_description_edit_text.getText().toString()
        ));

        date_start_image_button.setOnClickListener(v -> {
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(new Date());
            new DatePickerDialog(requireContext(), setStartDateCallBack,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        setStartDateCallBack = (view, year, monthOfYear, dayOfMonth) -> {
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.set(year, monthOfYear, dayOfMonth);

            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "dd-MM-yyyy", // шаблон форматирования
                    Locale.getDefault() // язык отображения (получение языка по-умолчанию)
            );

            String dateString = dateFormat.format(calendar.getTime());
            date_start_edit_text.setText(dateString);

        };
    }

    private void setupMvvm(){

        addHabitViewModel = new ViewModelProvider(this, new ViewModelFactory(requireContext())).get(AddHabitViewModel.class);

        addHabitViewModel.getInsertedSuccessSingleLiveEvent().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer idRes) {
                Toast.makeText(requireContext(), getString(idRes), Toast.LENGTH_SHORT).show();
            }
        });



        addHabitViewModel.getErrorSingleLiveEvent().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer idRes) {
                Toast.makeText(requireContext(), getString(idRes), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
