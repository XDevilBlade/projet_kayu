package fr.univpau.projet_android_kayu_taillandier_remy.object;

import android.graphics.Bitmap;

public class ProduitScanne {

    private int id;
    private String codeBarre;
    private String nom;
    private String urlImage;
    private Bitmap image;

    public  ProduitScanne(){

    }

    public ProduitScanne(int id, String codeBarre, String nom, String urlImage, Bitmap image) {
        this.id = id;
        this.codeBarre = codeBarre;
        this.nom = nom;
        this.urlImage = urlImage;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodeBarre() {
        return codeBarre;
    }

    public void setCodeBarre(String codeBarre) {
        this.codeBarre = codeBarre;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
