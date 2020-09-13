package fr.univpau.projet_android_kayu_taillandier_remy.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.util.ArrayList;


import fr.univpau.projet_android_kayu_taillandier_remy.listeners.ListenerBtnFlash;
import fr.univpau.projet_android_kayu_taillandier_remy.listeners.ListenerBtnSendCodeBarre;
import fr.univpau.projet_android_kayu_taillandier_remy.R;
import fr.univpau.projet_android_kayu_taillandier_remy.singleton.SingletonBottomSheetBehaviorInactivityTime;
import fr.univpau.projet_android_kayu_taillandier_remy.singleton.SingletonHandlerInactivityTimer;
import fr.univpau.projet_android_kayu_taillandier_remy.singleton.VerifIntentSendedOnce;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    private ZXingScannerView mScannerView = null;
    private LinearLayout layoutPopup = null;
    public final static int REQUEST_CODE = 2;



    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_scan);

        Button buttonFlash = (Button)findViewById(R.id.btnFlash);

        Button buttonSendCodeBarre = (Button)findViewById(R.id.buttonValiderCodeBarre);
        buttonSendCodeBarre.setOnClickListener(new ListenerBtnSendCodeBarre(this));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            // une vue dans laquelle il y a le rendu de la camera
            mScannerView = new ZXingScannerView(this);

            layoutPopup = (LinearLayout)findViewById(R.id.popupInactivityTime);
            layoutPopup.setVisibility(View.GONE);

            if (this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_AUTOFOCUS)){
                mScannerView.setAutoFocus(true);
            }

            //tableau dans lequel on va stocker les différents formats de code-barre que la camera pourra detecter
            ArrayList<BarcodeFormat> barcodeFormats = new ArrayList<BarcodeFormat>();
            barcodeFormats.add(BarcodeFormat.EAN_13);
            mScannerView.setFormats(barcodeFormats);

            LinearLayout layoutCamera = (LinearLayout)findViewById(R.id.areaCamera);
            //on ajoute la vue de la camera a un layout
            layoutCamera.addView(mScannerView);

            //si le telephone portable possede la fonction lampe torche, on affiche le bouton et on lie un evenement dessus pour permettre a l'utilisateur d'allumer la lampe torche
            if(this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)){
                buttonFlash.setOnClickListener(new ListenerBtnFlash(mScannerView));
            }
            else {
                buttonFlash.setVisibility(View.GONE);
                Toast.makeText(this, "Vous n'avez pas la fonctionnalité lampe torche sur votre téléphone", Toast.LENGTH_LONG).show();
            }

        } else {
            buttonFlash.setVisibility(View.GONE);
            Toast.makeText(this, "Vous n'avez pas donner la permission à l'application d'utiliser la caméra", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onResume() {

        if (mScannerView != null){
            mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
            mScannerView.startCamera();          // Start camera on resume
            SingletonHandlerInactivityTimer.getInstance(this).startHandler();

        }
        super.onResume();
    }

    @Override
    public void onPause() {
        SingletonHandlerInactivityTimer.getInstance(this).stopHandler();
        if (mScannerView != null){
            mScannerView.stopCamera();
        }
        super.onPause();
                   // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {

        if (!rawResult.getText().equals("")){
            if (!VerifIntentSendedOnce.getInstance()){
                VerifIntentSendedOnce.setInstance(true);
                Intent intent = new Intent();
                intent.setClass(this, ResultActivity.class);
                intent.putExtra("code_barre", rawResult.getText());
                startActivityForResult(intent,REQUEST_CODE);
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE){
            if (resultCode == ResultActivity.RESULT_CODE){
                VerifIntentSendedOnce.setInstance(false);
            }
        }

    }

    @Override
    protected void onDestroy() {
        SingletonBottomSheetBehaviorInactivityTime.setNull();
        SingletonHandlerInactivityTimer.setNull();
        super.onDestroy();
    }
}