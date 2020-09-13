package fr.univpau.projet_android_kayu_taillandier_remy.runnable;


import android.app.Activity;
import android.content.Context;

import android.support.design.widget.BottomSheetBehavior;
import android.view.View;
import android.widget.LinearLayout;


import fr.univpau.projet_android_kayu_taillandier_remy.R;
import fr.univpau.projet_android_kayu_taillandier_remy.singleton.SingletonBottomSheetBehaviorInactivityTime;

public class RunnableInactivityTimer implements Runnable {

    private Context context;

    public RunnableInactivityTimer(Context context) {
        this.context = context;
    }

    @Override
    public void run() {
        LinearLayout layoutPopup = (LinearLayout)((Activity)context).findViewById(R.id.popupInactivityTime);
        if (layoutPopup.getVisibility()!=View.VISIBLE){
            layoutPopup.setVisibility(View.VISIBLE);
        }
        SingletonBottomSheetBehaviorInactivityTime.getInstance(layoutPopup).setState(BottomSheetBehavior.STATE_EXPANDED);
    }


}
