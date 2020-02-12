package ru.sergeykozhukhov.habits.notes.backup;

import io.reactivex.Completable;

public interface IBackupWebInteractor {
    Completable insertHabits();
    Completable insertProgresses();
    Completable insertHabitsWithProgress();
}
