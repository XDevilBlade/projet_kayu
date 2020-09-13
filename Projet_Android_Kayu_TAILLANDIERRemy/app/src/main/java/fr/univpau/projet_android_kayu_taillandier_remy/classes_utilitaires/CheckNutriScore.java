package fr.univpau.projet_android_kayu_taillandier_remy.classes_utilitaires;

public class CheckNutriScore {

    public static String getLetterNutriScore (double nutriScore){
        if (nutriScore >= -15 && nutriScore <= -1) {
            return "a";
        }
        else if (nutriScore >= 0 && nutriScore <= 2) {
            return "b";
        }
        else if (nutriScore >= 3 && nutriScore <= 10) {
            return "c";
        }
        else if (nutriScore >= 11 && nutriScore <= 18) {
            return "d";
        }
        else if (nutriScore >= 19 && nutriScore <= 40) {
            return "e";
        }
        return "f";
    }

}
