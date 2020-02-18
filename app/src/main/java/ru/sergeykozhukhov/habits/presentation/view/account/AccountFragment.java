package ru.sergeykozhukhov.habits.presentation.view.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.presentation.viewmodel.AccountViewModel;
import ru.sergeykozhukhov.habits.presentation.factory.ViewModelFactory;

public class AccountFragment extends Fragment{

    private AccountViewModel accountViewModel;

    private Button testBackupButton;
    private Button testReplicationButton;
    private Button testLogOut;

    public static Fragment newInstance() {

        return new AccountFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.account_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        testBackupButton = view.findViewById(R.id.account_backup_button);
        testReplicationButton = view.findViewById(R.id.account_replication_button);
        testLogOut= view.findViewById(R.id.account_log_out_button);
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

        testBackupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountViewModel.insertHabitWithProgressesList();
            }
        });

        testReplicationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                accountViewModel.LoadHabitWithProgressesList();
            }
        });

        testLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountViewModel.logout();
            }
        });

    }

    private void initData(){

    }

    private void setupMvvm(){

        accountViewModel = ViewModelProviders.of(this, new ViewModelFactory(requireContext()))
                .get(AccountViewModel.class);

        accountViewModel.getIsLoadedSingleLiveEvent().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoaded) {
                if(isLoaded)
                    Toast.makeText(requireContext(), "success", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(requireContext(), "fail", Toast.LENGTH_SHORT).show();
            }
        });

        accountViewModel.getIsInsertedSingleLiveEvent().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isInserted) {
                if (isInserted)
                    Toast.makeText(requireContext(), "success", Toast.LENGTH_SHORT).show();
            }
        });

        accountViewModel.getErrorSingleLiveEvent().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer idRes) {
                Toast.makeText(requireContext(), getString(idRes), Toast.LENGTH_SHORT).show();
            }
        });

        accountViewModel.getSuccessSingleLiveEvent().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer idRes) {
                Toast.makeText(requireContext(), getString(idRes), Toast.LENGTH_SHORT).show();
            }
        });

        accountViewModel.getLogOutSuccessSingleLiveEvent().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer idRes) {
                Toast.makeText(requireContext(), getString(idRes), Toast.LENGTH_SHORT).show();
                FragmentActivity activity = getActivity();
                if (activity instanceof OnLogoutClickListener) {
                    ((OnLogoutClickListener) activity).onClick();
                }
            }
        });

    }

    public interface OnLogoutClickListener {
        void onClick();
    }


}
