package fr.univpau.projet_android_kayu_taillandier_remy.activities;

import android.Manifest;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import fr.univpau.projet_android_kayu_taillandier_remy.adapters.CursorAdapters.CursorAdapterProduitScanne;
import fr.univpau.projet_android_kayu_taillandier_remy.classes_utilitaires.CheckPermissions;
import fr.univpau.projet_android_kayu_taillandier_remy.contentproviders.ContentProviderProductScanne;
import fr.univpau.projet_android_kayu_taillandier_remy.listeners.ListenerBtnScan;
import fr.univpau.projet_android_kayu_taillandier_remy.R;


public class MainActivity extends AppCompatActivity {
    private Context context = this;
    private CursorAdapterProduitScanne cursorAdapterProduitScanne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // on verifie les droits
        if (!CheckPermissions.checkPermissionWifiState(this)) {
            ActivityCompat.requestPermissions(this, new
                    String[]{Manifest.permission.ACCESS_WIFI_STATE}, 201);
        }

        if (!CheckPermissions.checkPermissionNetworkState(this)) {
            ActivityCompat.requestPermissions(this, new
                    String[]{Manifest.permission.ACCESS_NETWORK_STATE}, 201);
        }

        if (!CheckPermissions.checkPermissionInternet(this)) {
            ActivityCompat.requestPermissions(this, new
                    String[]{Manifest.permission.INTERNET}, 201);
        }

        if (!CheckPermissions.checkPermissionCamera(this)) {
            ActivityCompat.requestPermissions(this, new
                    String[]{Manifest.permission.CAMERA}, 201);
        }

        Button btnScan = (Button) findViewById(R.id.btnScan);

        btnScan.setOnClickListener(new ListenerBtnScan());

        ListView listViewProduitScanne = (ListView)findViewById(R.id.listProduitScanne);
        //creation d'un CursorAdapter qui stockera les informations des produits scannes
        cursorAdapterProduitScanne = new CursorAdapterProduitScanne(this, null);
        //on lie la liste avec le CursorAdapter. Pour pouvoir par la suite afficher les informations des produits stockes dans notre base de donnees SQLite
        listViewProduitScanne.setAdapter(cursorAdapterProduitScanne);
        //suppression de l'effet "wave" qui se produit lorsque l'utilisateur appuie sur l'un des elements de la liste
        listViewProduitScanne.setSelector(this.getResources().getDrawable(R.drawable.transparent));

    }

    @Override
    protected void onResume() {
        super.onResume();
        //mise a jour de la liste des produits scannes
        Cursor cursor = getContentResolver().query(ContentProviderProductScanne.CONTENT_URI, null, null, null, null);
        cursorAdapterProduitScanne.changeCursor(cursor);


    }

    @Override
    protected void onPause() {
        super.onPause();

    }
}
