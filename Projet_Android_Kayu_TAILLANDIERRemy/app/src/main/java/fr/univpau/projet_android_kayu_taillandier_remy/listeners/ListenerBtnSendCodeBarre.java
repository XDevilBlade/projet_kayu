package fr.univpau.projet_android_kayu_taillandier_remy.listeners;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import fr.univpau.projet_android_kayu_taillandier_remy.activities.ResultActivity;
import fr.univpau.projet_android_kayu_taillandier_remy.activities.ScanActivity;
import fr.univpau.projet_android_kayu_taillandier_remy.R;
import fr.univpau.projet_android_kayu_taillandier_remy.singleton.VerifIntentSendedOnce;

public class ListenerBtnSendCodeBarre implements View.OnClickListener {

    private Context context;

    public ListenerBtnSendCodeBarre(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        EditText editTextCodeBarre = (EditText)((Activity)context).findViewById(R.id.editTextCodeBarre);
        if (!editTextCodeBarre.equals("")){
            if (editTextCodeBarre.getText().length()==13){
                if (!VerifIntentSendedOnce.getInstance()){
                    VerifIntentSendedOnce.setInstance(true);
                    Intent intent = new Intent();
                    intent.setClass(context, ResultActivity.class);
                    intent.putExtra("code_barre", editTextCodeBarre.getText().toString());
                    ((Activity)context).startActivityForResult(intent, ScanActivity.REQUEST_CODE);
                }
            }
            else{
                Toast.makeText(context, "Le code-barre que vous avez renseigné ne contient pas 13 chiffres.", Toast.LENGTH_LONG).show();
            }
        }
        else {
            Toast.makeText(context, "Vous n'avez pas de code-barre.", Toast.LENGTH_LONG).show();
        }
    }
}
