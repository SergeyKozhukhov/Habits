package ru.sergeykozhukhov.habits.base.domain.IInreractor;

import java.util.List;

import io.reactivex.Single;
import ru.sergeykozhukhov.habits.base.model.domain.Habit;

public interface IReplicationWebInteractor {

    Single<List<Habit>> loadListHabits();

}
