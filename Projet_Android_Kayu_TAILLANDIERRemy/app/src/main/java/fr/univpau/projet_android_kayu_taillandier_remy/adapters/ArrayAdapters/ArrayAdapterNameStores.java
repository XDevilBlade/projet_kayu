package fr.univpau.projet_android_kayu_taillandier_remy.adapters.ArrayAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import fr.univpau.projet_android_kayu_taillandier_remy.R;

public class ArrayAdapterNameStores extends ArrayAdapter<String> {
    public ArrayAdapterNameStores(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
    }

    @Override
    public int getPosition(@Nullable String store) {
        return super.getPosition(store);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String elementStr = this.getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.display_name_store, parent, false);
        }

        TextView textViewStr = (TextView)convertView.findViewById(R.id.str);
        textViewStr.setText(elementStr);

        return convertView;
    }
}
