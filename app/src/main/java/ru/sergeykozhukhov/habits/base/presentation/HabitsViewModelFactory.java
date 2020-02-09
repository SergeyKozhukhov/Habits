package ru.sergeykozhukhov.habits.base.presentation;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.sergeykozhukhov.habits.notes.backup.AuthenticationConverter;
import ru.sergeykozhukhov.habits.base.data.converter.ConfidentialityConverter;
import ru.sergeykozhukhov.habits.base.data.converter.HabitConverter;
import ru.sergeykozhukhov.habits.base.data.converter.HabitsConverter;
import ru.sergeykozhukhov.habits.base.data.converter.JwtConverter;
import ru.sergeykozhukhov.habits.base.data.database.HabitsDatabase;
import ru.sergeykozhukhov.habits.base.data.preferences.HabitsPreferences;
import ru.sergeykozhukhov.habits.base.data.repository.HabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.base.data.repository.HabitsPreferencesRepository;
import ru.sergeykozhukhov.habits.base.data.repository.HabitsWebRepository;
import ru.sergeykozhukhov.habits.base.data.retrofit.HabitsRetrofitClient;
import ru.sergeykozhukhov.habits.base.domain.IHabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.base.domain.IHabitsPreferencesRepository;
import ru.sergeykozhukhov.habits.base.domain.IHabitsWebRepository;
import ru.sergeykozhukhov.habits.base.domain.usecase.AuthenticateWebInteractor;
import ru.sergeykozhukhov.habits.base.domain.usecase.DeleteAllHabitsDbInteractor;
import ru.sergeykozhukhov.habits.base.domain.usecase.InsertHabitDbInteractor;
import ru.sergeykozhukhov.habits.base.domain.usecase.BackupWebHabitListWebInteractor;
import ru.sergeykozhukhov.habits.base.domain.usecase.LoadHabitsDbInteractor;
import ru.sergeykozhukhov.habits.base.domain.usecase.ReplicationListHabitsWebInteractor;

public class HabitsViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private Context context;

    public HabitsViewModelFactory(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if(HabitsListViewModel.class.equals(modelClass)){

            IHabitsDatabaseRepository habitsDatabaseRepository = new HabitsDatabaseRepository(
                    HabitsDatabase.getInstance(context),
                    new HabitConverter(),
                    new HabitsConverter());


            LoadHabitsDbInteractor loadHabitsInteractor = new LoadHabitsDbInteractor(habitsDatabaseRepository);
            DeleteAllHabitsDbInteractor deleteAllHabitsInteractor = new DeleteAllHabitsDbInteractor(habitsDatabaseRepository);

            // noinspection unchecked
            return (T) new HabitsListViewModel(
                    loadHabitsInteractor, deleteAllHabitsInteractor);
        }
        else if (AuthenticationViewModel.class.equals(modelClass))
        {

            IHabitsWebRepository habitsWebRepository = new HabitsWebRepository(
                    HabitsRetrofitClient.getInstance(),
                    new AuthenticationConverter(),
                    new ConfidentialityConverter(),
                    new HabitConverter(),
                    new JwtConverter()
            );

            IHabitsPreferencesRepository habitsPreferencesRepository = new HabitsPreferencesRepository(
                    HabitsPreferences.getInstance(context),
                    new JwtConverter()
            );

            AuthenticateWebInteractor authenticateClientInteractor = new AuthenticateWebInteractor(habitsWebRepository, habitsPreferencesRepository);

            // noinspection unchecked
            return (T) new AuthenticationViewModel(authenticateClientInteractor);
        }
        else if (AddHabitViewModel.class.equals(modelClass)){
            IHabitsDatabaseRepository habitsDatabaseRepository = new HabitsDatabaseRepository(
                    HabitsDatabase.getInstance(context),
                    new HabitConverter(),
                    new HabitsConverter());

            InsertHabitDbInteractor insertHabitInteractor = new InsertHabitDbInteractor(habitsDatabaseRepository);

            // noinspection unchecked
            return (T) new AddHabitViewModel(insertHabitInteractor);
        }
        else if (BackupViewModel.class.equals(modelClass)){
            IHabitsDatabaseRepository habitsDatabaseRepository = new HabitsDatabaseRepository(
                    HabitsDatabase.getInstance(context),
                    new HabitConverter(),
                    new HabitsConverter());

            IHabitsWebRepository habitsWebRepository = new HabitsWebRepository(
                    HabitsRetrofitClient.getInstance(),
                    new AuthenticationConverter(),
                    new ConfidentialityConverter(),
                    new HabitConverter(),
                    new JwtConverter()
            );

            IHabitsPreferencesRepository habitsPreferencesRepository = new HabitsPreferencesRepository(
                    HabitsPreferences.getInstance(context),
                    new JwtConverter()
            );

            BackupWebHabitListWebInteractor insertWebHabitsInteractor = new BackupWebHabitListWebInteractor(habitsWebRepository, habitsDatabaseRepository, habitsPreferencesRepository);
            ReplicationListHabitsWebInteractor replicationListHabitsWebInteractor = new ReplicationListHabitsWebInteractor(habitsWebRepository, habitsDatabaseRepository, habitsPreferencesRepository);


            // noinspection unchecked
            return (T) new BackupViewModel(
                    insertWebHabitsInteractor,
                    replicationListHabitsWebInteractor);
        }
        else{
            return super.create(modelClass);
        }
    }
}
