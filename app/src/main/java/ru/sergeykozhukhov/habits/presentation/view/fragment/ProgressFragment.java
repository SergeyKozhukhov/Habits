package ru.sergeykozhukhov.habits.presentation.view.fragment;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.savvi.rangedatepicker.CalendarPickerView;
import com.savvi.rangedatepicker.SubTitle;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.model.domain.Habit;
import ru.sergeykozhukhov.habits.presentation.factory.ViewModelFactory;
import ru.sergeykozhukhov.habits.presentation.viewmodel.ProgressViewModel;
import ru.sergeykozhukhov.habits.presentation.viewmodel.SharedViewModel;

/**
 * Fragment для изменения списка дат выполнения конкретной привычки
 */
public class ProgressFragment extends Fragment {

    private static final String ARG_HABIT = "ARG_HABIT";
    private static final String EXTRA_TRANSITION_NAME = "transition_name";

    private ProgressViewModel progressViewModel;
    private SharedViewModel sharedViewModel;

    private CalendarPickerView calendarProgressPickerView;

    private CardView cardView;
    private FrameLayout frameLayout;

    private TextView titleHabitTextView;
    private TextView datesHabitTextView;
    private TextView descriptionHabitTextView;

    private ObjectAnimator objectAnimator1;

    public static ProgressFragment newInstance() {
        return new ProgressFragment();
    }

    public static ProgressFragment newInstance(@NonNull Habit habit, String transitionName) {

        ProgressFragment progressFragment = new ProgressFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_HABIT, habit);
        args.putString(EXTRA_TRANSITION_NAME, transitionName);
        progressFragment.setArguments(args);
        return progressFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.progress_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        titleHabitTextView = view.findViewById(R.id.title_habit_text_view_item_habits);
        datesHabitTextView = view.findViewById(R.id.start_date_habit_text_view_item_habits);
        descriptionHabitTextView = view.findViewById(R.id.description_habit_text_view_item_habits);

        calendarProgressPickerView = view.findViewById(R.id.calendar_piker_view);
        cardView = view.findViewById(R.id.progress_card_view);
        frameLayout = view.findViewById(R.id.calendar_piker_view_frame_layout);

        PropertyValuesHolder alphaHolder = PropertyValuesHolder.ofFloat(View.ALPHA, 0, 1);

        objectAnimator1 = ObjectAnimator.ofPropertyValuesHolder(frameLayout, alphaHolder);
        objectAnimator1.setDuration(1500);

        cardView.setTransitionName(getTransitionName());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initData();
        setupMvvm();
        initListeners();

        objectAnimator1.start();
    }

    @Override
    public void onStop() {
        progressViewModel.saveProgressList();
        objectAnimator1.end();
        super.onStop();
    }

    private void initData() {

        Date min = getHabitFromArgs().getStartDate();
        Date max = getHabitFromArgs().getEndDate();

        calendarProgressPickerView.init(min, max);

        Habit habit = getHabitFromArgs();

        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd.MM.yyyy",
                Locale.getDefault()
        );

        titleHabitTextView.setText(habit.getTitle());
        datesHabitTextView.setText(String.format(getString(R.string.dates_progress_fragment_text_view),
                dateFormat.format(habit.getStartDate()), habit.getDuration()));
        descriptionHabitTextView.setText(habit.getDescription());
    }

    private void setupMvvm() {

        progressViewModel = new ViewModelProvider(this, new ViewModelFactory(requireContext())).get(ProgressViewModel.class);
        sharedViewModel = new ViewModelProvider(this, new ViewModelFactory(requireContext())).get(SharedViewModel.class);
        sharedViewModel.getHabitMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Habit>() {
            @Override
            public void onChanged(Habit habit) {

            }
        });

        progressViewModel.getDateListLoadedSingleLiveEvent().observe(getViewLifecycleOwner(), dateList -> {

            Habit habit = getHabitFromArgs();

            Calendar min = habit.getStartDateCalendar();
            Calendar max = habit.getEndDateCalendar();
            max.add(Calendar.DATE, -1);

            SubTitle startDateSubTitle = new SubTitle(min.getTime(), getString(R.string.start_subtitle_calendar));
            SubTitle endDateSubTitle = new SubTitle(max.getTime(), getString(R.string.finish_subtitle_calendar));

            ArrayList<SubTitle> subTitleArrayList = new ArrayList<>(2);
            subTitleArrayList.add(startDateSubTitle);
            subTitleArrayList.add(endDateSubTitle);

            calendarProgressPickerView.init(min.getTime(), habit.getEndDate()) //
                    .inMode(CalendarPickerView.SelectionMode.MULTIPLE)
                    .withSelectedDates(dateList)
                    .withSubTitles(subTitleArrayList);
        });

        progressViewModel.initChangeProgressList(getHabitFromArgs().getIdHabit());

        progressViewModel.getSuccessSingleLiveEvent().observe(getViewLifecycleOwner(),
                idRes -> Toast.makeText(getContext(), getString(idRes), Toast.LENGTH_SHORT).show());

        progressViewModel.getErrorSingleLiveEvent().observe(getViewLifecycleOwner(),
                idRes -> Toast.makeText(getContext(), getString(idRes), Toast.LENGTH_SHORT).show());
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

    private Habit getHabitFromArgs() {
        Bundle arguments = getArguments();
        if (arguments == null)
            throw new IllegalStateException();
        Habit habit = arguments.getParcelable(ARG_HABIT);
        if (habit == null)
            throw new IllegalStateException();
        return habit;
    }

    private String getTransitionName() {
        Bundle arguments = getArguments();
        if (arguments == null)
            throw new IllegalStateException();
        String transitionName = arguments.getString(EXTRA_TRANSITION_NAME);
        if (transitionName == null)
            throw new IllegalStateException();
        return transitionName;
    }
}
