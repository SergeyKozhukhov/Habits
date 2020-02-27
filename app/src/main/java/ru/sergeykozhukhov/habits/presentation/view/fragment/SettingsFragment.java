package ru.sergeykozhukhov.habits.presentation.view.fragment;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.presentation.factory.ViewModelFactory;
import ru.sergeykozhukhov.habits.presentation.viewmodel.SettingsViewModel;

/**
 * Fragment для настроек приложения и существенными операциями с данными
 */
public class SettingsFragment extends PreferenceFragmentCompat {

    private SettingsViewModel settingsViewModel;

    public static Fragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.prefs, null);
        initListeners();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupMvvm();
    }

    private void initListeners() {
        Preference accountPreference = findPreference(getString(R.string.pref_account_key));
        if (accountPreference != null) {
            accountPreference.setOnPreferenceClickListener(preference -> {
                FragmentActivity activity = getActivity();
                if (activity instanceof OnViewsClickListener)
                    ((OnViewsClickListener) activity).onOpenAccountClick();
                return true;
            });
        }

        Preference deleteAllPreference = findPreference(getString(R.string.pref_delete_all_key));
        if (deleteAllPreference != null) {
            deleteAllPreference.setOnPreferenceClickListener(preference -> {
                settingsViewModel.deleteAllHabits();
                return true;
            });
        }
    }

    private void setupMvvm() {
        settingsViewModel = new ViewModelProvider(this, new ViewModelFactory(requireContext())).get(SettingsViewModel.class);

        settingsViewModel.getSuccessSingleLiveEvent().observe(getViewLifecycleOwner(),
                idRes -> Toast.makeText(requireContext(), getString(idRes), Toast.LENGTH_SHORT).show());

        settingsViewModel.getErrorSingleLiveEvent().observe(getViewLifecycleOwner(),
                idRes -> Toast.makeText(requireContext(), getString(idRes), Toast.LENGTH_SHORT).show());
    }

    /**
     * Слушатель нажатия на элементы настроек
     */
    public interface OnViewsClickListener {

        /**
         * Обработчик нажатия на кнопку входа в аккаунт
         */
        void onOpenAccountClick();
    }
}
