package fr.univpau.projet_android_kayu_taillandier_remy.contentproviders;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import fr.univpau.projet_android_kayu_taillandier_remy.sqlitehelper.ProduitScanneSQLiteHelper;

public class ContentProviderProductScanne extends ContentProvider {

    private ProduitScanneSQLiteHelper dbProductScan;
    public static final String AUTHORITY = "fr.univpau.projet_android_kayu_taillandier_remy.contentproviders.ContentProviderProductScanne";
    public static final String RESOURCE = ProduitScanneSQLiteHelper.TABLE_PRODUIT_SCANNE;
    public static final Uri CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/"+RESOURCE);
    public static final String MIME = "vnd.android.cursor.dir/vnd.fr.univpau.projet_android_kayu_taillandier_remy.object.ProduitScanne";

    @Override
    public boolean onCreate() {
        dbProductScan = new ProduitScanneSQLiteHelper(this.getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        assert(this.getContext()!=null);
        SQLiteDatabase database = dbProductScan.getReadableDatabase();
        String[] uriSplit = uri.toString().split("/");
        if (uriSplit[uriSplit.length-1].equals(ProduitScanneSQLiteHelper.TABLE_PRODUIT_SCANNE)){
            return database.query(ProduitScanneSQLiteHelper.TABLE_PRODUIT_SCANNE,projection,null,null,null,null,null);
        } else {
            return database.query(ProduitScanneSQLiteHelper.TABLE_PRODUIT_SCANNE, projection, selection, selectionArgs, null, null, sortOrder);
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return MIME;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        assert(this.getContext()!=null);
        SQLiteDatabase database = dbProductScan.getWritableDatabase();
        if (database.insert(ProduitScanneSQLiteHelper.TABLE_PRODUIT_SCANNE, null, contentValues)!= -1){
            return uri;
        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
