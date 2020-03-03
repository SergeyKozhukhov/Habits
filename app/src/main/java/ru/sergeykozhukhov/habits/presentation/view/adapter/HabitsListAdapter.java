package ru.sergeykozhukhov.habits.presentation.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

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

    public HabitsListAdapter() {

        habitList = new ArrayList<>();
        habitClickListener = null;
    }

    public void setHabitList(List<Habit> habitList) {

        this.habitList = habitList;
        notifyDataSetChanged();
    }

    public void setHabitClickListener(IHabitClickListener habitClickListener) {
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
        return super.getItemViewType(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;

        private TextView titleHabitTextView;
        private TextView startDateHabitTextView;
        private TextView descriptionHabitTextView;
        private TextView idHabitTextView;

        private SimpleDateFormat dateFormat;
        private GregorianCalendar calendar;


        public ViewHolder(@NonNull final View itemView, IHabitClickListener habitClickListener) {
            super(itemView);


            dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

            calendar = new GregorianCalendar();

            cardView = itemView.findViewById(R.id.host_habit_card_view);

            titleHabitTextView = itemView.findViewById(R.id.title_habit_text_view_item_habits);
            startDateHabitTextView = itemView.findViewById(R.id.start_date_habit_text_view_item_habits);
            descriptionHabitTextView = itemView.findViewById(R.id.description_habit_text_view_item_habits);
            idHabitTextView = itemView.findViewById(R.id.id_habit_text_view_item_habits);


            itemView.setOnClickListener(v -> habitClickListener.onItemClick(habitList.get(getAdapterPosition()), cardView));

        }

        void bindView(final Habit habit) {

            ViewCompat.setTransitionName(cardView, "transitionName" + (habitList.get(getAdapterPosition()).getIdHabit()));

            calendar.setTime(habit.getStartDate());

            titleHabitTextView.setText(habit.getTitle());
            startDateHabitTextView.setText(String.format(itemView.getResources()
                    .getString(R.string.dates_habit_item_text_view), dateFormat.format(habit.getStartDate()), habit.getDuration()));
            String string = habit.getDescription();
            if (string.length() > 35) {
                String shortDescription = string.substring(0, 32);
                int index = shortDescription.lastIndexOf(" ");
                if (index < 0)
                    descriptionHabitTextView.setText(String.format("%s...", shortDescription));
                else
                    descriptionHabitTextView.setText(String.format("%s...", shortDescription.substring(0, index)));
            } else
                descriptionHabitTextView.setText(habit.getDescription());

            int id = habitList.size() - getAdapterPosition();
            idHabitTextView.setText(String.format(itemView.getResources().getString(R.string.id_habit_item_text_view), id));

        }
    }

    public interface IHabitClickListener {
        void onItemClick(Habit habit, View view);
    }
}

