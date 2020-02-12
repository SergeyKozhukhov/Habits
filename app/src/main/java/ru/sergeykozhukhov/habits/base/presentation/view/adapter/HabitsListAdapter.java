package ru.sergeykozhukhov.habits.base.presentation.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.base.model.domain.Habit;

public class HabitsListAdapter extends RecyclerView.Adapter<HabitsListAdapter.ViewHolder> {

    private IHabitClickListener habitClickListener;

    private List<Habit> habitList;


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



    class ViewHolder extends RecyclerView.ViewHolder {

        private IHabitClickListener habitClickListener;
        private TextView descriptionHabitTextView;

        public ViewHolder(@NonNull final View itemView, IHabitClickListener habitClickListener) {
            super(itemView);
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
            descriptionHabitTextView.setText(habit.toString());
        }
    }

    public interface IHabitClickListener {
        void onItemClick(Habit habit);
    }
}

