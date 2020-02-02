package ru.sergeykozhukhov.habits.notes.retrofit.helper;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import ru.sergeykozhukhov.habits.notes.retrofit.data.authentication.AuthUser;
import ru.sergeykozhukhov.habits.notes.retrofit.data.authentication.AuthResult;
import ru.sergeykozhukhov.habits.notes.retrofit.data.authentication.JwtData;
import ru.sergeykozhukhov.habits.notes.retrofit.data.habits.HabitsList;

public interface ApiService {

    @POST("/authentication-jwt/api/login.php")
    Call<AuthResult> authClient(@Body AuthUser authUser);

    @POST("/authentication-jwt/api/get_habits.php")
    Call<HabitsList> getHabits(@Body JwtData jwt);
        //Call<HabitsList> getHabitList(@Header("Authorization")  String jwt);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("/authentication-jwt/api/get_habits.php")
    Single<HabitsList> getHabitsRx(@Header("Authorization")  JwtData jwt);
    // Single<HabitsList> getHabitsRx(@Body JwtData jwt);
}
