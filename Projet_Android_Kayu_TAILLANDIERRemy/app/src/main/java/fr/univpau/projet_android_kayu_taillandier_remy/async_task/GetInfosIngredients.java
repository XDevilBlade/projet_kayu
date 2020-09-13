package fr.univpau.projet_android_kayu_taillandier_remy.async_task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

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

import fr.univpau.projet_android_kayu_taillandier_remy.classes_utilitaires.ManageLayoutsAreaDisplayExtrasInformationsProduct;
import fr.univpau.projet_android_kayu_taillandier_remy.handler.HandlerAsyncTaskTooLong;
import fr.univpau.projet_android_kayu_taillandier_remy.R;

public class GetInfosIngredients extends AsyncTask<String, String, JSONObject> {

    private Context context;
    private String codeBarre;
    private ProgressDialog p;
    private ArrayList<LinearLayout> linearLayouts;
    private HandlerAsyncTaskTooLong handlerAsyncTaskTooLong;

    public GetInfosIngredients(Context context, String codeBarre, ArrayList<LinearLayout> linearLayouts){
        this.context = context;
        this.codeBarre = codeBarre;
        this.linearLayouts = linearLayouts;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        p = new ProgressDialog(context);
        p.setMessage("Récupération des informations sur les ingrédients qui composent le produit...");
        p.setIndeterminate(false);
        p.setCancelable(false);
        p.show();
        //on demarre un thread qui va gerer les problemes de reseau internet
        handlerAsyncTaskTooLong = new HandlerAsyncTaskTooLong(this,
                "La récupération des informations sur les ingrédients a pris trop de temps à se faire, veuillez réappuyer sur le bouton \"Ingrédients\" ou vérifier votre connexion internet.",
                context, p);
        handlerAsyncTaskTooLong.startHandler();
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        try {
            //on requete l'API d'Open Food Facts pour recuperer les informations liees aux ingredients
            URL url = new URL(strings[0]);
            HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();
            if (connection.getResponseCode()==200){
                //recuperation du resultat de la requete
                InputStream inputStream = connection.getInputStream();
                BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
                String inputLine;
                StringBuffer content = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                //stockage du resultat de la requete qui au depart etait au format string maintenant au format JSON
                JSONObject contentJSON = new JSONObject(content.toString());
                //creation et alimentation du json qui sera retourne par la fonction doInBackground
                JSONObject jsonReturn = new JSONObject();
                if (contentJSON.getInt("status")==1){
                    JSONObject product = contentJSON.getJSONObject("product");
                    addInformationsIngredients(product, jsonReturn);
                }
                jsonReturn.put("message", "success");
                return jsonReturn;
            }
            return new JSONObject().put("message", "Echec de la récupération des informations des ingrédients.");
        } catch (MalformedURLException e) {
            try {
                return new JSONObject().put("message", "L'adresse du service web est incorrect.");
            } catch (JSONException ex) {
                return null;
            }
        } catch (IOException e) {
            try {
                return new JSONObject().put("message", "Problème de connexion avec OpenFoodFacts, veuillez vérifier votre connexion internet ou réappuyer sur le bouton \"Ingrédients\".");
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

                    TextView textViewIngredients = new TextView(context);

                    String ingredients = jsonObject.getString("ingredients_text");
                    textViewIngredients.setText(ingredients);

                    TextView textViewMessageInterrupt = (TextView)((Activity)context).findViewById(R.id.textViewMessageInterrupt);
                    if (textViewMessageInterrupt.getVisibility() == View.VISIBLE){
                        textViewMessageInterrupt.setVisibility(View.GONE);
                    }
                    //gestion de l'affichage des informations supplementaires sur le produit scanne(ingredients, magasins, nutriments)
                    //1 layout pour les ingredients - 2 layouts pour les nutriments (un pour l'affichage de la portion et un autre pour la liste des nutriments) - 1 pour les magasins
                    ManageLayoutsAreaDisplayExtrasInformationsProduct.setVisibilityGoneLinearLayout(linearLayouts.get(3));//nutriments
                    ManageLayoutsAreaDisplayExtrasInformationsProduct.setVisibilityGoneLinearLayout(linearLayouts.get(2));//nutriments
                    ManageLayoutsAreaDisplayExtrasInformationsProduct.setVisibilityVisibleLinearLayout(linearLayouts.get(1));//ingredients
                    ManageLayoutsAreaDisplayExtrasInformationsProduct.setVisibilityGoneLinearLayout(linearLayouts.get(0));//magasins

                    linearLayouts.get(1).addView(textViewIngredients);
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
            displayMessageError("Erreur survenue pendant le processus de récupération des informations des ingrédients.");

        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    private JSONObject addInformationsIngredients(JSONObject product, JSONObject jsonReturn){
        try {
            if (product.has("ingredients_text")){
                if (!product.getString("ingredients_text").equals("")){
                    jsonReturn.put("ingredients_text", product.getString("ingredients_text"));
                } else {
                    jsonReturn.put("ingredients_text", "Inconnu");
                }
            } else {
                jsonReturn.put("ingredients_text", "Inconnu");
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
