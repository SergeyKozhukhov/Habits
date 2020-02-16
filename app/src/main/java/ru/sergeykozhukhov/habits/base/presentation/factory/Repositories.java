package ru.sergeykozhukhov.habits.base.presentation.factory;

import android.content.Context;

import ru.sergeykozhukhov.habits.base.data.converter.ConfidentialityConverter;
import ru.sergeykozhukhov.habits.base.data.converter.HabitConverter;
import ru.sergeykozhukhov.habits.base.data.converter.HabitListConverter;
import ru.sergeykozhukhov.habits.base.data.converter.HabitWithProgressesConverter;
import ru.sergeykozhukhov.habits.base.data.converter.HabitWithProgressesListConverter;
import ru.sergeykozhukhov.habits.base.data.converter.JwtConverter;
import ru.sergeykozhukhov.habits.base.data.converter.ProgressConverter;
import ru.sergeykozhukhov.habits.base.data.converter.ProgressListConverter;
import ru.sergeykozhukhov.habits.base.data.converter.RegistrationConverter;
import ru.sergeykozhukhov.habits.base.data.converter.StatisticConverter;
import ru.sergeykozhukhov.habits.base.data.converter.StatisticListConverter;
import ru.sergeykozhukhov.habits.base.data.database.HabitsDatabase;
import ru.sergeykozhukhov.habits.base.data.preferences.HabitsPreferences;
import ru.sergeykozhukhov.habits.base.data.repository.HabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.base.data.repository.HabitsPreferencesRepository;
import ru.sergeykozhukhov.habits.base.data.repository.HabitsWebRepository;
import ru.sergeykozhukhov.habits.base.data.retrofit.HabitsRetrofitClient;
import ru.sergeykozhukhov.habits.base.domain.IHabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.base.domain.IHabitsPreferencesRepository;
import ru.sergeykozhukhov.habits.base.domain.IHabitsWebRepository;
import ru.sergeykozhukhov.habits.base.domain.IRepository;
import ru.sergeykozhukhov.habits.notes.backup.AuthenticationConverter;

/**
 * Фабрика создания репозиториев
 */
public class Repositories {

    public static IHabitsDatabaseRepository newDatabaseRepository(Context context){
        return new HabitsDatabaseRepository(
                HabitsDatabase.getInstance(context),
                new HabitConverter(),
                new HabitListConverter(new HabitConverter()),
                new ProgressConverter(),
                new ProgressListConverter(new ProgressConverter()),
                new HabitWithProgressesConverter(new HabitConverter(), new ProgressListConverter(new ProgressConverter())),
                new HabitWithProgressesListConverter(new HabitWithProgressesConverter(new HabitConverter(), new ProgressListConverter(new ProgressConverter()))),
                new StatisticListConverter(new StatisticConverter()));
    }

    public static IHabitsWebRepository newWebRepository(){
        return new HabitsWebRepository(
                HabitsRetrofitClient.getInstance(),
                new RegistrationConverter(),
                new AuthenticationConverter(),
                new ConfidentialityConverter(),
                new HabitConverter(),
                new HabitListConverter(new HabitConverter()),
                new ProgressListConverter(new ProgressConverter()),
                new HabitWithProgressesConverter(new HabitConverter(), new ProgressListConverter(new ProgressConverter())),
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
