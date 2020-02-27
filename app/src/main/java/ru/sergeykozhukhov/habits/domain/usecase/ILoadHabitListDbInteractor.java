package ru.sergeykozhukhov.habits.domain.usecase;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.Flowable;
import ru.sergeykozhukhov.habits.model.domain.Habit;

/**
 * Интерфейс интерактора получения списка привычек из базы данных
 */
public interface ILoadHabitListDbInteractor {

    /**
     * @return
     */
    @NonNull
    Flowable<List<Habit>> loadHabitList();
}
