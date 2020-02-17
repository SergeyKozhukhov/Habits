package ru.sergeykozhukhov.habits.domain.usecase;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.domain.IHabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.domain.IHabitsWebRepository;
import ru.sergeykozhukhov.habits.domain.IInreractor.IReplicationWebInteractor;
import ru.sergeykozhukhov.habits.domain.IInreractor.provider.IGetJwtValueInteractor;
import ru.sergeykozhukhov.habits.model.domain.HabitWithProgresses;
import ru.sergeykozhukhov.habits.model.exception.GetJwtException;
import ru.sergeykozhukhov.habits.model.exception.LoadWebException;

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
    public Single<List<HabitWithProgresses>> loadHabitWithProgressesList() {

        String jwt;
        try {
            jwt = getJwtValue.getValue();
        } catch (GetJwtException e) {
            return Single.error(e);
        }

        return habitsWebRepository.loadHabitWithProgressesList(jwt)
                .doOnSuccess(habitWithProgresses -> {
                    habitsDatabaseRepository.deleteAllHabits().subscribe();
                    habitsDatabaseRepository.insertHabitWithProgressesList(habitWithProgresses)
                            .subscribeOn(Schedulers.io())
                            .subscribe(new CompletableObserver() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onComplete() {
                                    Log.d("FF", "111" );
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Log.d("FF", "222" );
                                }
                            });
                })
                .onErrorResumeNext(throwable -> Single.error(new LoadWebException(R.string.load_web_exception, throwable)));
    }
}
