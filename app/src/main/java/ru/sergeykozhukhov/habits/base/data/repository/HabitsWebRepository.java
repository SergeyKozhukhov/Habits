package ru.sergeykozhukhov.habits.base.data.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.functions.Function;
import retrofit2.Response;
import ru.sergeykozhukhov.habits.base.data.converter.AuthenticationConverter;
import ru.sergeykozhukhov.habits.base.data.converter.ConfidentialityConverter;
import ru.sergeykozhukhov.habits.base.data.converter.HabitConverter;
import ru.sergeykozhukhov.habits.base.data.model.AuthenticationData;
import ru.sergeykozhukhov.habits.base.data.model.HabitData;
import ru.sergeykozhukhov.habits.base.data.retrofit.HabitsRetrofitClient;
import ru.sergeykozhukhov.habits.base.data.retrofit.IHabitsService;
import ru.sergeykozhukhov.habits.base.domain.IHabitsWebRepository;
import ru.sergeykozhukhov.habits.base.domain.model.Authentication;
import ru.sergeykozhukhov.habits.base.domain.model.Confidentiality;
import ru.sergeykozhukhov.habits.base.domain.model.Habit;

public class HabitsWebRepository implements IHabitsWebRepository {

    public static final String TAG = "HabitsWebRepository";

    private HabitsRetrofitClient habitsRetrofitClient;
    private IHabitsService habitsService;

    private AuthenticationConverter authenticationConverter;
    private ConfidentialityConverter confidentialityConverter;

    private HabitConverter habitConverter;

    public HabitsWebRepository(
            @NonNull HabitsRetrofitClient habitsRetrofitClient,
            @NonNull AuthenticationConverter authenticationConverter,
            @NonNull ConfidentialityConverter confidentialityConverter,
            @NonNull HabitConverter habitConverter) {
        this.habitsRetrofitClient = habitsRetrofitClient;
        this.authenticationConverter = authenticationConverter;
        this.confidentialityConverter = confidentialityConverter;
        this.habitConverter = habitConverter;
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
        else {

            if (response.body() == null || response.errorBody() != null) {
                return null;
            }
        }

        return authenticationConverter.convertTo(response.body());


    }

    @Override
    public Single<Authentication> authClientRx(Confidentiality confidentiality) {
        return habitsService.authClientRx(confidentialityConverter.convertFrom(confidentiality))
                .map(new Function<AuthenticationData, Authentication>() {
                    @Override
                    public Authentication apply(AuthenticationData authenticationData) throws Exception {
                        Log.d(TAG, "authClientRx: " + authenticationData.getMessage());
                        return authenticationConverter.convertTo(authenticationData);
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
    public Single<Long> insertHabits(List<Habit> habitList, String jwt) {
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
                            convertHabitList.add(habitConverter.convertTo(habitData));
                        }
                        return convertHabitList;
                    }
                });
    }



}
