package ru.sergeykozhukhov.habits.base.domain.IInreractor;

import androidx.annotation.Nullable;

import java.util.Date;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface IChangeProgressListDbInteractor {

    Single<List<Date>> getProgressList(long idHabit);
    void addNewDate(Date date);
    void deleteDate(Date date);

    @Nullable
    Completable saveProgressList();

}
