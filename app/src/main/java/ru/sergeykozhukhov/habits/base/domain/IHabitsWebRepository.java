package ru.sergeykozhukhov.habits.base.domain;

import java.util.List;

import io.reactivex.Single;
import ru.sergeykozhukhov.habits.base.domain.model.Authentication;
import ru.sergeykozhukhov.habits.base.domain.model.Confidentiality;
import ru.sergeykozhukhov.habits.base.domain.model.Habit;

public interface IHabitsWebRepository {

    Authentication authClient(Confidentiality confidentiality);
    Single<Authentication> authClientRx(Confidentiality confidentiality);

    Single<Habit> insertHabit(Habit habit, String jwt);
    Single<Long> insertHabits(List<Habit> habitList, String jwt);

    Single<List<Habit>> loadListHabits(String jwt);
}
