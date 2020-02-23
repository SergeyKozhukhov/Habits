package ru.sergeykozhukhov.habits.domain.usecase;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.domain.IHabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.domain.IHabitsWebRepository;
import ru.sergeykozhukhov.habits.domain.IInreractor.IReplicationWebInteractor;
import ru.sergeykozhukhov.habits.domain.IInreractor.IGetJwtValueInteractor;
import ru.sergeykozhukhov.habits.model.domain.HabitWithProgresses;
import ru.sergeykozhukhov.habits.model.exception.GetJwtException;
import ru.sergeykozhukhov.habits.model.exception.ReplicationException;

public class ReplicationListHabitsWebInteractor implements IReplicationWebInteractor {

    private final IHabitsWebRepository habitsWebRepository;
    private final IHabitsDatabaseRepository habitsDatabaseRepository;
    private final IGetJwtValueInteractor getJwtValue;

    public ReplicationListHabitsWebInteractor(@NonNull IHabitsWebRepository habitsWebRepository,
                                              @NonNull IHabitsDatabaseRepository habitsDatabaseRepository,
                                              @NonNull IGetJwtValueInteractor getJwtValue) {
        this.habitsWebRepository = habitsWebRepository;
        this.habitsDatabaseRepository = habitsDatabaseRepository;
        this.getJwtValue = getJwtValue;
    }


    @NonNull
    @Override
    public Completable loadHabitWithProgressesList() {

        String jwt;
        try {
            jwt = getJwtValue.getValue();
        } catch (GetJwtException e) {
            return Completable.error(e);
        }


        return habitsWebRepository.loadHabitWithProgressesList(jwt)
                .onErrorResumeNext(throwable -> Single.error(new ReplicationException(R.string.load_web_exception, throwable)))
                .flatMapCompletable(
                        new Function<List<HabitWithProgresses>, CompletableSource>() {
                            @Override
                            public CompletableSource apply(List<HabitWithProgresses> habitWithProgresses) {
                                return habitsDatabaseRepository.deleteAllHabits()
                                        .andThen(habitsDatabaseRepository.insertHabitWithProgressesList(habitWithProgresses));
                            }
                        });

        /*return habitsWebRepository.loadHabitWithProgressesList(jwt)
                .doOnSuccess(habitWithProgresses -> {
                    habitsDatabaseRepository.deleteAllHabits().subscribe();
                    habitsDatabaseRepository.insertHabitWithProgressesList(habitWithProgresses)
                            .subscribeOn(Schedulers.io())
                            .subscribe();
                })
                .onErrorResumeNext(throwable -> Single.error(new ReplicationException(R.string.load_web_exception, throwable)));*/
    }
}
