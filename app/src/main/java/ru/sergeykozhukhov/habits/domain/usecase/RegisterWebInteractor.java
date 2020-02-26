package ru.sergeykozhukhov.habits.domain.usecase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.reactivex.Completable;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.domain.IHabitsWebRepository;
import ru.sergeykozhukhov.habits.domain.iusecase.IRegisterWebInteractor;
import ru.sergeykozhukhov.habits.domain.iusecase.IBuildRegistrationInstance;
import ru.sergeykozhukhov.habits.model.domain.Registration;
import ru.sergeykozhukhov.habits.model.domain.exception.BuildException;
import ru.sergeykozhukhov.habits.model.domain.exception.RegisterException;

public class RegisterWebInteractor implements IRegisterWebInteractor {

    public static final String TAG = "registerClientInter";
    private final IHabitsWebRepository habitsWebRepository;
    private final IBuildRegistrationInstance buildRegistrationInstance;

    public RegisterWebInteractor(@NonNull IHabitsWebRepository habitsWebRepository,
                                 @NonNull IBuildRegistrationInstance buildRegistrationInstance) {
        this.habitsWebRepository = habitsWebRepository;
        this.buildRegistrationInstance = buildRegistrationInstance;
    }

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
