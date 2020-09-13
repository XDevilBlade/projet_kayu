package fr.univpau.projet_android_kayu_taillandier_remy.handler;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;

import org.json.JSONObject;

import fr.univpau.projet_android_kayu_taillandier_remy.runnable.RunnableAsyncTaskTooLong;

public class HandlerAsyncTaskTooLong extends Handler {

    private RunnableAsyncTaskTooLong runnableAsyncTaskTooLong;

    public HandlerAsyncTaskTooLong(AsyncTask<String, String, JSONObject> asyncTask, String message, Context context, ProgressDialog p) {
        runnableAsyncTaskTooLong = new RunnableAsyncTaskTooLong(asyncTask, this, message, context, p);
    }

    public void stopHandler(){
        this.removeCallbacks(runnableAsyncTaskTooLong);
    }

    public void startHandler() {
        this.postDelayed(runnableAsyncTaskTooLong, 10000); //for 10 sec
    }
}
