package ru.sergeykozhukhov.habits.domain.IInreractor;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.Flowable;
import ru.sergeykozhukhov.habits.model.domain.Habit;

/**
 * Интерфейс интерактора получения списка привычек из базы данных
 */
public interface ILoadHabitListDbInteractor {
    @NonNull
    Flowable<List<Habit>> loadHabitList();
}
