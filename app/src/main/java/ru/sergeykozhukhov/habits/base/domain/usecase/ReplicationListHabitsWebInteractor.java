package ru.sergeykozhukhov.habits.base.domain.usecase;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.functions.Consumer;
import ru.sergeykozhukhov.habits.base.domain.IHabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.base.domain.IHabitsPreferencesRepository;
import ru.sergeykozhukhov.habits.base.domain.IHabitsWebRepository;
import ru.sergeykozhukhov.habits.base.domain.IInreractor.IReplicationWebInteractor;
import ru.sergeykozhukhov.habits.base.model.domain.Habit;
import ru.sergeykozhukhov.habits.base.model.domain.Jwt;

public class ReplicationListHabitsWebInteractor implements IReplicationWebInteractor {

    private IHabitsWebRepository habitsWebRepository;
    private IHabitsDatabaseRepository habitsDatabaseRepository;
    private IHabitsPreferencesRepository habitsPreferencesRepository;

    public ReplicationListHabitsWebInteractor(@NonNull IHabitsWebRepository habitsWebRepository,
                                              @NonNull IHabitsDatabaseRepository habitsDatabaseRepository,
                                              @NonNull IHabitsPreferencesRepository habitsPreferencesRepository) {
        this.habitsWebRepository = habitsWebRepository;
        this.habitsDatabaseRepository = habitsDatabaseRepository;
        this.habitsPreferencesRepository = habitsPreferencesRepository;
    }


    @Override
    public Single<List<Habit>> loadListHabits() {

        String jwt;
        try{
            jwt = habitsWebRepository.getJwt().getJwt();
        } catch (Exception e) {
            jwt = habitsPreferencesRepository.loadJwt().getJwt();
            habitsWebRepository.setJwt(new Jwt(jwt));
            if (jwt == null)
                return null;
            e.printStackTrace();
        }

        return habitsWebRepository.loadListHabits(jwt)
                .doOnSuccess(new Consumer<List<Habit>>() {
                    @Override
                    public void accept(List<Habit> habits) throws Exception {
                        habitsDatabaseRepository.insertListHabits(habits)
                                .subscribe();
                    }
                });

    }
}
