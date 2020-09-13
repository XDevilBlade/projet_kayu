package fr.univpau.projet_android_kayu_taillandier_remy.async_task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

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


import fr.univpau.projet_android_kayu_taillandier_remy.classes_utilitaires.CheckNutriScore;
import fr.univpau.projet_android_kayu_taillandier_remy.classes_utilitaires.GetImageProduct;
import fr.univpau.projet_android_kayu_taillandier_remy.classes_utilitaires.ManageLayoutsAreaDisplayExtrasInformationsProduct;
import fr.univpau.projet_android_kayu_taillandier_remy.contentproviders.ContentProviderProductScanne;
import fr.univpau.projet_android_kayu_taillandier_remy.handler.HandlerAsyncTaskTooLong;
import fr.univpau.projet_android_kayu_taillandier_remy.listeners.ListenerBtnIngredients;
import fr.univpau.projet_android_kayu_taillandier_remy.listeners.ListenerBtnNutriments;
import fr.univpau.projet_android_kayu_taillandier_remy.listeners.ListenerBtnStores;
import fr.univpau.projet_android_kayu_taillandier_remy.object.ProduitScanne;
import fr.univpau.projet_android_kayu_taillandier_remy.R;
import fr.univpau.projet_android_kayu_taillandier_remy.sqlitehelper.ProduitScanneSQLiteHelper;


public class GetInfosProduit  extends AsyncTask<String, String, JSONObject> {

    private Context context;
    private String codeBarre;
    private ProgressDialog p;
    private HandlerAsyncTaskTooLong handlerAsyncTaskTooLong;

