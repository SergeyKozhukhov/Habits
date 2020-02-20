package ru.sergeykozhukhov.habits.data.retrofit;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import ru.sergeykozhukhov.habits.model.data.ConfidentialityData;
import ru.sergeykozhukhov.habits.model.data.HabitData;
import ru.sergeykozhukhov.habits.model.data.HabitWithProgressesData;
import ru.sergeykozhukhov.habits.model.data.JwtData;
import ru.sergeykozhukhov.habits.model.data.ProgressData;
import ru.sergeykozhukhov.habits.model.data.RegistrationData;

public interface IHabitsService {

    @POST("/authentication-jwt/api/create_user.php")
    Completable registerClient(@Body RegistrationData registrationData);

    @POST("/authentication-jwt/api/login.php")
    Single<JwtData> authenticateClient(@Body ConfidentialityData confidentialityData);

    @POST("/authentication-jwt/api/insert_progresses.php")
    Completable insertHabitWithProgressesList(@Body List<HabitWithProgressesData> habitWithProgressesDataList,
                                              @Header("Authorization")  String jwt);

    @GET("/authentication-jwt/api/get_list_habits_with_progress.php")
    Single<List<HabitWithProgressesData>> loadHabitWithProgressesList(@Header("Authorization")  String jwt);

}
