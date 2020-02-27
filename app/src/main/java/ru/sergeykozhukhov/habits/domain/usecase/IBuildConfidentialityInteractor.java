package ru.sergeykozhukhov.habits.domain.usecase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ru.sergeykozhukhov.habits.model.domain.Confidentiality;
import ru.sergeykozhukhov.habits.model.domain.exception.BuildException;

/**
 * Интерфейс интерактора получения нового экземпляра класса Confidentiality
 */
public interface IBuildConfidentialityInteractor {

    /**
     * Получение нового экхемпляпа коасса Confidentiality
     *
     * @param email    почта
     * @param password пароль
     * @return экземпляр класса Confidentiality
     * @throws BuildException исключение при ошибке создания экземпляра из-за неверных парметров
     */
    @NonNull
    Confidentiality build(@Nullable String email, @Nullable String password) throws BuildException;

}
