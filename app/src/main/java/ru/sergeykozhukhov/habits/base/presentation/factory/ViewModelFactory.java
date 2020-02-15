package ru.sergeykozhukhov.habits.base.presentation.factory;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.sergeykozhukhov.habits.base.domain.IInreractor.provider.IBuildConfidentialityInstance;
import ru.sergeykozhukhov.habits.base.domain.IInreractor.provider.IBuildHabitInstance;
import ru.sergeykozhukhov.habits.base.domain.IInreractor.provider.IBuildRegistrationInstance;
import ru.sergeykozhukhov.habits.base.domain.IInreractor.provider.IGetJwtValue;
import ru.sergeykozhukhov.habits.base.domain.usecase.LoadStatisticListInteractor;
import ru.sergeykozhukhov.habits.base.domain.usecase.RegistrateWebInteractor;
import ru.sergeykozhukhov.habits.base.domain.usecase.provider.BuildConfidentialityInstance;
import ru.sergeykozhukhov.habits.base.domain.usecase.provider.BuildHabitInstace;
import ru.sergeykozhukhov.habits.base.domain.usecase.ChangeProgressListDbInteractor;
import ru.sergeykozhukhov.habits.base.domain.usecase.provider.BuildRegistrationInstance;
import ru.sergeykozhukhov.habits.base.domain.usecase.provider.GetJwtValue;
import ru.sergeykozhukhov.habits.base.domain.IHabitsDatabaseRepository;
import ru.sergeykozhukhov.habits.base.domain.IHabitsPreferencesRepository;
import ru.sergeykozhukhov.habits.base.domain.IHabitsWebRepository;
import ru.sergeykozhukhov.habits.base.domain.usecase.AuthenticateWebInteractor;
import ru.sergeykozhukhov.habits.base.domain.usecase.DeleteAllHabitsDbInteractor;
import ru.sergeykozhukhov.habits.base.domain.usecase.util.InsertHabitDbInteractor;
import ru.sergeykozhukhov.habits.base.domain.usecase.BackupWebHabitListWebInteractor;
import ru.sergeykozhukhov.habits.base.domain.usecase.LoadHabitListDbInteractor;
import ru.sergeykozhukhov.habits.base.domain.usecase.ReplicationListHabitsWebInteractor;
import ru.sergeykozhukhov.habits.base.presentation.AddHabitViewModel;
import ru.sergeykozhukhov.habits.base.presentation.AuthenticationViewModel;
import ru.sergeykozhukhov.habits.base.presentation.BackupViewModel;
import ru.sergeykozhukhov.habits.base.presentation.HabitsListViewModel;
import ru.sergeykozhukhov.habits.base.presentation.ProgressViewModel;
import ru.sergeykozhukhov.habits.base.presentation.RegistrationViewModel;
import ru.sergeykozhukhov.habits.base.presentation.StatisticsViewModel;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private Context context;

    public ViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if(HabitsListViewModel.class.equals(modelClass)){

            IHabitsDatabaseRepository habitsDatabaseRepository = Repositories.newDatabaseRepository(context);

            LoadHabitListDbInteractor loadHabitsInteractor = new LoadHabitListDbInteractor(habitsDatabaseRepository);

            // noinspection unchecked
            return (T) new HabitsListViewModel(loadHabitsInteractor);
        }

        else if (RegistrationViewModel.class.equals(modelClass))
        {

            IHabitsWebRepository habitsWebRepository = Repositories.newWebRepository();

            IBuildRegistrationInstance buildRegistrationInstance = new BuildRegistrationInstance();

            RegistrateWebInteractor registrateWebInteractor = new RegistrateWebInteractor(habitsWebRepository, buildRegistrationInstance);

            // noinspection unchecked
            return (T) new RegistrationViewModel(registrateWebInteractor);
        }


        else if (AuthenticationViewModel.class.equals(modelClass))
        {

            IHabitsWebRepository habitsWebRepository = Repositories.newWebRepository();

            IHabitsPreferencesRepository habitsPreferencesRepository = Repositories.newPreferencesRepository(context);

            IBuildConfidentialityInstance buildConfidentialityInstance = new BuildConfidentialityInstance();
            AuthenticateWebInteractor authenticateClientInteractor = new AuthenticateWebInteractor(
                    habitsWebRepository,
                    habitsPreferencesRepository,
                    buildConfidentialityInstance);

            // noinspection unchecked
            return (T) new AuthenticationViewModel(authenticateClientInteractor);
        }
        else if (AddHabitViewModel.class.equals(modelClass)){
            IHabitsDatabaseRepository habitsDatabaseRepository = Repositories.newDatabaseRepository(context);

            IBuildHabitInstance buildHabitInstance = new BuildHabitInstace();

            InsertHabitDbInteractor insertHabitInteractor = new InsertHabitDbInteractor(habitsDatabaseRepository, buildHabitInstance);

            // noinspection unchecked
            return (T) new AddHabitViewModel(insertHabitInteractor);
        }
        else if (BackupViewModel.class.equals(modelClass)){
            IHabitsDatabaseRepository habitsDatabaseRepository = Repositories.newDatabaseRepository(context);

            IHabitsWebRepository habitsWebRepository = Repositories.newWebRepository();

            IHabitsPreferencesRepository habitsPreferencesRepository = Repositories.newPreferencesRepository(context);

            IGetJwtValue getJwtValue = new GetJwtValue(habitsWebRepository, habitsPreferencesRepository);

            BackupWebHabitListWebInteractor insertWebHabitsInteractor = new BackupWebHabitListWebInteractor(
                    habitsWebRepository,
                    habitsDatabaseRepository,
                    getJwtValue);
            ReplicationListHabitsWebInteractor replicationListHabitsWebInteractor = new ReplicationListHabitsWebInteractor(
                    habitsWebRepository,
                    habitsDatabaseRepository,
                    getJwtValue);

            DeleteAllHabitsDbInteractor deleteAllHabitsInteractor = new DeleteAllHabitsDbInteractor(habitsDatabaseRepository);

            // noinspection unchecked
            return (T) new BackupViewModel(
                    insertWebHabitsInteractor,
                    replicationListHabitsWebInteractor,
                    deleteAllHabitsInteractor);
        }
        else if (ProgressViewModel.class.equals(modelClass)){
            IHabitsDatabaseRepository habitsDatabaseRepository = Repositories.newDatabaseRepository(context);

            ChangeProgressListDbInteractor changeProgressListDbInteractor = new ChangeProgressListDbInteractor(habitsDatabaseRepository);
            // noinspection unchecked
            return (T) new ProgressViewModel(changeProgressListDbInteractor);
        }
        else if (StatisticsViewModel.class.equals(modelClass)){
            IHabitsDatabaseRepository habitsDatabaseRepository = Repositories.newDatabaseRepository(context);

            LoadStatisticListInteractor loadStatisticsListInteractor = new LoadStatisticListInteractor(habitsDatabaseRepository);        // noinspection unchecked
            // noinspection unchecked
            return (T) new StatisticsViewModel(loadStatisticsListInteractor);
        }
        else{
            return super.create(modelClass);
        }
    }
}
