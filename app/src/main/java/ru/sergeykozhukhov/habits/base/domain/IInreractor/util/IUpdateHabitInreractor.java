package ru.sergeykozhukhov.habits.base.domain.IInreractor.util;

import androidx.annotation.NonNull;

import io.reactivex.Completable;
import io.reactivex.Single;
import ru.sergeykozhukhov.habits.base.model.domain.Habit;

/**
 * Интерфейс интерактора обновления информации о конкретной привычке
 */
public interface IUpdateHabitInreractor {
    @NonNull
    Completable updateHabit(Habit habit);
}
