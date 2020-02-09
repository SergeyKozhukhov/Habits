package ru.sergeykozhukhov.habits.base.data.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.functions.Function;
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

    private JwtConverter jwtConverter;

    public HabitsWebRepository(
            @NonNull HabitsRetrofitClient habitsRetrofitClient,
            @NonNull AuthenticationConverter authenticationConverter,
            @NonNull ConfidentialityConverter confidentialityConverter,
            @NonNull HabitConverter habitConverter,
            @NonNull JwtConverter jwtConverter) {
        this.habitsRetrofitClient = habitsRetrofitClient;
        this.authenticationConverter = authenticationConverter;
        this.confidentialityConverter = confidentialityConverter;
        this.habitConverter = habitConverter;
        this.jwtConverter = jwtConverter;
        habitsService = habitsRetrofitClient.getApiService();
    }



    @Override
    public Single<Jwt> authClientRx(Confidentiality confidentiality) {
        return habitsService.authClientRx(confidentialityConverter.convertFrom(confidentiality))
                .map(new Function<JwtData, Jwt>() {
                    @Override
                    public Jwt apply(JwtData jwtData) throws Exception {
                        Log.d(TAG, "authClientRx: " + jwtData.getJwt());
                        return jwtConverter.convertTo(jwtData);
                    }
                });
    }

    @Override
    public Single<Habit> insertHabit(Habit habit, String jwt) {

        return habitsService.insertHabits(habitConverter.convertFrom(habit), jwt)
                .map(new Function<HabitData, Habit>() {
                    @Override
                    public Habit apply(HabitData habitData) throws Exception {
                        Log.d(TAG, "insertHabits: "+ habitData.getIdHabitServer());
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
        return habitsService.insertHabits(habitDataList, jwt);
    }

    @Override
    public Single<List<Habit>> loadListHabits(String jwt) {
        return habitsService.loadListHabits(jwt)
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
    public void setJwt(@NonNull Jwt jwt) {
        habitsRetrofitClient.setJwtData(jwtConverter.convertFrom(jwt));
    }

    @Override
    public Jwt getJwt() {
        return jwtConverter.convertTo(habitsRetrofitClient.getJwtData());
    }


}
