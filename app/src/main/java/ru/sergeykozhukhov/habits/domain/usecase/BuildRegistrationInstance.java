package ru.sergeykozhukhov.habits.domain.usecase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.domain.IInreractor.IBuildRegistrationInstance;
import ru.sergeykozhukhov.habits.model.domain.Registration;
import ru.sergeykozhukhov.habits.model.exception.BuildException;

public class BuildRegistrationInstance implements IBuildRegistrationInstance {

    private static final int nameMinLength = 2;
    private static final int nameMaxLength = 15;

    private static final int emailMinLength = 5;

    private static final int passwordMinLength = 5;
    private static final int passwordMaxLength = 30;

    @NonNull
    @Override
    public Registration build(@Nullable String firstaname,
                              @Nullable String lastname,
                              @Nullable String email,
                              @Nullable String password,
                              @Nullable String passwordConfirmation) throws BuildException {

        if (firstaname == null || lastname == null || email == null || password == null || passwordConfirmation == null)
            throw new BuildException(R.string.null_data_build_instance_exception);
        if (firstaname.length() <= nameMinLength)
            throw new BuildException(R.string.firstname_min_build_instance_exception);
        if (firstaname.length() >= nameMaxLength)
            throw new BuildException(R.string.firstname_max_build_instance_exception);
        if (lastname.length() <= nameMinLength)
            throw new BuildException(R.string.lastname_min_build_instance_exception);
        if (lastname.length() >= nameMaxLength)
            throw new BuildException(R.string.lastname_max_build_instance_exception);
        if (email.length() <= emailMinLength)
            throw new BuildException(R.string.email_min_build_instance_exception);
        if (password.length() <= passwordMinLength)
            throw new BuildException(R.string.password_min_build_instance_exception);
        if (password.length() >= passwordMaxLength)
            throw new BuildException(R.string.password_max_build_instance_exception);
        if (!password.equals(passwordConfirmation))
            throw new BuildException(R.string.password_confirmation_build_instance_exception);

        return new Registration(firstaname, lastname, email, password);
    }
}
