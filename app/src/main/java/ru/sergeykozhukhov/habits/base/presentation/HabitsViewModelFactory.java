package ru.sergeykozhukhov.habits.base.presentation;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.concurrent.Executors;

import retrofit2.http.GET;
import ru.sergeykozhukhov.habits.base.data.converter.AuthenticationConverter;
import ru.sergeykozhukhov.habits.base.data.converter.ConfidentialityConverter;
import ru.sergeykozhukhov.habits.base.data.converter.HabitConverter;
import ru.sergeykozhukhov.habits.base.data.converter.HabitsConverter;
import ru.sergeykozhukhov.habits.base.data.converter.JwtConverter;
import ru.sergeykozhukhov.habits.base.data.preferences.HabitsPreferences;
import ru.sergeykozhukhov.habits.base.data.repository.HabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.base.data.repository.HabitsPreferencesRepository;
import ru.sergeykozhukhov.habits.base.data.repository.HabitsWebRepository;
import ru.sergeykozhukhov.habits.base.data.retrofit.HabitsRetrofitClient;
import ru.sergeykozhukhov.habits.base.domain.IHabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.base.domain.IHabitsPreferencesRepository;
import ru.sergeykozhukhov.habits.base.domain.IHabitsWebRepository;
import ru.sergeykozhukhov.habits.base.domain.usecase.AuthenticateClientInteractor;
import ru.sergeykozhukhov.habits.base.domain.usecase.DeleteAllHabitsInteractor;
import ru.sergeykozhukhov.habits.base.domain.usecase.GetJwtInteractor;
import ru.sergeykozhukhov.habits.base.domain.usecase.InsertHabitInteractor;
import ru.sergeykozhukhov.habits.base.domain.usecase.InsertListHabitsDBInteractor;
import ru.sergeykozhukhov.habits.base.domain.usecase.InsertWebHabitInteractor;
import ru.sergeykozhukhov.habits.base.domain.usecase.InsertWebHabitsInteractor;
import ru.sergeykozhukhov.habits.base.domain.usecase.LoadHabitsInteractor;
import ru.sergeykozhukhov.habits.base.domain.usecase.LoadJwtInteractor;
import ru.sergeykozhukhov.habits.base.domain.usecase.LoadListHabitsWebInteractor;
import ru.sergeykozhukhov.habits.base.domain.usecase.SaveJwtInteractor;
import ru.sergeykozhukhov.habits.base.domain.usecase.SetJwtInteractor;
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
                    context,
                    Executors.newSingleThreadExecutor(),
                    new HabitConverter(),
                    new HabitsConverter());

            IHabitsWebRepository habitsWebRepository = new HabitsWebRepository(
                    new HabitsRetrofitClient(),
                    new AuthenticationConverter(),
                    new ConfidentialityConverter(),
                    new HabitConverter()
            );

            IHabitsPreferencesRepository habitsPreferencesRepository = new HabitsPreferencesRepository(
                    context,
                    new HabitsPreferences(),
                    new JwtConverter()
            );

            LoadHabitsInteractor loadHabitsInteractor = new LoadHabitsInteractor(habitsDatabaseRepository);
            InsertHabitInteractor insertHabitInteractor = new InsertHabitInteractor(habitsDatabaseRepository);
            InsertListHabitsDBInteractor insertListHabitsDBInteractor = new InsertListHabitsDBInteractor(habitsDatabaseRepository);
            UpdateHabitInteractor updateHabitInteractor = new UpdateHabitInteractor(habitsDatabaseRepository);
            DeleteAllHabitsInteractor deleteAllHabitsInteractor = new DeleteAllHabitsInteractor(habitsDatabaseRepository);

            AuthenticateClientInteractor authenticateClientInteractor = new AuthenticateClientInteractor(habitsWebRepository);
            InsertWebHabitInteractor insertWebHabitInteractor = new InsertWebHabitInteractor(habitsWebRepository);
            InsertWebHabitsInteractor insertWebHabitsInteractor = new InsertWebHabitsInteractor(habitsWebRepository);
            LoadListHabitsWebInteractor loadListHabitsWebInteractor = new LoadListHabitsWebInteractor(habitsWebRepository);

            SaveJwtInteractor saveJwtInteractor = new SaveJwtInteractor(habitsPreferencesRepository);
            LoadJwtInteractor loadJwtInteractor = new LoadJwtInteractor(habitsPreferencesRepository);
            SetJwtInteractor setJwtInteractor = new SetJwtInteractor(habitsPreferencesRepository);
            GetJwtInteractor getJwtInteractor = new GetJwtInteractor(habitsPreferencesRepository);

            // noinspection unchecked
            return (T) new HabitsViewModel(
                    loadHabitsInteractor,
                    insertHabitInteractor,
                    insertListHabitsDBInteractor,
                    updateHabitInteractor,
                    deleteAllHabitsInteractor,
                    authenticateClientInteractor,
                    insertWebHabitInteractor,
                    insertWebHabitsInteractor,
                    loadListHabitsWebInteractor,
                    saveJwtInteractor,
                    loadJwtInteractor,
                    setJwtInteractor,
                    getJwtInteractor);
        }
        else{
            return super.create(modelClass);
        }
    }
}
