package ru.sergeykozhukhov.habits.base.presentation;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.concurrent.Executors;

import ru.sergeykozhukhov.habits.base.data.converter.AuthenticationConverter;
import ru.sergeykozhukhov.habits.base.data.converter.ConfidentialityConverter;
import ru.sergeykozhukhov.habits.base.data.converter.HabitConverter;
import ru.sergeykozhukhov.habits.base.data.converter.HabitsConverter;
import ru.sergeykozhukhov.habits.base.data.preferences.HabitsPreferences;
import ru.sergeykozhukhov.habits.base.data.repository.HabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.base.data.repository.HabitsPreferencesRepository;
import ru.sergeykozhukhov.habits.base.data.repository.HabitsWebRepository;
import ru.sergeykozhukhov.habits.base.data.retrofit.HabitsRetrofitClient;
import ru.sergeykozhukhov.habits.base.domain.IHabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.base.domain.IHabitsPreferencesRepository;
import ru.sergeykozhukhov.habits.base.domain.IHabitsWebRepository;
import ru.sergeykozhukhov.habits.base.domain.usecase.AuthenticateClientInteractor;
import ru.sergeykozhukhov.habits.base.domain.usecase.InsertHabitInteractor;
import ru.sergeykozhukhov.habits.base.domain.usecase.LoadHabitsInteractor;
import ru.sergeykozhukhov.habits.base.domain.usecase.LoadJwtInteractor;
import ru.sergeykozhukhov.habits.base.domain.usecase.SaveJwtInteractor;
import ru.sergeykozhukhov.habits.base.domain.usecase.UpdateHabitInteractor;

public class HabitsViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private Context context;

    public HabitsViewModelFactory(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if(HabitsViewModel.class.equals(modelClass)){
            IHabitsDatabaseRepository habitsDatabaseRepository = new HabitsDatabaseRepository(
                    context, Executors.newSingleThreadExecutor(),
                    new HabitConverter(), new HabitsConverter());

            IHabitsWebRepository habitsWebRepository = new HabitsWebRepository(
                    new HabitsRetrofitClient(), new AuthenticationConverter(), new ConfidentialityConverter()
            );

            IHabitsPreferencesRepository habitsPreferencesRepository = new HabitsPreferencesRepository(
                    context, new HabitsPreferences()
            );

            LoadHabitsInteractor loadHabitsInteractor = new LoadHabitsInteractor(habitsDatabaseRepository);
            InsertHabitInteractor insertHabitInteractor = new InsertHabitInteractor(habitsDatabaseRepository);
            UpdateHabitInteractor updateHabitInteractor = new UpdateHabitInteractor(habitsDatabaseRepository);
            AuthenticateClientInteractor authenticateClientInteractor = new AuthenticateClientInteractor(habitsWebRepository);
            SaveJwtInteractor saveJwtInteractor = new SaveJwtInteractor(habitsPreferencesRepository);
            LoadJwtInteractor loadJwtInteractor = new LoadJwtInteractor(habitsPreferencesRepository);

            // noinspection unchecked
            return (T) new HabitsViewModel(
                    loadHabitsInteractor,
                    insertHabitInteractor,
                    updateHabitInteractor,
                    authenticateClientInteractor,
                    saveJwtInteractor,
                    loadJwtInteractor);
        }
        else{
            return super.create(modelClass);
        }
    }
}
