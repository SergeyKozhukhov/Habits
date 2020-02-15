package ru.sergeykozhukhov.habits.base.domain.usecase.provider;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.base.domain.IInreractor.provider.IBuildRegistrationInstance;
import ru.sergeykozhukhov.habits.base.model.domain.Registration;
import ru.sergeykozhukhov.habits.base.model.exception.BuildException;

public class BuildRegistrationInstance implements IBuildRegistrationInstance {

    private static final int minLength = 2;
    private static final int maxLength = 30;

    @NonNull
    @Override
    public Registration build(@Nullable String firstaname,
                              @Nullable String lastname,
                              @Nullable String email,
                              @Nullable String password) throws BuildException, BuildException {

        if (firstaname == null || lastname == null || email == null || password == null)
            throw new BuildException(R.string.build_instance_exception);
        if (firstaname.length() < minLength || firstaname.length() > maxLength)
            throw new BuildException(R.string.build_instance_exception);
        if (lastname.length() < minLength || lastname.length() > maxLength)
            throw new BuildException(R.string.build_instance_exception);
        if (email.length() < minLength || email.length() > maxLength)
            throw new BuildException(R.string.build_instance_exception);
        if (password.length()<minLength || password.length() > maxLength)
            throw new BuildException(R.string.build_instance_exception);
        return new Registration(firstaname, lastname, email, password);
    }
}
