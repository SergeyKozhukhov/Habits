package ru.sergeykozhukhov.habits.base.domain.usecase;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.functions.Consumer;
import ru.sergeykozhukhov.habits.base.domain.IHabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.base.domain.IHabitsPreferencesRepository;
import ru.sergeykozhukhov.habits.base.domain.IHabitsWebRepository;
import ru.sergeykozhukhov.habits.base.domain.IInreractor.IReplicationWebInteractor;
import ru.sergeykozhukhov.habits.base.model.domain.HabitWithProgresses;
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
    public Single<List<HabitWithProgresses>> loadHabitWithProgressesList() {
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

        return habitsWebRepository.loadHabitWithProgressesList(jwt)
                .doOnSuccess(new Consumer<List<HabitWithProgresses>>() {
                    @Override
                    public void accept(List<HabitWithProgresses> habitWithProgresses) throws Exception {
                        habitsDatabaseRepository.deleteAllHabits().subscribe();
                        habitsDatabaseRepository.insertHabitWithProgressesList(habitWithProgresses)
                                .subscribe();
                    }
                });
    }
}
