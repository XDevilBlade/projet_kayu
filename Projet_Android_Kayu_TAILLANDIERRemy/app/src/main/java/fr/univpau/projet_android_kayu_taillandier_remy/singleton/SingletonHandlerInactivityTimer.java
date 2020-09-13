package fr.univpau.projet_android_kayu_taillandier_remy.singleton;

import android.content.Context;

import fr.univpau.projet_android_kayu_taillandier_remy.handler.HandlerInactivityTimer;

public class SingletonHandlerInactivityTimer {
    private static HandlerInactivityTimer handlerInactivityTimer = null;

    private SingletonHandlerInactivityTimer(){

    }

    public static synchronized HandlerInactivityTimer getInstance(Context context){
        if (handlerInactivityTimer == null){
            handlerInactivityTimer = new HandlerInactivityTimer(context);
        }
        return handlerInactivityTimer;
    }

    public static synchronized void setNull(){
        if (handlerInactivityTimer != null){
            handlerInactivityTimer = null;
        }
    }


}
