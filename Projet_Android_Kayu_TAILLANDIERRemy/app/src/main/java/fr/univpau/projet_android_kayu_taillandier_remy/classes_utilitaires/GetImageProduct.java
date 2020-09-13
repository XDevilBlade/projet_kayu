package fr.univpau.projet_android_kayu_taillandier_remy.classes_utilitaires;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import fr.univpau.projet_android_kayu_taillandier_remy.object.ProduitScanne;

public class GetImageProduct {

    public static ProduitScanne getImageByUrl (ProduitScanne produitScanne){
        try {
            final URL url = new URL(produitScanne.getUrlImage());
            HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            final Bitmap image = BitmapFactory.decodeStream(inputStream);
            produitScanne.setImage(image);

            return produitScanne;

        } catch (MalformedURLException e) {
            return null;
        } catch (IOException e) {
            return null;
        }

    }

}
