package fr.univpau.projet_android_kayu_taillandier_remy.classes_utilitaires;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

import fr.univpau.projet_android_kayu_taillandier_remy.R;

public class ManageLayoutsAreaDisplayExtrasInformationsProduct {

    public static ArrayList<LinearLayout> getLayouts(Context context){
        ArrayList<LinearLayout> linearLayouts = new ArrayList<LinearLayout>();
        linearLayouts.add( (LinearLayout)((Activity)context).findViewById(R.id.areaDisplayStores) );
        linearLayouts.add( (LinearLayout)((Activity)context).findViewById(R.id.areaDisplayIngredients) );
        linearLayouts.add( (LinearLayout)((Activity)context).findViewById(R.id.areaDisplayNutrimentsHeader) );
        linearLayouts.add( (LinearLayout)((Activity)context).findViewById(R.id.areaDisplayNutriments) );

        return  linearLayouts;
    }

    public static void setVisibilityGoneLinearLayout(LinearLayout layout){
        layout.setVisibility(View.GONE);
        layout.removeAllViews();
    }

    public static void setVisibilityVisibleLinearLayout(LinearLayout layout){
        layout.setVisibility(View.VISIBLE);
        layout.removeAllViews();
    }

}
