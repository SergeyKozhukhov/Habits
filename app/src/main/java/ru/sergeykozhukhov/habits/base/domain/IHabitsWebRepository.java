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
import ru.sergeykozhukhov.habits.base.model.domain.Registration;

public interface IHabitsWebRepository extends IRepository {

    Completable registrateClient(@NonNull Registration registration);
    Single<Jwt> authenticateClient(@NonNull Confidentiality confidentiality);

    Single<Habit> insertHabit(Habit habit, @NonNull String jwt);
    Completable insertHabits(List<Habit> habitList, @NonNull String jwt);

    Completable insertProgressList(List<Progress> progressList, @NonNull String jwt);

    Completable insertHabitWithProgressesList(List<HabitWithProgresses> habitWithProgressesList, @NonNull String jwt);

    Single<List<Habit>> loadHabitList(@NonNull String jwt);
    Single<List<HabitWithProgresses>> loadHabitWithProgressesList(@NonNull String jwt);

    void setJwt(@NonNull Jwt jwt);
    Jwt getJwt();
}
