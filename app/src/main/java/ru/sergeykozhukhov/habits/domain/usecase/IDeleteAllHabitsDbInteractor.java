package ru.sergeykozhukhov.habits.domain.usecase;

import androidx.annotation.NonNull;

import io.reactivex.Completable;

/**
 * Интерфейс интерактора удаления всех привычек (и дат выполнения) из базы данных
 */
public interface IDeleteAllHabitsDbInteractor {

    /**
     * Удаление всех записей о привычках
     */
    @NonNull
    Completable deleteAllHabits();
}
