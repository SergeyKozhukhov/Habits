package ru.sergeykozhukhov.habits.presentation.factory;

import android.content.Context;

import ru.sergeykozhukhov.habits.data.converter.ConfidentialityConverter;
import ru.sergeykozhukhov.habits.data.converter.HabitConverter;
import ru.sergeykozhukhov.habits.data.converter.HabitListConverter;
import ru.sergeykozhukhov.habits.data.converter.HabitWithProgressesConverter;
import ru.sergeykozhukhov.habits.data.converter.HabitWithProgressesListConverter;
import ru.sergeykozhukhov.habits.data.converter.JwtConverter;
import ru.sergeykozhukhov.habits.data.converter.ProgressConverter;
import ru.sergeykozhukhov.habits.data.converter.ProgressListConverter;
import ru.sergeykozhukhov.habits.data.converter.RegistrationConverter;
import ru.sergeykozhukhov.habits.data.converter.StatisticConverter;
import ru.sergeykozhukhov.habits.data.converter.StatisticListConverter;
import ru.sergeykozhukhov.habits.data.database.HabitsDatabase;
import ru.sergeykozhukhov.habits.data.preferences.HabitsPreferences;
import ru.sergeykozhukhov.habits.data.repository.HabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.data.repository.HabitsPreferencesRepository;
import ru.sergeykozhukhov.habits.data.repository.HabitsWebRepository;
import ru.sergeykozhukhov.habits.data.retrofit.HabitsRetrofitClient;
import ru.sergeykozhukhov.habits.domain.IHabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.domain.IHabitsPreferencesRepository;
import ru.sergeykozhukhov.habits.domain.IHabitsWebRepository;

/**
 * Фабрика создания репозиториев
 */
public class Repositories {

    public static IHabitsDatabaseRepository newDatabaseRepository(Context context){
        return new HabitsDatabaseRepository(
                HabitsDatabase.getInstance(context),
                new HabitConverter(),
                new HabitListConverter(new HabitConverter()),
                new ProgressListConverter(new ProgressConverter()),
                new HabitWithProgressesConverter(new HabitConverter(), new ProgressListConverter(new ProgressConverter())),
                new HabitWithProgressesListConverter(new HabitWithProgressesConverter(new HabitConverter(), new ProgressListConverter(new ProgressConverter()))),
                new StatisticListConverter(new StatisticConverter()));
    }

    public static IHabitsWebRepository newWebRepository(){
        return new HabitsWebRepository(
                HabitsRetrofitClient.getInstance(),
                HabitsRetrofitClient.getInstance().getApiService(),
                new RegistrationConverter(),
                new ConfidentialityConverter(),
                new HabitWithProgressesListConverter(new HabitWithProgressesConverter(new HabitConverter(), new ProgressListConverter(new ProgressConverter()))),
                new JwtConverter()
        );
    }

    public static IHabitsPreferencesRepository newPreferencesRepository(Context context){
        return new HabitsPreferencesRepository(
                HabitsPreferences.getInstance(context),
                new JwtConverter()
        );
    }
}
