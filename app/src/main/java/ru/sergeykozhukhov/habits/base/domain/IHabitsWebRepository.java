package ru.sergeykozhukhov.habits.base.domain;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import ru.sergeykozhukhov.habits.base.model.domain.Confidentiality;
import ru.sergeykozhukhov.habits.base.model.domain.Habit;
import ru.sergeykozhukhov.habits.base.model.domain.Jwt;

public interface IHabitsWebRepository {

    Single<Jwt> authClientRx(Confidentiality confidentiality);

    Single<Habit> insertHabit(Habit habit, String jwt);
    Completable insertHabits(List<Habit> habitList, String jwt);

    Single<List<Habit>> loadListHabits(String jwt);

    void setJwt(@NonNull Jwt jwt);
    Jwt getJwt();
}
