package fr.univpau.projet_android_kayu_taillandier_remy.listeners;

import android.view.View;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ListenerBtnFlash implements View.OnClickListener{

    private ZXingScannerView mScannerView;

    public ListenerBtnFlash(ZXingScannerView mScannerView) {
        this.mScannerView = mScannerView;
    }

    @Override
    public void onClick(View v) {
        if (this.mScannerView.getFlash()){
            this.mScannerView.setFlash(false);
        }
        else {
            this.mScannerView.setFlash(true);
        }
    }
}
