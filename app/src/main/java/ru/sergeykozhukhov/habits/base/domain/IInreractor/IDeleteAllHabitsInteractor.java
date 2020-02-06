package ru.sergeykozhukhov.habits.base.domain.IInreractor;

import io.reactivex.Completable;

public interface IDeleteAllHabitsInteractor {
    Completable deleteAllHabits();
}
