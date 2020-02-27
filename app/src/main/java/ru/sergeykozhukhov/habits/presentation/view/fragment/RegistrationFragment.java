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
import androidx.lifecycle.ViewModelProvider;

import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.presentation.viewmodel.RegistrationViewModel;
import ru.sergeykozhukhov.habits.presentation.factory.ViewModelFactory;

/**
 * Fragment для регистрации нового пользователя
 */
public class RegistrationFragment extends Fragment {

    private RegistrationViewModel registrationViewModel;

    private FrameLayout loadingFrameLayout;

    private EditText firsnameRegistrationEditText;
    private EditText lastnameRegistrationEditText;
    private EditText loginRegistrationEditText;
    private EditText passwordRegistrationEditText;
    private EditText passwordConfirmationEditText;

    private Button requestRegistrationButton;

    public static Fragment newInstance() {
        return new RegistrationFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.registration_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firsnameRegistrationEditText = view.findViewById(R.id.firstname_registration_edit_text);
        lastnameRegistrationEditText = view.findViewById(R.id.lastname_registration_edit_text);
        loginRegistrationEditText = view.findViewById(R.id.login_registration_edit_text);
        passwordRegistrationEditText = view.findViewById(R.id.password_registration_edit_text);
        passwordConfirmationEditText = view.findViewById(R.id.password_confirmation_edit_text);

        requestRegistrationButton = view.findViewById(R.id.request_registration_button);

        loadingFrameLayout = view.findViewById(R.id.registration_loading_view_frame_layout);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupMvvm();
        initViewListeners();
    }

    private void initViewListeners() {
        requestRegistrationButton.setOnClickListener(v -> registrationViewModel.registerClient(
                firsnameRegistrationEditText.getText().toString(),
                lastnameRegistrationEditText.getText().toString(),
                loginRegistrationEditText.getText().toString(),
                passwordRegistrationEditText.getText().toString(),
                passwordConfirmationEditText.getText().toString()
        ));
    }

    private void setupMvvm() {
        registrationViewModel = new ViewModelProvider(this, new ViewModelFactory(requireContext())).get(RegistrationViewModel.class);

        registrationViewModel.getSuccessSingleLiveEvent().observe(getViewLifecycleOwner(), idRes -> {
            Toast.makeText(requireContext(), getString(idRes), Toast.LENGTH_SHORT).show();
            FragmentActivity activity = getActivity();
            if (activity instanceof OnRegistrationSuccessListener)
                ((OnRegistrationSuccessListener) activity).onRegistrationSuccess();
        });

        registrationViewModel.getErrorSingleLiveEvent().observe(getViewLifecycleOwner(),
                idRes -> Toast.makeText(requireContext(), getString(idRes), Toast.LENGTH_SHORT).show());

        registrationViewModel.getIsLoadingMutableLiveData().observe(getViewLifecycleOwner(),
                isLoading -> loadingFrameLayout.setVisibility(isLoading ? View.VISIBLE : View.GONE));
    }

    /**
     * Слушатеть успешной регистрации нового пользователя
     */
    public interface OnRegistrationSuccessListener {

        /**
         * Обработчик регистрации нового пользователя
         */
        void onRegistrationSuccess();
    }
}
