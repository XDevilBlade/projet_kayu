package fr.univpau.projet_android_kayu_taillandier_remy.classes_utilitaires;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

import fr.univpau.projet_android_kayu_taillandier_remy.R;
import fr.univpau.projet_android_kayu_taillandier_remy.handler.HandlerAsyncTaskTooLong;
import fr.univpau.projet_android_kayu_taillandier_remy.runnable.RunnableAsyncTaskTooLong;

public class InterruptAsyncTask {

    public static void interrupt(String message, AsyncTask<String, String, JSONObject> asyncTask, RunnableAsyncTaskTooLong runnable, HandlerAsyncTaskTooLong handler,
                                 Context context, ProgressDialog p){

        if (!asyncTask.isCancelled()) {
            asyncTask.cancel(false);
            handler.stopHandler();
            if (p != null) {
                p.dismiss();
            }
            TextView textViewInterrupt = (TextView) ((Activity)context).findViewById(R.id.textViewMessageInterrupt);
            if (textViewInterrupt.getVisibility()== View.GONE){
                textViewInterrupt.setVisibility(View.VISIBLE);
            }
            textViewInterrupt.setText(message);
        }
    }

}
