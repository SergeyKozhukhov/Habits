package ru.sergeykozhukhov.habits.base.domain.IInreractor;

import androidx.annotation.NonNull;

import io.reactivex.Completable;

/**
 * Интерфейс интерактора удаления всех привычек (и дат выполнения) из базы данных
 */
public interface IDeleteAllHabitsDbInteractor {
    @NonNull
    Completable deleteAllHabits();
}
