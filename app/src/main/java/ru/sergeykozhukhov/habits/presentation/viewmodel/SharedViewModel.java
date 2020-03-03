package ru.sergeykozhukhov.habits.presentation.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.sergeykozhukhov.habits.model.domain.Habit;

public class SharedViewModel extends ViewModel {

    private final MutableLiveData<Habit> habitMutableLiveData = new MutableLiveData<>();

    public void sendHabit(Habit habit) {
        habitMutableLiveData.setValue(habit);
    }

    public MutableLiveData<Habit> getHabitMutableLiveData() {
        return habitMutableLiveData;
    }
}
