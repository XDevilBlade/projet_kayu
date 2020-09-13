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


import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import javax.net.ssl.HttpsURLConnection;
import fr.univpau.projet_android_kayu_taillandier_remy.adapters.ArrayAdapters.ArrayAdapterNutriment;
import fr.univpau.projet_android_kayu_taillandier_remy.classes_utilitaires.ManageLayoutsAreaDisplayExtrasInformationsProduct;
import fr.univpau.projet_android_kayu_taillandier_remy.handler.HandlerAsyncTaskTooLong;
import fr.univpau.projet_android_kayu_taillandier_remy.object.Nutriment;
import fr.univpau.projet_android_kayu_taillandier_remy.R;
import fr.univpau.projet_android_kayu_taillandier_remy.singleton.SingletonAllNutrimentsWhichExistInOPF;

public class GetInfosNutriments extends AsyncTask<String, String, JSONObject> {

    private Context context;
    private String codeBarre;
    private ProgressDialog p;
    private ArrayList<LinearLayout> linearLayouts;
    private HandlerAsyncTaskTooLong handlerAsyncTaskTooLong;

    public GetInfosNutriments(Context context, String codeBarre, ArrayList<LinearLayout> linearLayouts){
        this.context = context;
        this.codeBarre = codeBarre;
        this.linearLayouts = linearLayouts;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        p = new ProgressDialog(context);
        p.setMessage("Récupération des informations sur les nutriments qui composent le produit...");
        p.setIndeterminate(false);
        p.setCancelable(false);
        p.show();
        handlerAsyncTaskTooLong = new HandlerAsyncTaskTooLong(this,
                "La récupération des informations sur les nutriments a pris trop de temps à se faire, veuillez réappuyer sur le bouton \"Nutriments\" ou vérifier votre connexion internet.",
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
                    addInformationsNutriments(product, jsonReturn);
                }
                jsonReturn.put("message", "success");
                return jsonReturn;
            }
            return new JSONObject().put("message", "Echec de la récupération des informations des nutriments.");
        } catch (MalformedURLException e) {
            try {
                return new JSONObject().put("message", "L'adresse du service web est incorrect.");
            } catch (JSONException ex) {
                return null;
            }
        } catch (IOException e) {
            try {
                return new JSONObject().put("message", "Problème de connexion avec OpenFoodFacts, veuillez vérifier votre connexion internet ou réappuyer sur le bouton \"Nutriments\".");
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
                    JSONObject nutriments = jsonObject.getJSONObject("nutriments");

                    LinearLayout layoutNutrimentsWithHeader = (LinearLayout) ((Activity) context).getLayoutInflater().inflate(R.layout.display_header_nutriments, linearLayouts.get(2), false);
                    TextView textViewPortion = (TextView)layoutNutrimentsWithHeader.findViewById(R.id.textViewPortion);
                    textViewPortion.setText("Par portion : "+jsonObject.getString("serving_size"));

                    TextView textViewMessageInterrupt = (TextView)((Activity)context).findViewById(R.id.textViewMessageInterrupt);
                    if (textViewMessageInterrupt.getVisibility() == View.VISIBLE){
                        textViewMessageInterrupt.setVisibility(View.GONE);
                    }

                    ManageLayoutsAreaDisplayExtrasInformationsProduct.setVisibilityGoneLinearLayout(linearLayouts.get(1));
                    ManageLayoutsAreaDisplayExtrasInformationsProduct.setVisibilityGoneLinearLayout(linearLayouts.get(0));
                    ManageLayoutsAreaDisplayExtrasInformationsProduct.setVisibilityVisibleLinearLayout(linearLayouts.get(3));
                    ManageLayoutsAreaDisplayExtrasInformationsProduct.setVisibilityVisibleLinearLayout(linearLayouts.get(2));

                    linearLayouts.get(2).addView(layoutNutrimentsWithHeader);

                    if (!nutriments.has("inconnu")){
                        ArrayList<Nutriment> listNutriments = new ArrayList<Nutriment>();
                        listNutriments = extractNutriments(nutriments);
                        //on affiche la liste des nutriments
                        ArrayAdapterNutriment arrayAdapterNutriment = new ArrayAdapterNutriment(context,R.layout.display_nutriment, listNutriments);
                        ListView listViewNutriments = new ListView(context);
                        listViewNutriments.setAdapter(arrayAdapterNutriment);
                        listViewNutriments.setSelector(context.getResources().getDrawable(R.drawable.transparent));
                        linearLayouts.get(3).addView(listViewNutriments);
                    }
                    else {
                        TextView textViewNutriment = new TextView(context);
                        textViewNutriment.setText("Inconnu");
                        linearLayouts.get(3).addView(textViewNutriment);
                    }
                }
                else{
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
            displayMessageError("Erreur survenue pendant le processus de récupération des informations des nutriments.");
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    private JSONObject addInformationsNutriments(JSONObject product, JSONObject jsonReturn){
        try {
            if (product.has("nutriments")){
                if (product.getJSONObject("nutriments").length()!=0){
                    jsonReturn.put("nutriments", product.getJSONObject("nutriments"));
                } else {
                    jsonReturn.put("nutriments", new JSONObject().put("inconnu","inconnu"));
                }
            } else {
                jsonReturn.put("nutriments", new JSONObject().put("inconnu","inconnu"));
            }

        } catch (JSONException e) {
            displayMessageError("Erreur dans le JSON reçu par le service web.");
        }
        try {
            if (product.has("serving_size")){
                if (!product.getString("serving_size").equals("")){
                    jsonReturn.put("serving_size", product.getString("serving_size"));
                } else {
                    jsonReturn.put("serving_size", "Inconnu");
                }
            } else {
                jsonReturn.put("serving_size", "Inconnu");
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

    private ArrayList<Nutriment> extractNutriments(JSONObject nutriments){
        ArrayList<Nutriment> listNutriments = new ArrayList<Nutriment>();
        try {
            //creation d'une map <nutriment,valeur> a partir de la liste des nutriments que Open Food Facts m'a fourni sous la forme d'une chaine de caracteres
            Map<String, Object> result = new Gson().fromJson(nutriments.toString(), Map.class);
            Map<String, Object> sortedMap = new TreeMap<String, Object>(result);
            HashMap<String, JSONObject> jsonObjectHashMap = new HashMap<String, JSONObject>();
            //jsonObjectHashMap : creation d'une HashMap <nutriment, {nutriment_100g : valeur, nutriment_serving : valeur, nutriment_unit : valeur,}
            for (Map.Entry<String,Object> entry : sortedMap.entrySet()){
                String key = entry.getKey();
                if (!jsonObjectHashMap.containsKey(key) && SingletonAllNutrimentsWhichExistInOPF.getInstance().containsKey(key)){
                    jsonObjectHashMap.put(entry.getKey(),new JSONObject());
                }
                if (entry.getKey().contains("_100g")){
                    String keyNutri = entry.getKey().substring(0, key.indexOf("_100g"));
                    JSONObject element = jsonObjectHashMap.get(keyNutri);
                    if (element!=null){
                        element.put(key,entry.getValue());
                        jsonObjectHashMap.put(keyNutri, element);
                    }
                } else if (entry.getKey().contains("_serving")) {
                    String keyNutri = entry.getKey().substring(0, key.indexOf("_serving"));
                    JSONObject element = jsonObjectHashMap.get(keyNutri);
                    if (element!=null){
                        element.put(key,entry.getValue());
                        jsonObjectHashMap.put(keyNutri, element);
                    }
                } else if (entry.getKey().contains("_unit")){
                    String keyNutri = entry.getKey().substring(0, key.indexOf("_unit"));
                    JSONObject element = jsonObjectHashMap.get(keyNutri);
                    if (element!=null){
                        element.put(key,entry.getValue());
                        jsonObjectHashMap.put(keyNutri, element);
                    }
                }
            }
            //creation de ma ArrayList de nutriment
            for (Map.Entry<String,JSONObject> entry2 : jsonObjectHashMap.entrySet()){
                JSONObject nutrimentJson = entry2.getValue();
                String nutriement100g = "inconnu";
                if(nutrimentJson.has(entry2.getKey()+"_100g")){
                    nutriement100g = nutrimentJson.getString(entry2.getKey()+"_100g");
                }
                String nutriementPortion = "inconnu";;
                if(nutrimentJson.has(entry2.getKey()+"_serving")){
                    nutriementPortion = nutrimentJson.getString(entry2.getKey()+"_serving");
                }
                String nutriementUnite = "inconnu";;
                if(nutrimentJson.has(entry2.getKey()+"_unit")){
                    nutriementUnite = nutrimentJson.getString(entry2.getKey()+"_unit");
                }

                Nutriment nutriment = new Nutriment(nutriement100g, nutriementPortion, SingletonAllNutrimentsWhichExistInOPF.getInstance().get(entry2.getKey()), nutriementUnite);
                listNutriments.add(nutriment);
            }
        } catch (JSONException e){
            displayMessageError("Erreur dans le JSON reçu par le service web.");
        }
        return listNutriments;
    }
}
