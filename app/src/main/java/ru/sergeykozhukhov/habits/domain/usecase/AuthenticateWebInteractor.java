package ru.sergeykozhukhov.habits.domain.usecase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.reactivex.Single;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.domain.IHabitsPreferencesRepository;
import ru.sergeykozhukhov.habits.domain.IHabitsWebRepository;
import ru.sergeykozhukhov.habits.domain.IInreractor.IAuthenticateWebInteractor;
import ru.sergeykozhukhov.habits.domain.IInreractor.IBuildConfidentialityInstance;
import ru.sergeykozhukhov.habits.model.domain.Confidentiality;
import ru.sergeykozhukhov.habits.model.domain.Jwt;
import ru.sergeykozhukhov.habits.model.exception.AuthenticateException;
import ru.sergeykozhukhov.habits.model.exception.BuildException;

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
                })
                .onErrorResumeNext(throwable ->
                        Single.error(new AuthenticateException(R.string.authenticate_exception, throwable)));
    }







}
