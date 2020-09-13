package fr.univpau.projet_android_kayu_taillandier_remy.listeners;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import fr.univpau.projet_android_kayu_taillandier_remy.R;
import fr.univpau.projet_android_kayu_taillandier_remy.async_task.GetInfosStores;
import fr.univpau.projet_android_kayu_taillandier_remy.classes_utilitaires.CheckConnectionInternet;


public class ListenerBtnStores implements View.OnClickListener{

    private Context context;
    private String codeBarre;
    private ArrayList<LinearLayout> linearLayouts;


    public ListenerBtnStores(Context context, String codeBarre, ArrayList<LinearLayout> linearLayouts) {
        this.context = context;
        this.codeBarre = codeBarre;
        this.linearLayouts = linearLayouts;

    }

    @Override
    public void onClick(View v) {
        if (CheckConnectionInternet.isNetworkConnected(context)){
            GetInfosStores getInfosStores = new GetInfosStores(context, codeBarre, linearLayouts);
            getInfosStores.execute("https://world.openfoodfacts.org/api/v0/product/"+codeBarre+".json");
        }
        else {
            TextView textViewInterrupt = (TextView) ((Activity)context).findViewById(R.id.textViewMessageInterrupt);
            if (textViewInterrupt.getVisibility()== View.GONE){
                textViewInterrupt.setVisibility(View.VISIBLE);
            }
            textViewInterrupt.setText("Pour récupérer les informations sur les magasins où l'on peut trouver le produit, vous devez avoir une connexion internet");
        }


    }


}
