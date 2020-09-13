package fr.univpau.projet_android_kayu_taillandier_remy.sqlitehelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import fr.univpau.projet_android_kayu_taillandier_remy.classes_utilitaires.DecodeEncodeImage;
import fr.univpau.projet_android_kayu_taillandier_remy.object.ProduitScanne;

public class ProduitScanneSQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_PRODUIT_SCANNE = "Produit_Scanne";
    public static final String COLUMN_PRODUIT_SCANNE__ID = "_id";
    public static final String COLUMN_PRODUIT_SCANNE_CODE_BARRE = "code_barre";
    public static final String COLUMN_PRODUIT_SCANNE_NOM = "nom";
    public static final String COLUMN_PRODUIT_SCANNE_URL_IMAGE = "url_image";
    public static final String COLUMN_PRODUIT_SCANNE_IMAGE = "image";

    // You may use your own database name
    private static final String DATABASE_NAME = "myDatabase.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "CREATE TABLE "
            + TABLE_PRODUIT_SCANNE + "("  + COLUMN_PRODUIT_SCANNE__ID + " INTEGER , "
            + COLUMN_PRODUIT_SCANNE_CODE_BARRE + " TEXT , "
            + COLUMN_PRODUIT_SCANNE_NOM + " TEXT , "
            + COLUMN_PRODUIT_SCANNE_URL_IMAGE + " TEXT , "
            + COLUMN_PRODUIT_SCANNE_IMAGE + " BLOB ); "


            ;
    public ProduitScanneSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(ProduitScanneSQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUIT_SCANNE);
        onCreate(db);
    }

    public static ContentValues getContentValuesProduitScanne(ProduitScanne produitScanne){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PRODUIT_SCANNE__ID, produitScanne.getId());
        contentValues.put(COLUMN_PRODUIT_SCANNE_CODE_BARRE, produitScanne.getCodeBarre());
        contentValues.put(COLUMN_PRODUIT_SCANNE_NOM, produitScanne.getNom());
        contentValues.put(COLUMN_PRODUIT_SCANNE_URL_IMAGE, produitScanne.getUrlImage());
        String[] partUrlImage = produitScanne.getUrlImage().split("\\.");

        String extensionFileImage = partUrlImage[partUrlImage.length-1];
        if (extensionFileImage.equals("jpg")||extensionFileImage.equals("JPG")){
            contentValues.put(COLUMN_PRODUIT_SCANNE_IMAGE, DecodeEncodeImage.getBytesImageJPEG(produitScanne.getImage()));
        }
        else if (extensionFileImage.equals("png")||extensionFileImage.equals("PNG")){
            contentValues.put(COLUMN_PRODUIT_SCANNE_IMAGE, DecodeEncodeImage.getBytesImagePNG(produitScanne.getImage()));
        }


        return contentValues;
    }

}
