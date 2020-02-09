package ru.sergeykozhukhov.habits.base.domain.usecase;

import android.util.Log;

import androidx.annotation.NonNull;

import io.reactivex.Single;
import io.reactivex.functions.Consumer;
import ru.sergeykozhukhov.habits.base.domain.IHabitsPreferencesRepository;
import ru.sergeykozhukhov.habits.base.domain.IHabitsWebRepository;
import ru.sergeykozhukhov.habits.base.domain.IInreractor.IAuthenticateWebInteractor;
import ru.sergeykozhukhov.habits.base.model.domain.Confidentiality;
import ru.sergeykozhukhov.habits.base.model.domain.Jwt;

public class AuthenticateWebInteractor implements IAuthenticateWebInteractor {

    public static final String TAG = "authenticateClientInter";
    private final IHabitsWebRepository habitsWebRepository;
    private final IHabitsPreferencesRepository habitsPreferencesRepository;

    public AuthenticateWebInteractor(@NonNull IHabitsWebRepository habitsWebRepository,
                                     @NonNull IHabitsPreferencesRepository habitsPreferencesRepository) {
        this.habitsWebRepository = habitsWebRepository;
        this.habitsPreferencesRepository = habitsPreferencesRepository;
    }

    @Override
    public Single<Jwt> authenticateClientRx(Confidentiality confidentiality) {
        return habitsWebRepository.authClientRx(confidentiality)
                .doOnSuccess(new Consumer<Jwt>() {
                    @Override
                    public void accept(Jwt jwt) throws Exception {
                        habitsWebRepository.setJwt(jwt);
                        habitsPreferencesRepository.saveJwt(jwt);
                        Log.d(TAG, "authenticateClientRx: success");
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d(TAG, "authenticateClientRx: error");
                    }
                });
    }
}
