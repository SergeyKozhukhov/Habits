package ru.sergeykozhukhov.habits.base.data.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import ru.sergeykozhukhov.habits.base.data.converter.HabitListConverter;
import ru.sergeykozhukhov.habits.base.data.converter.HabitWithProgressesConverter;
import ru.sergeykozhukhov.habits.base.data.converter.HabitWithProgressesListConverter;
import ru.sergeykozhukhov.habits.base.data.converter.ProgressListConverter;
import ru.sergeykozhukhov.habits.base.data.converter.RegistrationConverter;
import ru.sergeykozhukhov.habits.base.model.data.HabitWithProgressesData;
import ru.sergeykozhukhov.habits.base.model.domain.HabitWithProgresses;
import ru.sergeykozhukhov.habits.base.model.domain.Progress;
import ru.sergeykozhukhov.habits.base.model.domain.Registration;
import ru.sergeykozhukhov.habits.notes.backup.AuthenticationConverter;
import ru.sergeykozhukhov.habits.base.data.converter.ConfidentialityConverter;
import ru.sergeykozhukhov.habits.base.data.converter.HabitConverter;
import ru.sergeykozhukhov.habits.base.data.converter.JwtConverter;
import ru.sergeykozhukhov.habits.base.model.data.HabitData;
import ru.sergeykozhukhov.habits.base.data.retrofit.HabitsRetrofitClient;
import ru.sergeykozhukhov.habits.base.data.retrofit.IHabitsService;
import ru.sergeykozhukhov.habits.base.domain.IHabitsWebRepository;
import ru.sergeykozhukhov.habits.base.model.data.JwtData;
import ru.sergeykozhukhov.habits.base.model.domain.Confidentiality;
import ru.sergeykozhukhov.habits.base.model.domain.Habit;
import ru.sergeykozhukhov.habits.base.model.domain.Jwt;

public class HabitsWebRepository implements IHabitsWebRepository {

    public static final String TAG = "HabitsWebRepository";

    private HabitsRetrofitClient habitsRetrofitClient;
    private IHabitsService habitsService;

    private RegistrationConverter registrationConverter;
    private AuthenticationConverter authenticationConverter;
    private ConfidentialityConverter confidentialityConverter;

    private HabitConverter habitConverter;
    private HabitListConverter habitListConverter;
    private ProgressListConverter progressListConverter;
    private HabitWithProgressesConverter habitWithProgressesConverter;
    private HabitWithProgressesListConverter habitWithProgressesListConverter;

    private JwtConverter jwtConverter;

    public HabitsWebRepository(
            @NonNull HabitsRetrofitClient habitsRetrofitClient,
            @NonNull RegistrationConverter registrationConverter,
            @NonNull AuthenticationConverter authenticationConverter,
            @NonNull ConfidentialityConverter confidentialityConverter,
            @NonNull HabitConverter habitConverter,
            @NonNull HabitListConverter habitListConverter,
            @NonNull ProgressListConverter progressListConverter,
            @NonNull HabitWithProgressesConverter habitWithProgressesConverter,
            @NonNull HabitWithProgressesListConverter habitWithProgressesListConverter,
            @NonNull JwtConverter jwtConverter) {
        this.habitsRetrofitClient = habitsRetrofitClient;
        this.registrationConverter = registrationConverter;
        this.authenticationConverter = authenticationConverter;
        this.confidentialityConverter = confidentialityConverter;
        this.habitConverter = habitConverter;
        this.habitListConverter = habitListConverter;
        this.progressListConverter = progressListConverter;
        this.habitWithProgressesConverter = habitWithProgressesConverter;
        this.habitWithProgressesListConverter = habitWithProgressesListConverter;
        this.jwtConverter = jwtConverter;
        habitsService = habitsRetrofitClient.getApiService();
    }

    @Override
    public Completable registrateClient(@NonNull Registration registration) {
        return habitsService.registrateClient(registrationConverter.convertFrom(registration));
    }

    @Override
    public Single<Jwt> authenticateClient(@NonNull Confidentiality confidentiality) {
        return habitsService.authenticateClient(confidentialityConverter.convertFrom(confidentiality))
                .map(jwtData -> {
                    Log.d(TAG, "authenticateClient: " + jwtData.getJwt());
                    return jwtConverter.convertTo(jwtData);
                });
    }

    @Override
    public Single<Habit> insertHabit(Habit habit, @NonNull String jwt) {
        return habitsService.insertHabit(habitConverter.convertFrom(habit), jwt)
                .map(habitData -> {
                    Log.d(TAG, "insertHabit: "+ habitData.getIdHabitServer());
                    return habitConverter.convertTo(habitData);
                });
    }

    @Override
    public Completable insertHabits(List<Habit> habitList, @NonNull String jwt) {
        return habitsService.insertHabitList(habitListConverter.convertFrom(habitList), jwt);
    }

    @Override
    public Completable insertProgressList(List<Progress> progressList, @NonNull String jwt) {
        return habitsService.insertProgressList(progressListConverter.convertFrom(progressList), jwt);
    }

    @Override
    public Completable insertHabitWithProgressesList(List<HabitWithProgresses> habitWithProgressesList, @NonNull String jwt) {
        return habitsService.insertHabitWithProgressesList(
                habitWithProgressesListConverter.convertFrom(habitWithProgressesList),
                jwt);
    }

    @Override
    public Single<List<Habit>> loadHabitList(@NonNull String jwt) {
        return habitsService.loadHabitList(jwt)
                .map(habitDataList -> habitListConverter.convertTo(habitDataList));
    }

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


}
