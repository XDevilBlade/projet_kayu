package fr.univpau.projet_android_kayu_taillandier_remy.singleton;

public class VerifIntentSendedOnce {

    private static boolean isSendedOnce = false;

    private VerifIntentSendedOnce(){

    }

    public static synchronized boolean getInstance(){
        return isSendedOnce;
    }

    public static synchronized void setInstance(boolean newIsSendedOnce){
        isSendedOnce = newIsSendedOnce;
    }
}
