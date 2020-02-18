package ru.sergeykozhukhov.habits.presentation.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.leochuan.CarouselLayoutManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.model.domain.Habit;

public class HabitsListAdapter extends RecyclerView.Adapter<HabitsListAdapter.ViewHolder> {

    private IHabitClickListener habitClickListener;

    private List<Habit> habitList;

    private static int NEXT_ELEM = 1;
    private static int NEXT_NEXT_ELEM = 2;
    private static int LAST_ELEM = -1;
    private static int LAST_LAST_ELEM = -2;


    public HabitsListAdapter() {

        habitList = new ArrayList<>();
        habitClickListener = null;
    }

    public HabitsListAdapter(List<Habit> habits) {
        this.habitList = habits;
    }

    public List<Habit> getHabitList() {
        return habitList;
    }

    public void setHabitList(List<Habit> habitList) {

        this.habitList = habitList;
        notifyDataSetChanged();
    }

    public void setHabitClickListener(IHabitClickListener habitClickListener)
    {
        this.habitClickListener = habitClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View view = layoutInflater.inflate(R.layout.item_habits, parent, false);
        return new ViewHolder(view, habitClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Habit habit = habitList.get(position);
        holder.bindView(habit);
    }

    @Override
    public int getItemCount() {
        return habitList.size();
    }


    @Override
    public int getItemViewType(int position) {
        if (position>=1  && position <= getItemCount()-1){

        }
        return super.getItemViewType(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private IHabitClickListener habitClickListener;

        private RelativeLayout relativeLayout;

        private TextView titleHabitTextView;
        private TextView startDateHabitTextView;
        private TextView durationHabitTextView;
        private TextView descriptionHabitTextView;

        SimpleDateFormat dateFormat;
        GregorianCalendar calendar;




        public ViewHolder(@NonNull final View itemView, IHabitClickListener habitClickListener) {
            super(itemView);



            dateFormat = new SimpleDateFormat(
                    "EEEE, d MMMM yyyy", // шаблон форматирования
                    Locale.getDefault() // язык отображения (получение языка по-умолчанию)
            );

            calendar = new GregorianCalendar();

            relativeLayout = itemView.findViewById(R.id.habit_item_relative_layout);

            titleHabitTextView = itemView.findViewById(R.id.title_habit_text_view);
            startDateHabitTextView = itemView.findViewById(R.id.start_date_habit_text_view);
            durationHabitTextView = itemView.findViewById(R.id.duration_habit_text_view);
            descriptionHabitTextView = itemView.findViewById(R.id.description_habit_text_view);
            this.habitClickListener = habitClickListener;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    habitClickListener.onItemClick(habitList.get(getAdapterPosition()));
                }
            });

        }

        void bindView(final Habit habit) {

            calendar.setTime(habit.getStartDate());

            titleHabitTextView.setText(habit.getTitle());
            startDateHabitTextView.setText("Начало: " + dateFormat.format(calendar.getTime()));
            durationHabitTextView.setText("Продолжительность: " + String.valueOf(habit.getDuration()));
            descriptionHabitTextView.setText(habit.getDescription());





            //relativeLayout.setBackground(R.drawable.ic_2);



        }
    }




    public interface IHabitClickListener {
        void onItemClick(Habit habit);
    }
}

