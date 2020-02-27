package ru.sergeykozhukhov.habits.domain.usecaseimpl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.reactivex.Completable;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.domain.IHabitsPreferencesRepository;
import ru.sergeykozhukhov.habits.domain.IHabitsWebRepository;
import ru.sergeykozhukhov.habits.domain.usecase.IAuthenticateWebInteractor;
import ru.sergeykozhukhov.habits.domain.usecase.IBuildConfidentialityInteractor;
import ru.sergeykozhukhov.habits.model.domain.Confidentiality;
import ru.sergeykozhukhov.habits.model.domain.exception.AuthenticateException;
import ru.sergeykozhukhov.habits.model.domain.exception.BuildException;

/**
 * Реализация интерктора входа пользователя в свой аккаунт
 */
public class AuthenticateWebInteractor implements IAuthenticateWebInteractor {

    /**
     * Репозиторий (сервер)
     */
    private final IHabitsWebRepository habitsWebRepository;

    /**
     * Репозиторий (preferences)
     */
    private final IHabitsPreferencesRepository habitsPreferencesRepository;

    /**
     * Интерактор получения нового экземпляра класса Confidentiality
     */
    private final IBuildConfidentialityInteractor buildConfidentialityInstance;

    public AuthenticateWebInteractor(@NonNull IHabitsWebRepository habitsWebRepository,
                                     @NonNull IHabitsPreferencesRepository habitsPreferencesRepository,
                                     @NonNull IBuildConfidentialityInteractor buildConfidentialityInstance) {
        this.habitsWebRepository = habitsWebRepository;
        this.habitsPreferencesRepository = habitsPreferencesRepository;
        this.buildConfidentialityInstance = buildConfidentialityInstance;
    }

    /**
     * Вход пользователя в свой аккаунт
     *
     * @param email    почта
     * @param password пароль
     */
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
