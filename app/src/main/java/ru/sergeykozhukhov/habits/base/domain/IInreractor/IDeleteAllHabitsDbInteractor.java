package ru.sergeykozhukhov.habits.base.domain.IInreractor;

import io.reactivex.Completable;

public interface IDeleteAllHabitsDbInteractor {
    Completable deleteAllHabits();
}
