package fr.univpau.projet_android_kayu_taillandier_remy.object;

public class Nutriment {
    private String valeur100g;
    private String valeurPortion;
    private String typeNutriment;
    private String unite;

    public Nutriment(String valeur100g, String valeurPortion, String typeNutriment, String unite) {
        this.valeur100g = valeur100g;
        this.valeurPortion = valeurPortion;
        this.typeNutriment = typeNutriment;
        this.unite = unite;
    }

    public String getValeur100g() {
        return valeur100g;
    }

    public void setValeur100g(String valeur100g) {
        this.valeur100g = valeur100g;
    }

    public String getValeurPortion() {
        return valeurPortion;
    }

    public void setValeurPortion(String valeurPortion) {
        this.valeurPortion = valeurPortion;
    }

    public String getTypeNutriment() {
        return typeNutriment;
    }

    public void setTypeNutriment(String typeNutriment) {
        this.typeNutriment = typeNutriment;
    }

    public String getUnite() {
        return unite;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }
}
