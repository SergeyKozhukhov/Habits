package ru.sergeykozhukhov.habits.notes.fragments.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.notes.database.habit.Habit;
import ru.sergeykozhukhov.habits.notes.fragments.OnItemClickListener;

public class HabitsAdapter extends RecyclerView.Adapter<HabitsAdapter.ViewHolder> {


    /*
     * mOnItemClickListener - обработчик нажатия на ячейку списка
     * */
    private List<Habit> habits;
    private OnItemClickListener mOnItemClickListener;

    /*
     * Конструктор адаптера
     * */

    public HabitsAdapter(OnItemClickListener onItemClickListener) {
        this.habits = habits;
        this.mOnItemClickListener = onItemClickListener;
    }

    public HabitsAdapter(List<Habit> habits, OnItemClickListener onItemClickListener) {
        this.habits = habits;
        this.mOnItemClickListener = onItemClickListener;
    }

    public List<Habit> getHabits() {
        return habits;
    }

    public void setHabits(List<Habit> habits) {

        this.habits = habits;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View view = layoutInflater.inflate(R.layout.item_habits, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Habit habit = habits.get(position);
        holder.bindView(habit, mOnItemClickListener);
    }

    @Override
    public int getItemCount() {
        return habits.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        /*
         * descriptionHabitTextView -
         * - TextView для отображения наименования категории.
         * */
        private TextView descriptionHabitTextView;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            descriptionHabitTextView = itemView.findViewById(R.id.description_habit_text_view);

        }

        void bindView(final Habit habit, final OnItemClickListener listener) {
            descriptionHabitTextView.setText(habit.toString());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(habit);
                }
            });
        }
    }
}
