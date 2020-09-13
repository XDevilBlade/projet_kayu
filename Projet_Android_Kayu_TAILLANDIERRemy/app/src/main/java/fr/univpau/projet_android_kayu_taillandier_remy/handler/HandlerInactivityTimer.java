package fr.univpau.projet_android_kayu_taillandier_remy.handler;

import android.content.Context;
import android.os.Handler;

import fr.univpau.projet_android_kayu_taillandier_remy.runnable.RunnableInactivityTimer;

public class HandlerInactivityTimer extends Handler {

    private RunnableInactivityTimer runnableInactivityTimer;

    public HandlerInactivityTimer(Context context) {
        this.runnableInactivityTimer = new RunnableInactivityTimer(context);
    }

    public void stopHandler(){
        this.removeCallbacks(runnableInactivityTimer);
    }

    public void startHandler() {
        this.postDelayed(runnableInactivityTimer, 6000); //for 6 sec
    }

}
