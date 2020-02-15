package ru.sergeykozhukhov.habits.base.domain.IInreractor.provider;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ru.sergeykozhukhov.habits.base.model.domain.Confidentiality;
import ru.sergeykozhukhov.habits.base.model.exception.BuildException;

public interface IBuildConfidentialityInstance {

    @NonNull
    Confidentiality build(@Nullable String email, @Nullable String password) throws BuildException;

}
