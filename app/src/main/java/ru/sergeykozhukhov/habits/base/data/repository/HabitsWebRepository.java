package ru.sergeykozhukhov.habits.base.data.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;

import io.reactivex.Single;
import io.reactivex.functions.Function;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.sergeykozhukhov.habits.base.data.converter.AuthenticationConverter;
import ru.sergeykozhukhov.habits.base.data.converter.ConfidentialityConverter;
import ru.sergeykozhukhov.habits.base.data.model.AuthenticationData;
import ru.sergeykozhukhov.habits.base.data.retrofit.HabitsRetrofitClient;
import ru.sergeykozhukhov.habits.base.data.retrofit.IHabitsService;
import ru.sergeykozhukhov.habits.base.domain.IHabitsWebRepository;
import ru.sergeykozhukhov.habits.base.domain.model.Authentication;
import ru.sergeykozhukhov.habits.base.domain.model.Confidentiality;
import ru.sergeykozhukhov.habits.base.domain.model.Habit;

public class HabitsWebRepository implements IHabitsWebRepository {

    private HabitsRetrofitClient habitsRetrofitClient;
    private IHabitsService habitsService;

    private AuthenticationConverter authenticationConverter;
    private ConfidentialityConverter confidentialityConverter;

    public HabitsWebRepository(
            @NonNull HabitsRetrofitClient habitsRetrofitClient,
            @NonNull AuthenticationConverter authenticationConverter,
            @NonNull ConfidentialityConverter confidentialityConverter) {
        this.habitsRetrofitClient = habitsRetrofitClient;
        this.authenticationConverter = authenticationConverter;
        this.confidentialityConverter = confidentialityConverter;
        habitsService = HabitsRetrofitClient.getApiService();
    }

    @Override
    public Authentication authClient(Confidentiality confidentiality) {


        Response<AuthenticationData> response = null;
        try {
            response = habitsService.authClient(confidentialityConverter.convertFrom(confidentiality))
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response == null)
            return null;
        else{

            if (response.body() == null || response.errorBody() != null){
                return null;
            }
        }

        return authenticationConverter.convertTo(response.body());


    }

    @Override
    public Single<Authentication> authClientRx(Confidentiality confidentiality) {
        Log.d("web_repositor", "requset");
        return habitsService.authClientRx(confidentialityConverter.convertFrom(confidentiality))
                .map(new Function<AuthenticationData, Authentication>() {
                    @Override
                    public Authentication apply(AuthenticationData authenticationData) throws Exception {
                        return authenticationConverter.convertTo(authenticationData);
                    }
                });
    }
}
