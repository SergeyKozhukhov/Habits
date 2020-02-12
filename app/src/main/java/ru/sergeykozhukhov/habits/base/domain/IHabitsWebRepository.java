package ru.sergeykozhukhov.habits.base.domain;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import ru.sergeykozhukhov.habits.base.model.data.HabitWithProgressesData;
import ru.sergeykozhukhov.habits.base.model.domain.Confidentiality;
import ru.sergeykozhukhov.habits.base.model.domain.Habit;
import ru.sergeykozhukhov.habits.base.model.domain.HabitWithProgresses;
import ru.sergeykozhukhov.habits.base.model.domain.Jwt;
import ru.sergeykozhukhov.habits.base.model.domain.Progress;

public interface IHabitsWebRepository {

    Single<Jwt> authenticateClient(Confidentiality confidentiality);

    Single<Habit> insertHabit(Habit habit, String jwt);
    Completable insertHabits(List<Habit> habitList, String jwt);

    Completable insertProgressList(List<Progress> progressList, String jwt);

    Completable insertHabitWithProgressesList(List<HabitWithProgresses> habitWithProgressesList, String jwt);

    Single<List<Habit>> loadHabitList(String jwt);
    Single<List<HabitWithProgresses>> loadHabitWithProgressesList(String jwt);

    void setJwt(@NonNull Jwt jwt);
    Jwt getJwt();
}
