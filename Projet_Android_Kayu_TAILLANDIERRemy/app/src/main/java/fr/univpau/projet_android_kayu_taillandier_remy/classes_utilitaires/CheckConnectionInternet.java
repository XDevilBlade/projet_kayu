package fr.univpau.projet_android_kayu_taillandier_remy.classes_utilitaires;

import android.content.Context;
import android.net.ConnectivityManager;

public class CheckConnectionInternet {

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return (cm.getActiveNetworkInfo() != null) && cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

}
