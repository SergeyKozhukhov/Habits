package ru.sergeykozhukhov.habits.base.data.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import ru.sergeykozhukhov.habits.base.data.converter.HabitWithProgressesConverter;
import ru.sergeykozhukhov.habits.base.data.converter.ProgressesConverter;
import ru.sergeykozhukhov.habits.base.model.data.HabitWithProgressesData;
import ru.sergeykozhukhov.habits.base.model.domain.HabitWithProgresses;
import ru.sergeykozhukhov.habits.base.model.domain.Progress;
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

    private AuthenticationConverter authenticationConverter;
    private ConfidentialityConverter confidentialityConverter;

    private HabitConverter habitConverter;
    private ProgressesConverter progressesConverter;
    private HabitWithProgressesConverter habitWithProgressesConverter;

    private JwtConverter jwtConverter;

    public HabitsWebRepository(
            @NonNull HabitsRetrofitClient habitsRetrofitClient,
            @NonNull AuthenticationConverter authenticationConverter,
            @NonNull ConfidentialityConverter confidentialityConverter,
            @NonNull HabitConverter habitConverter,
            @NonNull ProgressesConverter progressesConverter,
            @NonNull HabitWithProgressesConverter habitWithProgressesConverter,
            @NonNull JwtConverter jwtConverter) {
        this.habitsRetrofitClient = habitsRetrofitClient;
        this.authenticationConverter = authenticationConverter;
        this.confidentialityConverter = confidentialityConverter;
        this.habitConverter = habitConverter;
        this.progressesConverter = progressesConverter;
        this.habitWithProgressesConverter = habitWithProgressesConverter;
        this.jwtConverter = jwtConverter;
        habitsService = habitsRetrofitClient.getApiService();
    }



    @Override
    public Single<Jwt> authenticateClient(Confidentiality confidentiality) {
        return habitsService.authenticateClient(confidentialityConverter.convertFrom(confidentiality))
                .map(new Function<JwtData, Jwt>() {
                    @Override
                    public Jwt apply(JwtData jwtData) throws Exception {
                        Log.d(TAG, "authenticateClient: " + jwtData.getJwt());
                        return jwtConverter.convertTo(jwtData);
                    }
                });
    }

    @Override
    public Single<Habit> insertHabit(Habit habit, String jwt) {

        return habitsService.insertHabit(habitConverter.convertFrom(habit), jwt)
                .map(new Function<HabitData, Habit>() {
                    @Override
                    public Habit apply(HabitData habitData) throws Exception {
                        Log.d(TAG, "insertHabit: "+ habitData.getIdHabitServer());
                        return habitConverter.convertTo(habitData);
                    }
                });
    }

    @Override
    public Completable insertHabits(List<Habit> habitList, String jwt) {
        List<HabitData> habitDataList = new ArrayList<>(habitList.size());
        for (Habit habit : habitList){
            habitDataList.add(habitConverter.convertFrom(habit));
        }
        return habitsService.insertHabitList(habitDataList, jwt);
    }

    @Override
    public Single<List<Habit>> loadHabitList(String jwt) {
        return habitsService.loadHabitList(jwt)
                .map(new Function<List<HabitData>, List<Habit>>() {
                    @Override
                    public List<Habit> apply(List<HabitData> habitDataList) throws Exception {
                        List<Habit> convertHabitList = new ArrayList<>(habitDataList.size());
                        for (HabitData habitData : habitDataList) {
                            Log.d(TAG, "loadListHabitsWeb"+habitData.toString());
                            convertHabitList.add(habitConverter.convertTo(habitData));
                        }
                        return convertHabitList;
                    }
                });
    }

    @Override
    public Completable insertProgressList(List<Progress> progressList, String jwt) {
        return habitsService.insertProgressList(progressesConverter.convertFrom(progressList), jwt);
    }

    @Override
    public Completable insertHabitWithProgressesList(List<HabitWithProgresses> habitWithProgressesList, String jwt) {

        List<HabitWithProgressesData> habitWithProgressesDataList = new ArrayList<>(habitWithProgressesList.size());
        for (HabitWithProgresses habitWithProgresses : habitWithProgressesList){
            habitWithProgressesDataList.add(habitWithProgressesConverter.convertFrom(habitWithProgresses));
        }

        return habitsService.insertHabitWithProgressesList(habitWithProgressesDataList, jwt);
    }


    @Override
    public Single<List<HabitWithProgresses>> loadHabitWithProgressesList(String jwt) {
        return habitsService.loadHabitWithProgressesList(jwt)
                .map(new Function<List<HabitWithProgressesData>, List<HabitWithProgresses>>() {
                    @Override
                    public List<HabitWithProgresses> apply(List<HabitWithProgressesData> habitWithProgressesData) throws Exception {
                        List<HabitWithProgresses> habitWithProgressesList = new ArrayList<>(habitWithProgressesData.size());
                        for (HabitWithProgressesData habitWithProgressesData1 : habitWithProgressesData){
                            habitWithProgressesList.add(habitWithProgressesConverter.convertTo(habitWithProgressesData1));
                        }

                        return habitWithProgressesList;
                    }
                });
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
