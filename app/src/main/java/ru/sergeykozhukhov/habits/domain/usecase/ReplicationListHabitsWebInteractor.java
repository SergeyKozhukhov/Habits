package ru.sergeykozhukhov.habits.domain.usecase;

import androidx.annotation.NonNull;

import io.reactivex.Completable;
import io.reactivex.Single;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.domain.IHabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.domain.IHabitsWebRepository;
import ru.sergeykozhukhov.habits.domain.iusecase.IReplicationWebInteractor;
import ru.sergeykozhukhov.habits.domain.iusecase.IGetJwtValueInteractor;
import ru.sergeykozhukhov.habits.model.domain.exception.DeleteFromDbException;
import ru.sergeykozhukhov.habits.model.domain.exception.GetJwtException;
import ru.sergeykozhukhov.habits.model.domain.exception.InsertDbException;
import ru.sergeykozhukhov.habits.model.domain.exception.ReplicationException;

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
                        habitWithProgresses -> habitsDatabaseRepository.deleteAllHabits()
                                .onErrorResumeNext(throwable ->
                                        Completable.error(new DeleteFromDbException(R.string.cleanup_db_exception, throwable)))
                                .andThen(habitsDatabaseRepository.insertHabitWithProgressesList(habitWithProgresses)
                                        .onErrorResumeNext(throwable ->
                                                Completable.error(new InsertDbException(R.string.insert_db_exception, throwable)))));

    }
}
