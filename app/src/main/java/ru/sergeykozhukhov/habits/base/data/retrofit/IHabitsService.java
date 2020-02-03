package ru.sergeykozhukhov.habits.base.data.retrofit;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import ru.sergeykozhukhov.habits.base.data.model.AuthenticationData;
import ru.sergeykozhukhov.habits.base.data.model.ConfidentialityData;

public interface IHabitsService {

    @POST("/authentication-jwt/api/login.php")
    Call<AuthenticationData> authClient(@Body ConfidentialityData confidentialityData);

    @POST("/authentication-jwt/api/login.php")
    Single<AuthenticationData> authClientRx(@Body ConfidentialityData confidentialityData);


}
