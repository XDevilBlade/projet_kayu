package fr.univpau.projet_android_kayu_taillandier_remy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import fr.univpau.projet_android_kayu_taillandier_remy.R;
import fr.univpau.projet_android_kayu_taillandier_remy.async_task.GetInfosProduit;
import fr.univpau.projet_android_kayu_taillandier_remy.classes_utilitaires.CheckConnectionInternet;
import fr.univpau.projet_android_kayu_taillandier_remy.classes_utilitaires.CheckPermissions;

public class ResultActivity extends AppCompatActivity {

    public static final int RESULT_CODE = 3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_interrupt);

        if (CheckPermissions.checkPermissionInternet(this) &&
            CheckPermissions.checkPermissionNetworkState(this) &&
            CheckPermissions.checkPermissionWifiState(this)){
            //on verifie si le telephone portable est connecte a internet
            if (CheckConnectionInternet.isNetworkConnected(this)){
                Intent intent = getIntent();
                String codeBarre = intent.getStringExtra("code_barre");
                GetInfosProduit getInfosProduit = new GetInfosProduit(this, codeBarre);
                //on execute la tache asynchrone qui va aller chercher les informations du produit scanne dans la BDD d'Open Food Facts
                getInfosProduit.execute("https://world.openfoodfacts.org/api/v0/product/"+codeBarre+".json");
            }
            else {
                TextView textViewInterrupt = (TextView) this.findViewById(R.id.textViewMessageInterrupt);
                if (textViewInterrupt.getVisibility()== View.GONE){
                    textViewInterrupt.setVisibility(View.VISIBLE);
                }
                textViewInterrupt.setText("Pour récupérer les informations du produit, vous devez avoir une connexion internet.");
            }
        }

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(RESULT_CODE, intent);
        super.onBackPressed();

    }


}
