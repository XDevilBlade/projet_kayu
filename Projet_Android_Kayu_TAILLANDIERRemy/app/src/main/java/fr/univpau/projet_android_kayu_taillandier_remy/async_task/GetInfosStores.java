package fr.univpau.projet_android_kayu_taillandier_remy.async_task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


import javax.net.ssl.HttpsURLConnection;

import fr.univpau.projet_android_kayu_taillandier_remy.adapters.ArrayAdapters.ArrayAdapterNameStores;
import fr.univpau.projet_android_kayu_taillandier_remy.classes_utilitaires.ManageLayoutsAreaDisplayExtrasInformationsProduct;
import fr.univpau.projet_android_kayu_taillandier_remy.handler.HandlerAsyncTaskTooLong;
import fr.univpau.projet_android_kayu_taillandier_remy.R;


public class GetInfosStores extends AsyncTask<String, String, JSONObject> {

    private Context context;
    private String codeBarre;
    private ProgressDialog p;
    private ArrayList<LinearLayout> linearLayouts;
    private HandlerAsyncTaskTooLong handlerAsyncTaskTooLong;

    public GetInfosStores(Context context, String codeBarre, ArrayList<LinearLayout> linearLayouts){
        this.context = context;
        this.codeBarre = codeBarre;
        this.linearLayouts = linearLayouts;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        p = new ProgressDialog(context);
        p.setMessage("Récupération des informations sur les magasins où l'on peut trouver le produit...");
        p.setIndeterminate(false);
        p.setCancelable(false);
        p.show();
        handlerAsyncTaskTooLong = new HandlerAsyncTaskTooLong(this,
                "La récupération des informations sur les magasins où l'on peut trouver le produit a pris trop de temps à se faire, veuillez réappuyer sur le bouton \"Magasins\" ou vérifier votre connexion internet.",
                context, p);
        handlerAsyncTaskTooLong.startHandler();
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);
            HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();
            if (connection.getResponseCode()==200){
                InputStream inputStream = connection.getInputStream();
                BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
                String inputLine;
                StringBuffer content = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                JSONObject contentJSON = new JSONObject(content.toString());
                JSONObject jsonReturn = new JSONObject();

                if (contentJSON.getInt("status")==1){
                    JSONObject product = contentJSON.getJSONObject("product");
                    addInformationsStores(product, jsonReturn);
                }
                jsonReturn.put("message", "success");
                return jsonReturn;
            }
            return new JSONObject().put("message", "Echec de la récupération des informations des magasins.");
        } catch (MalformedURLException e) {
            try {
                return new JSONObject().put("message", "L'adresse du service web est incorrect.");
            } catch (JSONException ex) {
                return null;
            }
        } catch (IOException e) {
            try {
                return new JSONObject().put("message", "Problème de connexion avec OpenFoodFacts, veuillez vérifier votre connexion internet ou réappuyer sur le bouton \"Magasins\".");
            } catch (JSONException ex) {
                return null;
            }
        } catch (JSONException e) {
            try {
                return new JSONObject().put("message", "Erreur dans le JSON reçu par le service web.");
            } catch (JSONException ex) {
                return null;
            }
        }


    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
        handlerAsyncTaskTooLong.stopHandler();
        if (jsonObject!=null){
            if (p!=null){
                p.dismiss();
            }

            try {
                if (jsonObject.getString("message").equals("success")){
                    ArrayList<String> stores = new Gson().fromJson(jsonObject.getJSONArray("stores_tags").toString(), ArrayList.class);
                    ArrayAdapterNameStores arrayAdapterStores = new ArrayAdapterNameStores(context,R.layout.display_name_store, stores);

                    ListView listViewStores = new ListView(context);
                    listViewStores.setAdapter(arrayAdapterStores);
                    listViewStores.setSelector(context.getResources().getDrawable(R.drawable.transparent));

                    TextView textViewMessageInterrupt = (TextView)((Activity)context).findViewById(R.id.textViewMessageInterrupt);
                    if (textViewMessageInterrupt.getVisibility() == View.VISIBLE){
                        textViewMessageInterrupt.setVisibility(View.GONE);
                    }

                    ManageLayoutsAreaDisplayExtrasInformationsProduct.setVisibilityGoneLinearLayout(linearLayouts.get(3));
                    ManageLayoutsAreaDisplayExtrasInformationsProduct.setVisibilityGoneLinearLayout(linearLayouts.get(2));
                    ManageLayoutsAreaDisplayExtrasInformationsProduct.setVisibilityGoneLinearLayout(linearLayouts.get(1));
                    ManageLayoutsAreaDisplayExtrasInformationsProduct.setVisibilityVisibleLinearLayout(linearLayouts.get(0));

                    linearLayouts.get(0).addView(listViewStores);
                }
                else {
                    displayMessageError(jsonObject.getString("message"));
                }
            } catch (JSONException e) {
                displayMessageError("Erreur dans le JSON reçu par le service web.");
            }
        }
        else {
            if (p!=null){
                p.dismiss();
            }
            displayMessageError("Erreur survenue pendant le processus de récupération des informations des magasins.");

        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    private JSONObject addInformationsStores(JSONObject product, JSONObject jsonReturn){
        try {
            if (product.has("stores_tags")){
                if (product.getJSONArray("stores_tags").length()!=0){
                    jsonReturn.put("stores_tags", product.getJSONArray("stores_tags"));
                } else {
                    jsonReturn.put("stores_tags", new JSONArray().put("Inconnu"));
                }
            } else {
                jsonReturn.put("stores_tags", new JSONArray().put("Inconnu"));
            }
        } catch (JSONException e) {
            displayMessageError("Erreur dans le JSON reçu par le service web.");
        }
        return jsonReturn;
    }

    private void displayMessageError(String message){
        TextView textViewInterrupt = (TextView) ((Activity)context).findViewById(R.id.textViewMessageInterrupt);
        if (textViewInterrupt.getVisibility()== View.GONE){
            textViewInterrupt.setVisibility(View.VISIBLE);
        }
        textViewInterrupt.setText(message);
    }


}
