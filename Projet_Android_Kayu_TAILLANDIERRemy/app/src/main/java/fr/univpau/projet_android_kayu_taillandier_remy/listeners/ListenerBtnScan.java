package fr.univpau.projet_android_kayu_taillandier_remy.listeners;

import android.content.Intent;
import android.view.View;

import fr.univpau.projet_android_kayu_taillandier_remy.activities.ScanActivity;

public class ListenerBtnScan implements View.OnClickListener{

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.setClass(v.getContext(), ScanActivity.class);
        v.getContext().startActivity(intent);
    }
}


