package ru.sergeykozhukhov.habits.domain.usecaseimpl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.domain.usecase.IBuildRegistrationInteractor;
import ru.sergeykozhukhov.habits.model.domain.Registration;
import ru.sergeykozhukhov.habits.model.domain.exception.BuildException;

/**
 * Реализация интерактора получения нового экземпляра класса Registration
 */
public class BuildRegistrationInteractor implements IBuildRegistrationInteractor {

    /**
     * Минимальная длинна имени и фамилии
     */
    private static final int NAME_MIN = 2;

    /**
     * Максимальная длинна имени и фамилии
     */
    private static final int NAME_MAX = 15;

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
     * Получение новго экземпляра класса Registration
     *
     * @param firstaname           имя
     * @param lastname             фамилия
     * @param email                почта
     * @param password             пароль
     * @param passwordConfirmation повторный пароль для подтверждения
     * @return экземпляр класса Registration
     * @throws BuildException исключение при ошибке создания экземпляра из-за неверных парметров
     */
    @NonNull
    @Override
    public Registration build(@Nullable String firstaname,
                              @Nullable String lastname,
                              @Nullable String email,
                              @Nullable String password,
                              @Nullable String passwordConfirmation) throws BuildException {

        if (firstaname == null || lastname == null || email == null || password == null || passwordConfirmation == null)
            throw new BuildException(R.string.null_data_build_instance_exception);
        if (firstaname.length() <= NAME_MIN)
            throw new BuildException(R.string.firstname_min_build_instance_exception);
        if (firstaname.length() >= NAME_MAX)
            throw new BuildException(R.string.firstname_max_build_instance_exception);
        if (lastname.length() <= NAME_MIN)
            throw new BuildException(R.string.lastname_min_build_instance_exception);
        if (lastname.length() >= NAME_MAX)
            throw new BuildException(R.string.lastname_max_build_instance_exception);
        if (email.length() <= EMAIL_MIN)
            throw new BuildException(R.string.email_min_build_instance_exception);
        if (password.length() <= PASSWORD_MIN)
            throw new BuildException(R.string.password_min_build_instance_exception);
        if (password.length() >= PASSWORD_MAX)
            throw new BuildException(R.string.password_max_build_instance_exception);
        if (!password.equals(passwordConfirmation))
            throw new BuildException(R.string.password_confirmation_build_instance_exception);

        return new Registration(firstaname, lastname, email, password);
    }
}