    public GetInfosProduit(Context context, String codeBarre){
        this.context = context;
        this.codeBarre = codeBarre;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        p = new ProgressDialog(context);
        p.setMessage("Récupération des informations du produit...");
        p.setIndeterminate(false);
        p.setCancelable(false);
        p.show();
        handlerAsyncTaskTooLong = new HandlerAsyncTaskTooLong(this,
                "La récupération des informations du produit a pris trop de temps à se faire, veuillez re scanner votre produit ou vérifier votre connexion internet.",
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
                jsonReturn.put("status_verbose", contentJSON.getString("status_verbose"));
                jsonReturn.put("status", Integer.toString(contentJSON.getInt("status")));
                if (contentJSON.getInt("status")==1){
                    JSONObject product = contentJSON.getJSONObject("product");
                    addInformationsProduct(product, jsonReturn);
                }
                jsonReturn.put("message", "success");
                return jsonReturn;
            }
            return new JSONObject().put("message", "Echec de la récupération des informations du produit.");
        } catch (MalformedURLException e) {
            try {
                return new JSONObject().put("message", "L'adresse du service web est incorrect.");
            } catch (JSONException ex) {
                return null;
            }
        } catch (IOException e) {
            try {
                return new JSONObject().put("message", "Problème de connexion avec OpenFoodFacts, veuillez vérifier votre connexion internet ou re scanner votre produit.");
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
                    //si le produit a ete trouve
                    if (jsonObject.getString("status").equals("1")){
                        ((Activity)context).setContentView(R.layout.activity_result);

                        ContentResolver resolver = context.getContentResolver();
                        Cursor cursor = resolver.query(ContentProviderProductScanne.CONTENT_URI,null, null, null, null);
                        int lastProduitScanneId = 0;
                        //recuperation de la derniere cle primaire
                        if (cursor.getCount()!= 0){
                            cursor.moveToLast();
                            lastProduitScanneId = cursor.getInt(0)+1;
                        }
                        ProduitScanne produitScanne = new ProduitScanne(lastProduitScanneId, codeBarre, jsonObject.getString("product_name"), null, null);
                        //gestion de l'image du produit
                        if(jsonObject.has("image")){
                            produitScanne.setUrlImage(jsonObject.getString("image_url"));
                            ImageView imageViewProduct = (ImageView)((Activity)context).findViewById(R.id.imageViewProduct);
                            Bitmap imageProduct = (Bitmap)jsonObject.get("image") ;
                            produitScanne.setImage(imageProduct);
                            imageViewProduct.setImageBitmap(imageProduct);
                            int width = 300;
                            int height = 300;
                            LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width,height);
                            parms.setMargins(20,20,20,20);
                            imageViewProduct.setLayoutParams(parms);
                        }
                        else {
                            ((Activity)context).setContentView(R.layout.activity_result_without_picture);
                        }
                        //insertion du produit scanne dans la base de donnees
                        resolver.insert(ContentProviderProductScanne.CONTENT_URI, ProduitScanneSQLiteHelper.getContentValuesProduitScanne(produitScanne));
                        updateChampProduct(context, jsonObject);

                        ArrayList<LinearLayout> linearLayouts = ManageLayoutsAreaDisplayExtrasInformationsProduct.getLayouts(context);

                        //on met un evenement sur chaque bouton (ingredients, nutrimens, magasins)
                        //linearLayouts - ArrayList (2 layouts pour les nutriments / 1 pour les ingredients / 1 pour les magasins)
                        bindButtons(context, linearLayouts);

                    }
                    else {
                        ((Activity)context).setContentView(R.layout.activity_result_product_not_found);
                        updateChampsProduct(R.id.textViewCodeBarre,codeBarre,context);
                        updateChampsProduct(R.id.textViewStatusVerbose,jsonObject.getString("status_verbose"),context);
                    }
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
            displayMessageError("Erreur survenue pendant le processus de récupération des informations du produit.");
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    private JSONObject addInformationsProduct(JSONObject product, JSONObject jsonReturn){
        try {
            checkValueExist(product, jsonReturn, "brands");
            checkValueExist(product, jsonReturn, "product_name");
            checkValueExist(product, jsonReturn, "quantity");
            if (product.has("nutriscore_score")){
                jsonReturn.put("nutriscore_score", String.valueOf(product.getDouble("nutriscore_score")));
            }
            else {
                jsonReturn.put("nutriscore_score", "Inconnu");
            }
            if (product.has("image_url")){
                if (!product.getString("image_url").equals("")){
                    String urlStr = product.getString("image_url");
                    ProduitScanne produitScanne = new ProduitScanne();
                    produitScanne.setUrlImage(urlStr);
                    if (GetImageProduct.getImageByUrl(produitScanne)!= null){
                        jsonReturn.put("image", produitScanne.getImage());
                        jsonReturn.put("image_url", product.getString("image_url"));
                    }
                }
            }
            else {
                String urlStr = "https://i.pinimg.com/736x/97/b9/62/97b962f32f8c886cce0387a008fda206.jpg";
                ProduitScanne produitScanne = new ProduitScanne();
                produitScanne.setUrlImage(urlStr);
                if (GetImageProduct.getImageByUrl(produitScanne)!= null){
                    jsonReturn.put("image", produitScanne.getImage());
                    jsonReturn.put("image_url", urlStr);
                }
            }
            return jsonReturn;
        } catch (JSONException e) {
            return jsonReturn;
        }
    }


    private void updateChampsProduct(int resource, String value, Context context){
        TextView textView = (TextView)((Activity)context).findViewById(resource);
        textView.setText(value);
    }

    public JSONObject checkValueExist(JSONObject product, JSONObject jsonReturn, String nameChamp){
        try {
            if (product.has(nameChamp)){
                if (!product.getString(nameChamp).equals("")){
                    jsonReturn.put(nameChamp, product.getString(nameChamp));
                } else {
                    jsonReturn.put(nameChamp, "Inconnu");
                }
            } else {
                jsonReturn.put(nameChamp, "Inconnu");
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

    private void updateChampProduct(Context context, JSONObject jsonObject){
        try{
            updateChampsProduct(R.id.textViewCodeBarre, codeBarre, context);
            updateChampsProduct(R.id.textViewBrand, jsonObject.getString("brands"), context);
            updateChampsProduct(R.id.textViewNameProduct, jsonObject.getString("product_name"), context);
            updateChampsProduct(R.id.textViewQuantity, jsonObject.getString("quantity"), context);
            if (jsonObject.getString("nutriscore_score").equals("Inconnu")){
                TextView textViewInconnuNutriScore = (TextView)((Activity)context).findViewById(R.id.textViewInconnueNutriScore);
                textViewInconnuNutriScore.setText("Inconnu");
            }
            else {
                Bitmap nutriScoreImage = null;
                switch (CheckNutriScore.getLetterNutriScore(Double.parseDouble(jsonObject.getString("nutriscore_score")))){
                    case "a":
                        nutriScoreImage = BitmapFactory.decodeResource(context.getResources(),
                                R.drawable.nutriscore_a);
                        break;

                    case "b":
                        nutriScoreImage = BitmapFactory.decodeResource(context.getResources(),
                                R.drawable.nutriscore_b);
                        break;

                    case "c":
                        nutriScoreImage = BitmapFactory.decodeResource(context.getResources(),
                                R.drawable.nutriscore_c);
                        break;

                    case "d":
                        nutriScoreImage = BitmapFactory.decodeResource(context.getResources(),
                                R.drawable.nutriscore_d);
                        break;

                    case "e":
                        nutriScoreImage = BitmapFactory.decodeResource(context.getResources(),
                                R.drawable.nutriscore_e);
                        break;

                    case "f":
                        nutriScoreImage = BitmapFactory.decodeResource(context.getResources(),
                                R.drawable.point_interrogation);
                        break;

                    default:
                        nutriScoreImage = BitmapFactory.decodeResource(context.getResources(),
                                R.drawable.point_interrogation);
                        break;
                }
                ImageView imageViewNutriScore = (ImageView)((Activity)context).findViewById(R.id.imageViewNutriScore);
                imageViewNutriScore.setVisibility(View.VISIBLE);
                imageViewNutriScore.setImageBitmap(nutriScoreImage);
                int width = 300;
                int height = 200;
                LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width,height);
                parms.setMargins(20,10,20,10);
                imageViewNutriScore.setLayoutParams(parms);
            }
        } catch (JSONException e){
            displayMessageError("Erreur dans le JSON reçu par le service web.");
        }

    }

    private void bindButtons (Context context, ArrayList<LinearLayout> linearLayouts){
        Button buttonStores = (Button)((Activity)context).findViewById(R.id.buttonStores);
        buttonStores.setOnClickListener(new ListenerBtnStores(context, codeBarre, linearLayouts));

        Button buttonIngredients = (Button)((Activity)context).findViewById(R.id.buttonIngredients);
        buttonIngredients.setOnClickListener(new ListenerBtnIngredients(context, codeBarre, linearLayouts));

        Button buttonNutriments = (Button)((Activity)context).findViewById(R.id.buttonNutriments);
        buttonNutriments.setOnClickListener(new ListenerBtnNutriments(context, codeBarre, linearLayouts));
    }
}
