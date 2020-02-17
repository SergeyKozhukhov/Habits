package ru.sergeykozhukhov.habits.domain.IInreractor;

import androidx.annotation.NonNull;

import io.reactivex.Completable;

/**
 * Интерейс интерактора сохранения на сервер всех записей из базы данных
 */
public interface IBackupWebInteractor {
    @NonNull
    Completable insertHabitWithProgressesList();
}
