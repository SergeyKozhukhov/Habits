package ru.sergeykozhukhov.habits.presentation.factory;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.sergeykozhukhov.habits.domain.usecaseimpl.DeleteJwtInteractor;
import ru.sergeykozhukhov.habits.domain.usecaseimpl.LoadStatisticListInteractor;
import ru.sergeykozhukhov.habits.domain.usecaseimpl.RegisterWebInteractor;
import ru.sergeykozhukhov.habits.domain.usecaseimpl.BuildConfidentialityInteractor;
import ru.sergeykozhukhov.habits.domain.usecaseimpl.BuildHabitInteractor;
import ru.sergeykozhukhov.habits.domain.usecaseimpl.ChangeProgressListDbInteractor;
import ru.sergeykozhukhov.habits.domain.usecaseimpl.BuildRegistrationInteractor;
import ru.sergeykozhukhov.habits.domain.usecaseimpl.GetJwtValueInteractor;
import ru.sergeykozhukhov.habits.domain.IHabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.domain.IHabitsPreferencesRepository;
import ru.sergeykozhukhov.habits.domain.IHabitsWebRepository;
import ru.sergeykozhukhov.habits.domain.usecaseimpl.AuthenticateWebInteractor;
import ru.sergeykozhukhov.habits.domain.usecaseimpl.DeleteAllHabitsDbInteractor;
import ru.sergeykozhukhov.habits.domain.usecaseimpl.InsertHabitDbInteractor;
import ru.sergeykozhukhov.habits.domain.usecaseimpl.BackupWebHabitListWebInteractor;
import ru.sergeykozhukhov.habits.domain.usecaseimpl.LoadHabitListDbInteractor;
import ru.sergeykozhukhov.habits.domain.usecaseimpl.ReplicationListHabitsWebInteractor;
import ru.sergeykozhukhov.habits.presentation.viewmodel.AccountManagerViewModel;
import ru.sergeykozhukhov.habits.presentation.viewmodel.AddHabitViewModel;
import ru.sergeykozhukhov.habits.presentation.viewmodel.AuthenticationViewModel;
import ru.sergeykozhukhov.habits.presentation.viewmodel.AccountViewModel;
import ru.sergeykozhukhov.habits.presentation.viewmodel.HabitsListViewModel;
import ru.sergeykozhukhov.habits.presentation.viewmodel.ProgressViewModel;
import ru.sergeykozhukhov.habits.presentation.viewmodel.RegistrationViewModel;
import ru.sergeykozhukhov.habits.presentation.viewmodel.SettingsViewModel;
import ru.sergeykozhukhov.habits.presentation.viewmodel.StatisticsViewModel;

/**
 * Фабрика создания ViewModel
 */
