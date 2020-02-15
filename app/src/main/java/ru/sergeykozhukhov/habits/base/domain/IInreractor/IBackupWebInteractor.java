package ru.sergeykozhukhov.habits.base.domain.IInreractor;

import androidx.annotation.NonNull;

import io.reactivex.Completable;

public interface IBackupWebInteractor {
    @NonNull
    Completable insertHabitWithProgressesList();
}
