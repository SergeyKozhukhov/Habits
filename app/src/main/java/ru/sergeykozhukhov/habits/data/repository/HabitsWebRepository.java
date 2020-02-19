package ru.sergeykozhukhov.habits.data.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import ru.sergeykozhukhov.habits.data.converter.HabitListConverter;
import ru.sergeykozhukhov.habits.data.converter.HabitWithProgressesConverter;
import ru.sergeykozhukhov.habits.data.converter.HabitWithProgressesListConverter;
import ru.sergeykozhukhov.habits.data.converter.ProgressListConverter;
import ru.sergeykozhukhov.habits.data.converter.RegistrationConverter;
import ru.sergeykozhukhov.habits.model.data.JwtData;
import ru.sergeykozhukhov.habits.model.domain.HabitWithProgresses;
import ru.sergeykozhukhov.habits.model.domain.Progress;
import ru.sergeykozhukhov.habits.model.domain.Registration;
import ru.sergeykozhukhov.habits.data.converter.ConfidentialityConverter;
import ru.sergeykozhukhov.habits.data.converter.HabitConverter;
import ru.sergeykozhukhov.habits.data.converter.JwtConverter;
import ru.sergeykozhukhov.habits.data.retrofit.HabitsRetrofitClient;
import ru.sergeykozhukhov.habits.data.retrofit.IHabitsService;
import ru.sergeykozhukhov.habits.domain.IHabitsWebRepository;
import ru.sergeykozhukhov.habits.model.domain.Confidentiality;
import ru.sergeykozhukhov.habits.model.domain.Habit;
import ru.sergeykozhukhov.habits.model.domain.Jwt;

public class HabitsWebRepository implements IHabitsWebRepository {

    public static final String TAG = "HabitsWebRepository";

    private HabitsRetrofitClient habitsRetrofitClient;
    private IHabitsService habitsService;

    private RegistrationConverter registrationConverter;
    private ConfidentialityConverter confidentialityConverter;

    private HabitWithProgressesListConverter habitWithProgressesListConverter;

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
        //habitsService = habitsRetrofitClient.getApiService();
    }

    @NonNull
    @Override
    public Completable registerClient(@NonNull Registration registration) {
        return habitsService.registerClient(registrationConverter.convertFrom(registration));
    }

    @NonNull
    @Override
    public Single<Jwt> authenticateClient(@NonNull Confidentiality confidentiality) {
        return habitsService.authenticateClient(confidentialityConverter.convertFrom(confidentiality))
                .map(new Function<JwtData, Jwt>() {
                    @Override
                    public Jwt apply(JwtData jwtData) throws Exception {
                        Log.d(TAG, "authenticateClient: " + jwtData.getJwt());
                        return jwtConverter.convertTo(jwtData);
                    }
                });
    }

    @NonNull
    @Override
    public Completable insertHabitWithProgressesList(List<HabitWithProgresses> habitWithProgressesList, @NonNull String jwt) {
        return habitsService.insertHabitWithProgressesList(
                habitWithProgressesListConverter.convertFrom(habitWithProgressesList),
                jwt);
    }

    @NonNull
    @Override
    public Single<List<HabitWithProgresses>> loadHabitWithProgressesList(@NonNull String jwt) {
        return habitsService.loadHabitWithProgressesList(jwt)
                .map(habitWithProgressesData -> habitWithProgressesListConverter.convertTo(habitWithProgressesData));
    }

    @Override
    public void setJwt(@NonNull Jwt jwt) {
        habitsRetrofitClient.setJwtData(jwtConverter.convertFrom(jwt));
    }


    @Override
    public Jwt getJwt() {
        return jwtConverter.convertTo(habitsRetrofitClient.getJwtData());
    }

    @Override
    public void deleteJwt() {
        habitsRetrofitClient.clearJwtData();
    }


}
