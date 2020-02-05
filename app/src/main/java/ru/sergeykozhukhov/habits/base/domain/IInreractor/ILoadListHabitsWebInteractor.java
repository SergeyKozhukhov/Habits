package ru.sergeykozhukhov.habits.base.domain.IInreractor;

import java.util.List;

import io.reactivex.Single;
import ru.sergeykozhukhov.habits.base.domain.model.Habit;

public interface ILoadListHabitsWebInteractor {

    Single<List<Habit>> loadListHabits(String jwt);

}
