package ru.sergeykozhukhov.habits.presentation.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.presentation.factory.ViewModelFactory;
import ru.sergeykozhukhov.habits.presentation.viewmodel.SettingsViewModel;

public class SettingsFragment extends PreferenceFragmentCompat {

    private SettingsViewModel settingsViewModel;

    public static Fragment newInstance() {

        return new SettingsFragment();
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.prefs, null);

        Preference accountPreference = findPreference(getString(R.string.pref_account_key));
        if (accountPreference != null){
            accountPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    FragmentActivity activity = getActivity();
                    if (activity instanceof OnAccountClickListener){
                        ((OnAccountClickListener)activity).onAccountClick();

                    }
                    return true;
                }
            });
        }

        Preference deleteAllPreference = findPreference(getString(R.string.pref_delete_all_key));
        if (deleteAllPreference != null){
            deleteAllPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    settingsViewModel.deleteAllHabits();

                    return true;
                }
            });
        }
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initData();
        initListeners();
        initViews();

        setupMvvm();


    }

    private void initViews() {

    }

    private void initListeners(){
        
    }

    private void initData(){

    }

    private void setupMvvm(){

        settingsViewModel = ViewModelProviders.of(this, new ViewModelFactory(requireContext()))
                .get(SettingsViewModel.class);


        settingsViewModel.getErrorSingleLiveEvent().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer idRes) {
                Toast.makeText(requireContext(), getString(idRes), Toast.LENGTH_SHORT).show();
            }
        });

        settingsViewModel.getSuccessSingleLiveEvent().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer idRes) {
                Toast.makeText(requireContext(), getString(idRes), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public interface OnAccountClickListener{
        void onAccountClick();
    }


}