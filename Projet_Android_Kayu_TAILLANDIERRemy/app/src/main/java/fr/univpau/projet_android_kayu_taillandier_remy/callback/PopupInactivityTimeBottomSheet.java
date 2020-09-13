package fr.univpau.projet_android_kayu_taillandier_remy.callback;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.view.View;

import fr.univpau.projet_android_kayu_taillandier_remy.singleton.SingletonHandlerInactivityTimer;

public class PopupInactivityTimeBottomSheet extends BottomSheetBehavior.BottomSheetCallback {


    @Override
    public void onStateChanged(@NonNull View bottomSheet, int newState) {
        switch (newState){
            case BottomSheetBehavior.STATE_HIDDEN :
                SingletonHandlerInactivityTimer.getInstance(bottomSheet.getContext()).stopHandler();
                SingletonHandlerInactivityTimer.getInstance(bottomSheet.getContext()).startHandler();
                break;

            default:
                break;
        }
    }

    @Override
    public void onSlide(@NonNull View bottomSheet, float slideOffset) {

    }
}
