package ru.sergeykozhukhov.habits.domain.usecase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.reactivex.Completable;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.domain.IHabitsPreferencesRepository;
import ru.sergeykozhukhov.habits.domain.IHabitsWebRepository;
import ru.sergeykozhukhov.habits.domain.iusecase.IAuthenticateWebInteractor;
import ru.sergeykozhukhov.habits.domain.iusecase.IBuildConfidentialityInstance;
import ru.sergeykozhukhov.habits.model.domain.Confidentiality;
import ru.sergeykozhukhov.habits.model.domain.exception.AuthenticateException;
import ru.sergeykozhukhov.habits.model.domain.exception.BuildException;

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
    public Completable authenticateClient(@Nullable String email, @Nullable String password) {

        Confidentiality confidentiality;
        try {
            confidentiality = buildConfidentialityInstance.build(email, password);
        } catch (BuildException e) {
            return Completable.error(e);
        }
        return habitsWebRepository.authenticateClient(confidentiality)
                .doOnSuccess(jwt -> {
                    habitsWebRepository.setJwt(jwt);
                    habitsPreferencesRepository.saveJwt(jwt);
                })
                .ignoreElement()
                .onErrorResumeNext(throwable ->
                        Completable.error(new AuthenticateException(R.string.authenticate_exception, throwable)));
    }


}
