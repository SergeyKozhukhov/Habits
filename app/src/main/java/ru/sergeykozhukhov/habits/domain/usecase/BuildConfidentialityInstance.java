package ru.sergeykozhukhov.habits.domain.usecase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.domain.IInreractor.IBuildConfidentialityInstance;
import ru.sergeykozhukhov.habits.model.domain.Confidentiality;
import ru.sergeykozhukhov.habits.model.exception.BuildException;

public class BuildConfidentialityInstance implements IBuildConfidentialityInstance {

    private static final int minLength = 5;
    private static final int maxLength = 30;

    @NonNull
    @Override
    public Confidentiality build(@Nullable String email, @Nullable String password) throws BuildException {
        if (email == null || password == null)
            throw new BuildException(R.string.null_data_build_instance_exception);
        if (email.length() <= minLength)
            throw new BuildException(R.string.email_min_build_instance_exception);
        if (password.length() <= minLength)
            throw new BuildException(R.string.password_min_build_instance_exception);
        if (password.length() >= maxLength)
            throw new BuildException(R.string.password_max_build_instance_exception);
        return new Confidentiality(email, password);
    }
}
