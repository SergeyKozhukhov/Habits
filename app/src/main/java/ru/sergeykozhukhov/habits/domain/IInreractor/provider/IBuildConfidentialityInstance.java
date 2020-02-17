package ru.sergeykozhukhov.habits.domain.IInreractor.provider;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ru.sergeykozhukhov.habits.model.domain.Confidentiality;
import ru.sergeykozhukhov.habits.model.exception.BuildException;

/**
 * Интерфейс получения нового экземпляра класса confidentiality
 */
public interface IBuildConfidentialityInstance {

    @NonNull
    Confidentiality build(@Nullable String email, @Nullable String password) throws BuildException;

}
