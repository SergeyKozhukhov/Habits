package ru.sergeykozhukhov.habits.domain.usecase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.domain.IInreractor.IBuildRegistrationInstance;
import ru.sergeykozhukhov.habits.model.domain.Registration;
import ru.sergeykozhukhov.habits.model.exception.BuildException;

public class BuildRegistrationInstance implements IBuildRegistrationInstance {

    private static final int nameMin = 2;
    private static final int nameMax = 15;

    private static final int emailMin = 5;

    private static final int passwordMin = 5;
    private static final int passwordMax = 30;

    @NonNull
    @Override
    public Registration build(@Nullable String firstaname,
                              @Nullable String lastname,
                              @Nullable String email,
                              @Nullable String password,
                              @Nullable String passwordConfirmation) throws BuildException {

        if (firstaname == null || lastname == null || email == null || password == null || passwordConfirmation == null)
            throw new BuildException(R.string.null_data_build_instance_exception);
        if (firstaname.length() <= nameMin)
            throw new BuildException(R.string.firstname_min_build_instance_exception);
        if (firstaname.length() >= nameMax)
            throw new BuildException(R.string.firstname_max_build_instance_exception);
        if (lastname.length() <= nameMin)
            throw new BuildException(R.string.lastname_min_build_instance_exception);
        if (lastname.length() >= nameMax)
            throw new BuildException(R.string.lastname_max_build_instance_exception);
        if (email.length() <= emailMin)
            throw new BuildException(R.string.email_min_build_instance_exception);
        if (password.length() <= passwordMin)
            throw new BuildException(R.string.password_min_build_instance_exception);
        if (password.length() >= passwordMax)
            throw new BuildException(R.string.password_max_build_instance_exception);
        if (!password.equals(passwordConfirmation))
            throw new BuildException(R.string.password_confirmation_build_instance_exception);

        return new Registration(firstaname, lastname, email, password);
    }
}
