package ru.sergeykozhukhov.habits.presentation.view.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import ru.sergeykozhukhov.habitData.R;
import ru.sergeykozhukhov.habits.presentation.viewmodel.AccountViewModel;
import ru.sergeykozhukhov.habits.presentation.factory.ViewModelFactory;

/**
 * Fragment для операций доступных в аккаунте (резервное копирования, восстановление информации)
 */
public class AccountFragment extends Fragment {

    private AccountViewModel accountViewModel;

    private FrameLayout loadingFrameLayout;

    private Button backupButton;
    private Button replicationButton;
    private Button logoutButton;

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
        backupButton = view.findViewById(R.id.backup_button);
        replicationButton = view.findViewById(R.id.replication_button);
        logoutButton = view.findViewById(R.id.logout_button);
        loadingFrameLayout = view.findViewById(R.id.account_loading_frame_layout);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initListeners();
        setupMvvm();
    }

    private void initListeners() {
        backupButton.setOnClickListener(v -> new AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.title_backup_alert_dialog))
                .setMessage(getString(R.string.message_backup_alert_dialog))
                .setPositiveButton(getString(R.string.positive_backup_alert_dialog), (dialog, which) -> accountViewModel.backup())
                .setNegativeButton(getString(R.string.negetive_backup_alert_dialog), null)
                .create().show());


        replicationButton.setOnClickListener(v -> new AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.title_replication_alert_dialog))
                .setMessage(getString(R.string.message_replication_alert_dialog))
                .setPositiveButton(getString(R.string.positive_replication_alert_dialog), (dialog, which) -> accountViewModel.replication())
                .setNegativeButton(getString(R.string.negative_replication_alert_dialog), null)
                .create().show());

        logoutButton.setOnClickListener(v -> new AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.title_logout_alert_dialog))
                .setMessage(getString(R.string.message_logout_alert_dialog))
                .setPositiveButton(getString(R.string.positive_logout_alert_dialog), (dialog, which) -> accountViewModel.logout())
                .setNegativeButton(getString(R.string.negative_logout_alert_dialog), null)
                .create().show());
    }

    private void setupMvvm() {
        accountViewModel = new ViewModelProvider(this, new ViewModelFactory(requireContext())).get(AccountViewModel.class);

        accountViewModel.getSuccessSingleLiveEvent().observe(getViewLifecycleOwner(),
                idRes -> Toast.makeText(requireContext(), getString(idRes), Toast.LENGTH_SHORT).show());

        accountViewModel.getErrorSingleLiveEvent().observe(getViewLifecycleOwner(),
                idRes -> Toast.makeText(requireContext(), getString(idRes), Toast.LENGTH_SHORT).show());

        accountViewModel.getLogOutSuccessSingleLiveEvent().observe(getViewLifecycleOwner(), idRes -> {
            Toast.makeText(requireContext(), getString(idRes), Toast.LENGTH_SHORT).show();
            FragmentActivity activity = getActivity();
            if (activity instanceof OnViewsClickListener)
                ((OnViewsClickListener) activity).onLogoutClick();
        });
        accountViewModel.getIsLoadingMutableLiveData().observe(getViewLifecycleOwner(),
                isLoading -> loadingFrameLayout.setVisibility(isLoading ? View.VISIBLE : View.GONE));
    }

    /**
     * Слушатель нажатия на элементы fragment
     */
    public interface OnViewsClickListener {
        /**
         * Обработчик нажатия на кнопку выхода из аккаунта
         */
        void onLogoutClick();
    }
}
