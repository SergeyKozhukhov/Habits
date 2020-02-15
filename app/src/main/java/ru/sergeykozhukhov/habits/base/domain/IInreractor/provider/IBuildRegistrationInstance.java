package ru.sergeykozhukhov.habits.base.domain.IInreractor.provider;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ru.sergeykozhukhov.habits.base.model.domain.Registration;
import ru.sergeykozhukhov.habits.base.model.exception.BuildException;

public interface IBuildRegistrationInstance {
    @NonNull
    Registration build(@Nullable String firstaname,
                       @Nullable String lastname,
                       @Nullable String email,
                       @Nullable String password) throws BuildException, BuildException;
}
