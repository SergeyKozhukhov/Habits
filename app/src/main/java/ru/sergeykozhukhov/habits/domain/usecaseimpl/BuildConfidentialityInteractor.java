package ru.sergeykozhukhov.habits.domain.usecaseimpl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.domain.usecase.IBuildConfidentialityInteractor;
import ru.sergeykozhukhov.habits.model.domain.Confidentiality;
import ru.sergeykozhukhov.habits.model.domain.exception.BuildException;

/**
 * Реализация получения нового экземпляра класса Confidentiality
 */
public class BuildConfidentialityInteractor implements IBuildConfidentialityInteractor {

    /**
     * Минимальная длинна почты
     */
    private static final int EMAIL_MIN = 5;

    /**
     * Минимальная длинна пароля
     */
    private static final int PASSWORD_MIN = 5;

    /**
     * Максимальная длинна пароля
     */
    private static final int PASSWORD_MAX = 30;

    /**
     * Получение нового экземпляпа класса Confidentiality
     *
     * @param email    почта
     * @param password пароль
     * @return экземпляр класса Confidentiality
     * @throws BuildException исключение при ошибке создания экземпляра из-за неверных парметров
     */
    @NonNull
    @Override
    public Confidentiality build(@Nullable String email, @Nullable String password) throws BuildException {
        if (email == null || password == null)
            throw new BuildException(R.string.null_data_build_instance_exception);
        if (email.length() <= EMAIL_MIN)
            throw new BuildException(R.string.email_min_build_instance_exception);
        if (password.length() <= PASSWORD_MIN)
            throw new BuildException(R.string.password_min_build_instance_exception);
        if (password.length() >= PASSWORD_MAX)
            throw new BuildException(R.string.password_max_build_instance_exception);

        return new Confidentiality(email, password);
    }
}
