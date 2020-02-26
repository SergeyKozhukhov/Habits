package ru.sergeykozhukhov.habits.presentation.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.presentation.viewmodel.AuthenticationViewModel;
import ru.sergeykozhukhov.habits.presentation.factory.ViewModelFactory;

public class AuthenticationFragment extends Fragment {

    private AuthenticationViewModel authenticationViewModel;

    private FrameLayout loadingViewFrameLayout;

    private EditText loginAuthEditText;
    private EditText passwordAuthEditText;

    private Button requestAuthButton;

    public static Fragment newInstance() {
        return new AuthenticationFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.authentication_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loginAuthEditText = view.findViewById(R.id.login_auth_edit_text);
        passwordAuthEditText = view.findViewById(R.id.password_auth_edit_text);

        requestAuthButton = view.findViewById(R.id.request_auth_button);

        loadingViewFrameLayout = view.findViewById(R.id.authentication_loading_view_frame_layout);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initViewListeners();
        setupMvvm();
    }

    @Override
    public void onDestroyView() {
        authenticationViewModel.cancelSubscritions();
        super.onDestroyView();
    }

    private void initViewListeners() {

        requestAuthButton.setOnClickListener(v -> authenticationViewModel.authenticateClient(
                loginAuthEditText.getText().toString(),
                passwordAuthEditText.getText().toString()
        ));
    }

    private void setupMvvm() {
        authenticationViewModel = new ViewModelProvider(this, new ViewModelFactory(requireContext())).get(AuthenticationViewModel.class);

        authenticationViewModel.getSuccessSingleLiveEvent().observe(getViewLifecycleOwner(), idRes -> {
            Toast.makeText(requireContext(), getString(idRes), Toast.LENGTH_SHORT).show();
            FragmentActivity activity = getActivity();
            if (activity instanceof OnLoginSuccess) {
                ((OnLoginSuccess) activity).openAccount();
            }
        });
        authenticationViewModel.getErrorSingleLiveEvent().observe(getViewLifecycleOwner(),
                idRes -> Toast.makeText(requireContext(), getString(idRes), Toast.LENGTH_SHORT).show());
        authenticationViewModel.getIsLoadingMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                loadingViewFrameLayout.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            }
        });

    }

    public interface OnLoginSuccess {
        void openAccount();
    }
}
