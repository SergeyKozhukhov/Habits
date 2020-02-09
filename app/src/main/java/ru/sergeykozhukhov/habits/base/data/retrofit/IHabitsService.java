package ru.sergeykozhukhov.habits.base.data.retrofit;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import ru.sergeykozhukhov.habits.base.model.data.ConfidentialityData;
import ru.sergeykozhukhov.habits.base.model.data.HabitData;
import ru.sergeykozhukhov.habits.base.model.data.JwtData;

public interface IHabitsService {

    @POST("/authentication-jwt/api/login.php")
    Single<JwtData> authClientRx(@Body ConfidentialityData confidentialityData);

    @POST("/authentication-jwt/api/insert_habit.php")
    Single<HabitData> insertHabits(@Body HabitData habitData, @Header("Authorization")  String jwt);

    @POST("/authentication-jwt/api/insert_habits.php")
    Completable insertHabits(@Body List<HabitData> habitDataList, @Header("Authorization")  String jwt);

    @GET("/authentication-jwt/api/get_list_habits.php")
    Single<List<HabitData>> loadListHabits(@Header("Authorization")  String jwt);


}
