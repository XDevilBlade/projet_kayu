package fr.univpau.projet_android_kayu_taillandier_remy.adapters.ArrayAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fr.univpau.projet_android_kayu_taillandier_remy.object.Nutriment;
import fr.univpau.projet_android_kayu_taillandier_remy.R;

public class ArrayAdapterNutriment extends ArrayAdapter<Nutriment> {

    public ArrayAdapterNutriment(@NonNull Context context, int resource, @NonNull ArrayList<Nutriment> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Nutriment nutriment = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.display_nutriment, parent, false);
        }

        TextView textViewCompositionNutritionnelle = (TextView)convertView.findViewById(R.id.textViewCompositionNutritionnelle);
        TextView textViewPour100g = (TextView)convertView.findViewById(R.id.textViewPour100g);
        TextView textViewParPortion = (TextView)convertView.findViewById(R.id.textViewParPortion);

        textViewCompositionNutritionnelle.setText("Composition nutritionnelle : "+nutriment.getTypeNutriment());
        textViewPour100g.setText("Pour 100 g : "+nutriment.getValeur100g()+" "+nutriment.getUnite());
        textViewParPortion.setText("Par portion : "+nutriment.getValeurPortion()+" "+nutriment.getUnite());

        return convertView;
    }
}
