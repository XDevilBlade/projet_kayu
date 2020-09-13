package fr.univpau.projet_android_kayu_taillandier_remy.adapters.CursorAdapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import fr.univpau.projet_android_kayu_taillandier_remy.classes_utilitaires.DecodeEncodeImage;
import fr.univpau.projet_android_kayu_taillandier_remy.listeners.ListenerBtnViewProduct;
import fr.univpau.projet_android_kayu_taillandier_remy.object.ProduitScanne;
import fr.univpau.projet_android_kayu_taillandier_remy.R;

public class CursorAdapterProduitScanne extends CursorAdapter {

    public CursorAdapterProduitScanne(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.display_produit_scanne, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ProduitScanne produitScanne = new ProduitScanne();
        produitScanne.setId(cursor.getInt(0));
        produitScanne.setCodeBarre(checkValueStringIsNull(1, cursor));
        produitScanne.setNom(checkValueStringIsNull(2, cursor));
        produitScanne.setUrlImage(checkValueStringIsNull(3, cursor));

        setTextViewProduitScanne(R.id.textViewCodeBarre, checkValueStringIsNull(1, cursor), view);
        setTextViewProduitScanne(R.id.textViewNameProduct, checkValueStringIsNull(2, cursor), view);

        if (cursor.getBlob(4)!= null){
            Bitmap image = DecodeEncodeImage.getImage(cursor.getBlob(4));
            if (image != null){
                ImageView imageViewProduct = (ImageView)view.findViewById(R.id.imageViewProduct);
                imageViewProduct.setImageBitmap(image);
                produitScanne.setImage(image);
            }

        }

        ImageButton buttonView = (ImageButton)view.findViewById(R.id.btnView);
        buttonView.setImageResource(android.R.drawable.ic_menu_view);
        buttonView.setOnClickListener(new ListenerBtnViewProduct(context, produitScanne));
    }

    private String checkValueStringIsNull( int numColumn, Cursor cursor){
        if (cursor.getString(numColumn)!=null){
            if (!cursor.getString(numColumn).equals("")){
                return cursor.getString(numColumn);
            }
            else {
                return "Inconnu";
            }
        }
        else{
            return "Inconnu";
        }
    }

    private void setTextViewProduitScanne(int resource, String value, View view){

        TextView textView = (TextView)view.findViewById(resource);
        textView.setText(value);

    }

}
