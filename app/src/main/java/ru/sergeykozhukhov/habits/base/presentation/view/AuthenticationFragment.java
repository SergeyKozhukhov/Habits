package ru.sergeykozhukhov.habits.base.presentation.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.base.presentation.AuthenticationViewModel;
import ru.sergeykozhukhov.habits.base.presentation.HabitsListViewModel;
import ru.sergeykozhukhov.habits.base.presentation.ViewModelFactory;

public class AuthenticationFragment extends Fragment {

    private HabitsListViewModel habitsListViewModel;
    private AuthenticationViewModel authenticationViewModel;

    private EditText loginAuthEditText;
    private EditText passwordAuthEditText;

    private Button requestAuthButton;

    public static Fragment newInstance() {
        return new AuthenticationFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.auth_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loginAuthEditText = view.findViewById(R.id.login_auth_edit_text);
        passwordAuthEditText = view.findViewById(R.id.password_auth_edit_text);

        requestAuthButton = view.findViewById(R.id.request_auth_button);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initData();
        initViewListeners();
        setupMvvm();
    }

    private void initData() {

    }

    private void initViewListeners() {

        requestAuthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authenticationViewModel.authenticateClient(
                        loginAuthEditText.getText().toString(),
                        passwordAuthEditText.getText().toString()
                );

            }
        });
    }

    private void setupMvvm(){
        authenticationViewModel = ViewModelProviders.of(this, new ViewModelFactory(requireContext()))
                .get(AuthenticationViewModel.class);
        authenticationViewModel.getIsAuthenticatedSingleLiveEvent().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isAuth) {
                if (isAuth)
                    Toast.makeText(requireContext(), "success", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(requireContext(), "fail", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
