package fr.univpau.projet_android_kayu_taillandier_remy.listeners;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import fr.univpau.projet_android_kayu_taillandier_remy.activities.ResultActivity;
import fr.univpau.projet_android_kayu_taillandier_remy.classes_utilitaires.CheckConnectionInternet;
import fr.univpau.projet_android_kayu_taillandier_remy.object.ProduitScanne;

public class ListenerBtnViewProduct implements View.OnClickListener {

    private Context context;
    private ProduitScanne produitScanne;

    public ListenerBtnViewProduct(Context context, ProduitScanne produitScanne) {
        this.context = context;
        this.produitScanne = produitScanne;
    }

    @Override
    public void onClick(View v) {
        if (CheckConnectionInternet.isNetworkConnected(context)){
            Intent intent = new Intent();
            intent.setClass(context, ResultActivity.class);
            intent.putExtra("code_barre", produitScanne.getCodeBarre());
            context.startActivity(intent);
        }
        else {
            Toast.makeText(context, "Pour récupérer les informations du produit, vous devez avoir une connexion internet", Toast.LENGTH_LONG).show();
        }

    }
}
