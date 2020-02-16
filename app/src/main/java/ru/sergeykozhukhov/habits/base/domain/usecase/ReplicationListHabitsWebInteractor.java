package ru.sergeykozhukhov.habits.base.domain.usecase;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.base.domain.IHabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.base.domain.IHabitsWebRepository;
import ru.sergeykozhukhov.habits.base.domain.IInreractor.IReplicationWebInteractor;
import ru.sergeykozhukhov.habits.base.domain.IInreractor.provider.IGetJwtValue;
import ru.sergeykozhukhov.habits.base.model.domain.HabitWithProgresses;
import ru.sergeykozhukhov.habits.base.model.exception.GetJwtException;
import ru.sergeykozhukhov.habits.base.model.exception.LoadWebException;

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
