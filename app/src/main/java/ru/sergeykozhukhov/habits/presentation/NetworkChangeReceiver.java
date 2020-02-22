package ru.sergeykozhukhov.habits.presentation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.util.Log;

import androidx.annotation.NonNull;

import ru.sergeykozhukhov.habits.domain.IInreractor.INetworkControllerInteractor;
import ru.sergeykozhukhov.habits.domain.usecase.NetworkControllerInteractor;

// https://codeunplug.com/check-internet-connection-status-using-broadcast-receiver-in-android/
public class NetworkChangeReceiver extends BroadcastReceiver {

    private final INetworkControllerInteractor networkControllerInteractor;

    public NetworkChangeReceiver() {
        this.networkControllerInteractor = NetworkControllerInteractor.getInstance();
    }


    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {

            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkRequest netRequest = new NetworkRequest.Builder()
                    .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                    .addTransportType(NetworkCapabilities.TRANSPORT_WIFI).build();

            if (cm != null) {


                /*NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                if (activeNetwork != null) {
                    boolean isConnected = activeNetwork.isConnectedOrConnecting();
                    networkControllerInteractor.put(isConnected);
                }*/


                NetworkCallback networkCallback = new NetworkCallback();
                cm.registerNetworkCallback(netRequest, networkCallback);
                //networkControllerInteractor.put(cm.isDefaultNetworkActive());


            }
        }

    }

    class NetworkCallback extends ConnectivityManager.NetworkCallback {

        public NetworkCallback() {
            super();
        }

        @Override
        public void onAvailable(@NonNull Network network) {
            // network change is available
            Log.d("inter: ", "on available");
            networkControllerInteractor.put(true);
        }

        @Override
        public void onLost(@NonNull Network network) {
            // network is lost
            Log.d("inter: ", "on lost");
            networkControllerInteractor.put(false);
        }


        @Override
        public void onUnavailable() {
            Log.d("inter: ", "on unavailable");
            networkControllerInteractor.put(false);
        }
    }
}
