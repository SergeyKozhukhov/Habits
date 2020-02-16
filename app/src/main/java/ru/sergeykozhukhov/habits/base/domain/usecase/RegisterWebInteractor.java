package ru.sergeykozhukhov.habits.base.domain.usecase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.functions.Function;
import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.base.domain.IHabitsWebRepository;
import ru.sergeykozhukhov.habits.base.domain.IInreractor.IRegisterWebInteractor;
import ru.sergeykozhukhov.habits.base.domain.IInreractor.provider.IBuildRegistrationInstance;
import ru.sergeykozhukhov.habits.base.model.domain.Registration;
import ru.sergeykozhukhov.habits.base.model.exception.BuildException;
import ru.sergeykozhukhov.habits.base.model.exception.RegisterException;

public class RegisterWebInteractor implements IRegisterWebInteractor {

    public static final String TAG = "registrateClientInter";
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
                                      @Nullable String password) {
        Registration registration;
        try {
            registration = buildRegistrationInstance.build(firstname, lastname, email, password);
        } catch (BuildException e) {
            return Completable.error(e);
        }
        return habitsWebRepository.registerClient(registration)
                .onErrorResumeNext(throwable ->
                        Completable.error(new RegisterException(R.string.register_exception, throwable)));
    }
}
