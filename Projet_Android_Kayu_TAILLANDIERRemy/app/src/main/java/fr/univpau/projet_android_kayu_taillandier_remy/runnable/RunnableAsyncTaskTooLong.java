package fr.univpau.projet_android_kayu_taillandier_remy.runnable;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONObject;

import fr.univpau.projet_android_kayu_taillandier_remy.classes_utilitaires.InterruptAsyncTask;
import fr.univpau.projet_android_kayu_taillandier_remy.handler.HandlerAsyncTaskTooLong;

public class RunnableAsyncTaskTooLong implements Runnable {

    private AsyncTask<String, String, JSONObject> asyncTask;
    private HandlerAsyncTaskTooLong handler;
    private String message;
    private Context context;
    private ProgressDialog p;

    public RunnableAsyncTaskTooLong(AsyncTask<String, String, JSONObject> asyncTask, HandlerAsyncTaskTooLong handler, String message, Context context, ProgressDialog p) {
        this.asyncTask = asyncTask;
        this.handler = handler;
        this.message = message;
        this.context = context;
        this.p = p;
    }

    @Override
    public void run() {
        InterruptAsyncTask.interrupt(message, asyncTask,this, handler, context, p);
    }
}