public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private Context context;

    public ViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if (HabitsListViewModel.class.equals(modelClass)) {
            IHabitsDatabaseRepository habitsDatabaseRepository = Repositories.newDatabaseRepository(context);

            LoadHabitListDbInteractor loadHabitsInteractor = new LoadHabitListDbInteractor(habitsDatabaseRepository);
            // noinspection unchecked
            return (T) new HabitsListViewModel(loadHabitsInteractor);
        } else if (RegistrationViewModel.class.equals(modelClass)) {
            IHabitsWebRepository habitsWebRepository = Repositories.newWebRepository();

            BuildRegistrationInteractor buildRegistrationInstance = new BuildRegistrationInteractor();
            RegisterWebInteractor registerWebInteractor = new RegisterWebInteractor(habitsWebRepository, buildRegistrationInstance);
            // noinspection unchecked
            return (T) new RegistrationViewModel(registerWebInteractor);
        } else if (AuthenticationViewModel.class.equals(modelClass)) {
            IHabitsWebRepository habitsWebRepository = Repositories.newWebRepository();
            IHabitsPreferencesRepository habitsPreferencesRepository = Repositories.newPreferencesRepository(context);

            BuildConfidentialityInteractor buildConfidentialityInstance = new BuildConfidentialityInteractor();
            AuthenticateWebInteractor authenticateClientInteractor = new AuthenticateWebInteractor(
                    habitsWebRepository,
                    habitsPreferencesRepository,
                    buildConfidentialityInstance);
            // noinspection unchecked
            return (T) new AuthenticationViewModel(authenticateClientInteractor);
        } else if (AddHabitViewModel.class.equals(modelClass)) {
            IHabitsDatabaseRepository habitsDatabaseRepository = Repositories.newDatabaseRepository(context);

            BuildHabitInteractor buildHabitInstance = new BuildHabitInteractor();

            InsertHabitDbInteractor insertHabitInteractor = new InsertHabitDbInteractor(habitsDatabaseRepository, buildHabitInstance);
            // noinspection unchecked
            return (T) new AddHabitViewModel(insertHabitInteractor);
        } else if (AccountViewModel.class.equals(modelClass)) {
            IHabitsDatabaseRepository habitsDatabaseRepository = Repositories.newDatabaseRepository(context);
            IHabitsWebRepository habitsWebRepository = Repositories.newWebRepository();
            IHabitsPreferencesRepository habitsPreferencesRepository = Repositories.newPreferencesRepository(context);

            GetJwtValueInteractor getJwtValue = new GetJwtValueInteractor(habitsWebRepository, habitsPreferencesRepository);
            DeleteJwtInteractor deleteJwtInteractor = new DeleteJwtInteractor(habitsWebRepository, habitsPreferencesRepository);
            BackupWebHabitListWebInteractor insertWebHabitsInteractor = new BackupWebHabitListWebInteractor(
                    habitsWebRepository,
                    habitsDatabaseRepository,
                    getJwtValue);
            ReplicationListHabitsWebInteractor replicationListHabitsWebInteractor = new ReplicationListHabitsWebInteractor(
                    habitsWebRepository,
                    habitsDatabaseRepository,
                    getJwtValue);
            // noinspection unchecked
            return (T) new AccountViewModel(
                    insertWebHabitsInteractor,
                    replicationListHabitsWebInteractor,
                    deleteJwtInteractor);
        } else if (ProgressViewModel.class.equals(modelClass)) {
            IHabitsDatabaseRepository habitsDatabaseRepository = Repositories.newDatabaseRepository(context);

            ChangeProgressListDbInteractor changeProgressListDbInteractor = new ChangeProgressListDbInteractor(habitsDatabaseRepository);
            // noinspection unchecked
            return (T) new ProgressViewModel(changeProgressListDbInteractor);
        } else if (StatisticsViewModel.class.equals(modelClass)) {
            IHabitsDatabaseRepository habitsDatabaseRepository = Repositories.newDatabaseRepository(context);

            LoadStatisticListInteractor loadStatisticsListInteractor = new LoadStatisticListInteractor(habitsDatabaseRepository);
            // noinspection unchecked
            return (T) new StatisticsViewModel(loadStatisticsListInteractor);
        } else if (AccountManagerViewModel.class.equals(modelClass)) {
            IHabitsWebRepository habitsWebRepository = Repositories.newWebRepository();
            IHabitsPreferencesRepository habitsPreferencesRepository = Repositories.newPreferencesRepository(context);

            GetJwtValueInteractor getJwtValueInteractor = new GetJwtValueInteractor(habitsWebRepository, habitsPreferencesRepository);
            // noinspection unchecked
            return (T) new AccountManagerViewModel(getJwtValueInteractor);
        } else if (SettingsViewModel.class.equals(modelClass)) {
            IHabitsDatabaseRepository habitsDatabaseRepository = Repositories.newDatabaseRepository(context);

            DeleteAllHabitsDbInteractor deleteAllHabitsDbInteractor = new DeleteAllHabitsDbInteractor(habitsDatabaseRepository);
            // noinspection unchecked
            return (T) new SettingsViewModel(deleteAllHabitsDbInteractor);
        } else {
            return super.create(modelClass);
        }
    }
}
