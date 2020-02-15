package ru.sergeykozhukhov.habits.base.domain.usecase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.reactivex.Single;
import io.reactivex.functions.Consumer;
import ru.sergeykozhukhov.habits.base.domain.IHabitsPreferencesRepository;
import ru.sergeykozhukhov.habits.base.domain.IHabitsWebRepository;
import ru.sergeykozhukhov.habits.base.domain.IInreractor.IAuthenticateWebInteractor;
import ru.sergeykozhukhov.habits.base.domain.IInreractor.provider.IBuildConfidentialityInstance;
import ru.sergeykozhukhov.habits.base.model.domain.Confidentiality;
import ru.sergeykozhukhov.habits.base.model.domain.Jwt;
import ru.sergeykozhukhov.habits.base.model.exception.BuildException;

public class AuthenticateWebInteractor implements IAuthenticateWebInteractor {

    public static final String TAG = "authenticateClientInter";
    private final IHabitsWebRepository habitsWebRepository;
    private final IHabitsPreferencesRepository habitsPreferencesRepository;
    private final IBuildConfidentialityInstance buildConfidentialityInstance;

    public AuthenticateWebInteractor(@NonNull IHabitsWebRepository habitsWebRepository,
                                     @NonNull IHabitsPreferencesRepository habitsPreferencesRepository,
                                     @NonNull IBuildConfidentialityInstance buildConfidentialityInstance) {
        this.habitsWebRepository = habitsWebRepository;
        this.habitsPreferencesRepository = habitsPreferencesRepository;
        this.buildConfidentialityInstance = buildConfidentialityInstance;
    }


    @NonNull
    @Override
    public Single<Jwt> authenticateClient(@Nullable String email, @Nullable String password) {
        Confidentiality confidentiality;
        try {
            confidentiality = buildConfidentialityInstance.build(email, password);
        } catch (BuildException e) {
            return Single.error(e);
        }
        return habitsWebRepository.authenticateClient(confidentiality)
                .doOnSuccess(jwt -> {
                    habitsWebRepository.setJwt(jwt);
                    habitsPreferencesRepository.saveJwt(jwt);
                    Log.d(TAG, "authenticateClient: success");
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d(TAG, "authenticateClient: error");
                    }
                });
    }
}
