package fr.univpau.projet_android_kayu_taillandier_remy.singleton;

import android.support.design.widget.BottomSheetBehavior;
import android.widget.LinearLayout;

import fr.univpau.projet_android_kayu_taillandier_remy.callback.PopupInactivityTimeBottomSheet;

public class SingletonBottomSheetBehaviorInactivityTime {
    private static BottomSheetBehavior bottomSheetBehavior = null;

    private SingletonBottomSheetBehaviorInactivityTime(){

    }

    public static synchronized BottomSheetBehavior getInstance(LinearLayout layoutPopup){
        if (bottomSheetBehavior == null){
            bottomSheetBehavior = BottomSheetBehavior.from(layoutPopup);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

            // set the peek height
            bottomSheetBehavior.setPeekHeight(50);

            // set hideable or not
            bottomSheetBehavior.setHideable(true);

            bottomSheetBehavior.setBottomSheetCallback(new PopupInactivityTimeBottomSheet());
        }
        return bottomSheetBehavior;
    }

    public static synchronized void setNull(){
        if (bottomSheetBehavior != null){
            bottomSheetBehavior = null;
        }
    }


}
