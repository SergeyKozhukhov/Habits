package ru.sergeykozhukhov.habits.domain.usecase;

import androidx.annotation.NonNull;

import io.reactivex.Completable;

/**
 * Интерейс интерактора создания резервной копии данных о привычках на сервере
 */
public interface IBackupWebInteractor {

    /**
     * Сохранение данных о привычкках и соответствующих датав выполнения на сервер
     */
    @NonNull
    Completable insertHabitWithProgressesList();
}
