package ru.sergeykozhukhov.habits.base.domain.IInreractor;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.Flowable;
import ru.sergeykozhukhov.habits.base.model.domain.Habit;

/**
 * Интерфейс интерактора получения списка привычек из базы данных
 */
public interface ILoadHabitListDbInteractor {
    @NonNull
    Flowable<List<Habit>> loadHabitList();
}
