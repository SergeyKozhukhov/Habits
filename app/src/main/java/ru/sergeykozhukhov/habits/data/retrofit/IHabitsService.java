package ru.sergeykozhukhov.habits.data.retrofit;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import ru.sergeykozhukhov.habits.model.data.ConfidentialityData;
import ru.sergeykozhukhov.habits.model.data.HabitWithProgressesData;
import ru.sergeykozhukhov.habits.model.data.JwtData;
import ru.sergeykozhukhov.habits.model.data.RegistrationData;

/**
 * Интерфейс, определяющий возможные http операции с сервером
 **/
public interface IHabitsService {

    /**
     * Регистрация нового клиента
     *
     * @param registrationData данные регистрации (имя, фамилия, email, пароль) (data слой)
     */
    @POST("/authentication-jwt/api/create_user.php")
    Completable registerClient(@Body RegistrationData registrationData);

    /**
     * Аутентификация пользователя
     *
     * @param confidentialityData данные, подвергающиеся проверке (email, пароль) (data слой)
     * @return single с token (jwt) (data слой)
     */
    @POST("/authentication-jwt/api/login.php")
    Single<JwtData> authenticateClient(@Body ConfidentialityData confidentialityData);

    /**
     * Добавление списка привычек с датами выполнения на сервер
     *
     * @param habitWithProgressesDataList список привычек с датами выполнения (data слой)
     * @param jwt                         строковое представление token (jwt)
     */
    @POST("/authentication-jwt/api/insert_progresses.php")
    Completable insertHabitWithProgressesList(@Body List<HabitWithProgressesData> habitWithProgressesDataList,
                                              @Header("Authorization") String jwt);

    /**
     * Загрузка списка привычек с датами выполнениям с сервера
     *
     * @param jwt строковое представление token (jwt)
     * @return single со списком привычек и датами их выполнения (data слой)
     */
    @GET("/authentication-jwt/api/get_list_habits_with_progress.php")
    Single<List<HabitWithProgressesData>> loadHabitWithProgressesList(@Header("Authorization") String jwt);

}
