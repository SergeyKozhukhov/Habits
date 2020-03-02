package ru.sergeykozhukhov.habits.data.repository;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import ru.sergeykozhukhov.habits.data.converter.ConfidentialityConverter;
import ru.sergeykozhukhov.habits.data.converter.HabitWithProgressesListConverter;
import ru.sergeykozhukhov.habits.data.converter.JwtConverter;
import ru.sergeykozhukhov.habits.data.converter.RegistrationConverter;
import ru.sergeykozhukhov.habits.data.retrofit.HabitsRetrofitClient;
import ru.sergeykozhukhov.habits.data.retrofit.IHabitsService;
import ru.sergeykozhukhov.habits.domain.IHabitsWebRepository;
import ru.sergeykozhukhov.habits.model.data.JwtData;
import ru.sergeykozhukhov.habits.model.domain.Confidentiality;
import ru.sergeykozhukhov.habits.model.domain.HabitWithProgresses;
import ru.sergeykozhukhov.habits.model.domain.Jwt;
import ru.sergeykozhukhov.habits.model.domain.Registration;

/**
 * Реализация репозитория (сервер)
 */
public class HabitsWebRepository implements IHabitsWebRepository {

    /**
     * Класс, подготавливающий и настраивающий работу с сервером
     */
    private HabitsRetrofitClient habitsRetrofitClient;

    /**
     * Интерфейс, определяющий возможные http операции с сервером
     */
    private IHabitsService habitsService;

    /**
     * Конвертер Registration модели между data и domain слоями
     */
    private RegistrationConverter registrationConverter;

    /**
     * Конвертер Confidentiality модели между data и domain слоями
     */
    private ConfidentialityConverter confidentialityConverter;

    /**
     * Конвертер списка HabitWithProgresses моделей между data и domain слоями
     */
    private HabitWithProgressesListConverter habitWithProgressesListConverter;

    /**
     * Конвертер Jwt модели между data и domain слоями
     */
    private JwtConverter jwtConverter;

    public HabitsWebRepository(
            @NonNull HabitsRetrofitClient habitsRetrofitClient,
            @NonNull IHabitsService habitsService,
            @NonNull RegistrationConverter registrationConverter,
            @NonNull ConfidentialityConverter confidentialityConverter,
            @NonNull HabitWithProgressesListConverter habitWithProgressesListConverter,
            @NonNull JwtConverter jwtConverter) {
        this.habitsRetrofitClient = habitsRetrofitClient;
        this.habitsService = habitsService;
        this.registrationConverter = registrationConverter;
        this.confidentialityConverter = confidentialityConverter;
        this.habitWithProgressesListConverter = habitWithProgressesListConverter;
        this.jwtConverter = jwtConverter;
    }

    /**
     * Регистрация нового клиента
     *
     * @param registration данные регистрации (имя, фамилия, email, пароль) (domain слой)
     */
    @NonNull
    @Override
    public Completable registerClient(@NonNull Registration registration) {
        return habitsService.registerClient(registrationConverter.convertFrom(registration));
    }

    /**
     * Аутентификация пользователя
     *
     * @param confidentiality данные, подвергающиеся проверке (email, пароль) (domain слой)
     * @return single с token (jwt) (domain слой)
     */
    @NonNull
    @Override
    public Single<Jwt> authenticateClient(@NonNull Confidentiality confidentiality) {
        return habitsService.authenticateClient(confidentialityConverter.convertFrom(confidentiality))
                .map(jwtData -> jwtConverter.convertTo(jwtData));
    }

    /**
     * Добавление списка привычек с датами выполнения на сервер
     *
     * @param habitWithProgressesList список привычек с датами выполнения (domain слой)
     * @param jwt                     строковое представление token (jwt)
     */
    @NonNull
    @Override
    public Completable insertHabitWithProgressesList(List<HabitWithProgresses> habitWithProgressesList, @NonNull String jwt) {
        return habitsService.insertHabitWithProgressesList(habitWithProgressesListConverter.convertFrom(habitWithProgressesList), jwt);
    }

    /**
     * Загрузка списка привычек с датами выполнениям с сервера
     *
     * @param jwt строковое представление token (jwt)
     * @return single со списком привычек и датами их выполнения (domain слой)
     */
    @NonNull
    @Override
    public Single<List<HabitWithProgresses>> loadHabitWithProgressesList(@NonNull String jwt) {
        return habitsService.loadHabitWithProgressesList(jwt)
                .map(habitWithProgressesData -> habitWithProgressesListConverter.convertTo(habitWithProgressesData));
    }

    /**
     * Сохранение token (jwt) в памяти
     *
     * @param jwt token (jwt) (domain слой)
     */
    @Override
    public void setJwt(@NonNull Jwt jwt) {
        habitsRetrofitClient.setJwtData(jwtConverter.convertFrom(jwt));
    }

    /**
     * Получение сохранненого в памяти token (jwt)
     *
     * @return token (jwt) (domain слой)
     * @throws NullPointerException token (jwt) является null
     */
    @Override
    @NonNull
    public Jwt getJwt() throws NullPointerException {
        JwtData jwtData = habitsRetrofitClient.getJwtData();
        if (jwtData == null)
            throw new NullPointerException();
        return jwtConverter.convertTo(jwtData);
    }

    /**
     * Обнуление token (jwt) в памяти (= null)
     */
    @Override
    public void deleteJwt() {
        habitsRetrofitClient.clearJwtData();
    }


}
