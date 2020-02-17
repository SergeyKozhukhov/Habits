package ru.sergeykozhukhov.habits.domain.usecase.provider;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.domain.IInreractor.provider.IBuildConfidentialityInstance;
import ru.sergeykozhukhov.habits.model.domain.Confidentiality;
import ru.sergeykozhukhov.habits.model.exception.BuildException;

public class BuildConfidentialityInstance implements IBuildConfidentialityInstance {

    private static final int minLength = 2;
    private static final int maxLength = 30;


    @NonNull
    @Override
    public Confidentiality build(@Nullable String email, @Nullable String password) throws BuildException {
        if (email == null || password == null)
            throw new BuildException(R.string.build_instance_exception);
        if (email.length() < minLength || email.length() > maxLength)
            throw new BuildException(R.string.build_instance_exception);
        if (password.length() < minLength || password.length() > maxLength)
            throw new BuildException(R.string.build_instance_exception);
        return new Confidentiality(email, password);
    }
}
