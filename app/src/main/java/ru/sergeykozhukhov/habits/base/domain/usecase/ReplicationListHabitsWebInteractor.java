package ru.sergeykozhukhov.habits.base.domain.usecase;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.functions.Consumer;
import ru.sergeykozhukhov.habits.base.domain.IHabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.base.domain.IHabitsWebRepository;
import ru.sergeykozhukhov.habits.base.domain.IInreractor.IReplicationWebInteractor;
import ru.sergeykozhukhov.habits.base.domain.IInreractor.provider.IGetJwtValue;
import ru.sergeykozhukhov.habits.base.model.domain.HabitWithProgresses;
import ru.sergeykozhukhov.habits.base.model.exception.GetJwtException;

public class ReplicationListHabitsWebInteractor implements IReplicationWebInteractor {

    private final IHabitsWebRepository habitsWebRepository;
    private final IHabitsDatabaseRepository habitsDatabaseRepository;
    private final IGetJwtValue getJwtValue;

    public ReplicationListHabitsWebInteractor(@NonNull IHabitsWebRepository habitsWebRepository,
                                              @NonNull IHabitsDatabaseRepository habitsDatabaseRepository,
                                              @NonNull IGetJwtValue getJwtValue) {
        this.habitsWebRepository = habitsWebRepository;
        this.habitsDatabaseRepository = habitsDatabaseRepository;
        this.getJwtValue = getJwtValue;
    }


    @Override
    public Single<List<HabitWithProgresses>> loadHabitWithProgressesList() {

        String jwt = null;
        try {
            jwt = getJwtValue.getValue();
        } catch (GetJwtException e) {
            return Single.error(e);
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
