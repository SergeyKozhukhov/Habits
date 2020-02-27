package ru.sergeykozhukhov.habits.presentation.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.presentation.viewmodel.AccountManagerViewModel;
import ru.sergeykozhukhov.habits.presentation.factory.ViewModelFactory;
import ru.sergeykozhukhov.habits.presentation.view.fragment.AccountFragment;
import ru.sergeykozhukhov.habits.presentation.view.fragment.AuthenticationFragment;
import ru.sergeykozhukhov.habits.presentation.view.fragment.RegistrationFragment;
import ru.sergeykozhukhov.habits.presentation.view.fragment.EnterAccountFragment;

/**
 * Activity - контейнер для Fragments работы с аккаунтом пользователя
 */
public class AccountActivity extends AppCompatActivity implements
        EnterAccountFragment.OnViewsClickListener,
        AccountFragment.OnViewsClickListener,
        AuthenticationFragment.OnIsLoginListener,
        RegistrationFragment.OnRegistrationSuccessListener {

    private AccountManagerViewModel accountManagerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        setupMvvm();
    }

    private void setupMvvm() {
        accountManagerViewModel = new ViewModelProvider(this, new ViewModelFactory(this))
                .get(AccountManagerViewModel.class);

        accountManagerViewModel.getSuccessSingleLiveEvent().observe(this, idRes ->
                replaceFragment(AccountFragment.newInstance(), false));

        accountManagerViewModel.getErrorSingleLiveEvent().observe(this, idRes ->
                replaceFragment(EnterAccountFragment.newInstance(), false));

        accountManagerViewModel.isLogInClient();
    }

    /**
     * Обработчик нажатия на кнопку открытия регистрации
     */
    @Override
    public void onRegistrationClick() {
        replaceFragment(RegistrationFragment.newInstance(), true);
    }

    /**
     * Обработчик нажатия на кнопку входа в существующий аккаунт
     */
    @Override
    public void onAuthenticationClick() {
        replaceFragment(AuthenticationFragment.newInstance(), true);
    }

    /**
     * Обработчик нажатия на кнопку выхода из аккаунта
     */
    @Override
    public void onLogoutClick() {
        this.finish();
    }

    /**
     * Обработчик проверки наличия на устройстве сохраненного аккаунта
     */
    @Override
    public void onIsLogin() {
        accountManagerViewModel.isLogInClient();
    }

    /**
     * Обработчик успешной регистрации пользователя
     */
    @Override
    public void onRegistrationSuccess() {
        replaceFragment(AuthenticationFragment.newInstance(), true);
    }

    private void replaceFragment(@NonNull Fragment newFragment, boolean addToBackStack) {
        if (addToBackStack) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.fragment_fade_enter, R.anim.fragment_fade_exit)
                    .replace(R.id.account_host, newFragment)
                    .addToBackStack(null)
                    .commit();
            return;
        }
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.fragment_fade_enter, R.anim.fragment_fade_exit)
                .replace(R.id.account_host, newFragment)
                .commit();
    }
}
