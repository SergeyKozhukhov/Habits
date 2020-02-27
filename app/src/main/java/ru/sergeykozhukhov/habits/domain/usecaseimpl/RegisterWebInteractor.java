package ru.sergeykozhukhov.habits.domain.usecaseimpl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.reactivex.Completable;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.domain.IHabitsWebRepository;
import ru.sergeykozhukhov.habits.domain.usecase.IRegisterWebInteractor;
import ru.sergeykozhukhov.habits.domain.usecase.IBuildRegistrationInteractor;
import ru.sergeykozhukhov.habits.model.domain.Registration;
import ru.sergeykozhukhov.habits.model.domain.exception.BuildException;
import ru.sergeykozhukhov.habits.model.domain.exception.RegisterException;

/**
 * Реализация интерактора регистрации нового пользователя
 */
public class RegisterWebInteractor implements IRegisterWebInteractor {

    /**
     * Репозиторий (сервер)
     */
    private final IHabitsWebRepository habitsWebRepository;

    /**
     * Интерактор получения нового экземпляра класса Registration
     */
    private final IBuildRegistrationInteractor buildRegistrationInstance;

    public RegisterWebInteractor(@NonNull IHabitsWebRepository habitsWebRepository,
                                 @NonNull IBuildRegistrationInteractor buildRegistrationInstance) {
        this.habitsWebRepository = habitsWebRepository;
        this.buildRegistrationInstance = buildRegistrationInstance;
    }

    /**
     * Регистрация нового пользователя
     *
     * @param firstname            имя
     * @param lastname             фамилия
     * @param email                почта
     * @param password             пароль
     * @param passwordConfirmation повторный пароль для подтверждения
     */
    @NonNull
    @Override
    public Completable registerClient(@Nullable String firstname,
                                      @Nullable String lastname,
                                      @Nullable String email,
                                      @Nullable String password,
                                      @Nullable String passwordConfirmation) {
        Registration registration;
        try {
            registration = buildRegistrationInstance.build(firstname, lastname, email, password, passwordConfirmation);
        } catch (BuildException e) {
            return Completable.error(e);
        }
        return habitsWebRepository.registerClient(registration)
                .onErrorResumeNext(throwable ->
                        Completable.error(new RegisterException(R.string.register_exception, throwable)));
    }
}
