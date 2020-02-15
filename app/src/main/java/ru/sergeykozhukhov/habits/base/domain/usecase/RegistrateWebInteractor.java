package ru.sergeykozhukhov.habits.base.domain.usecase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.reactivex.Completable;
import ru.sergeykozhukhov.habits.base.domain.IHabitsWebRepository;
import ru.sergeykozhukhov.habits.base.domain.IInreractor.IRegistrateWebInteractor;
import ru.sergeykozhukhov.habits.base.domain.IInreractor.provider.IBuildRegistrationInstance;
import ru.sergeykozhukhov.habits.base.model.domain.Registration;
import ru.sergeykozhukhov.habits.base.model.exception.BuildException;

public class RegistrateWebInteractor implements IRegistrateWebInteractor {

    public static final String TAG = "registrateClientInter";
    private final IHabitsWebRepository habitsWebRepository;
    private final IBuildRegistrationInstance buildRegistrationInstance;

    public RegistrateWebInteractor(@NonNull IHabitsWebRepository habitsWebRepository,
                                   @NonNull IBuildRegistrationInstance buildRegistrationInstance) {
        this.habitsWebRepository = habitsWebRepository;
        this.buildRegistrationInstance = buildRegistrationInstance;
    }


    @Override
    public Completable registrateClient(@Nullable String firstname,
                                        @Nullable String lastname,
                                        @Nullable String email,
                                        @Nullable String password) {
        Registration registration;
        try {
            registration = buildRegistrationInstance.build(firstname, lastname, email, password);
        } catch (BuildException e) {
            return Completable.error(e);
        }
        return habitsWebRepository.registrateClient(registration);
    }
}
