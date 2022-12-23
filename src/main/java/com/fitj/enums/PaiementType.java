package com.fitj.enums;

public enum PaiementType {
    CARTE_BANCAIRE,
    PAYPAL,
    INCONNU;

    public static PaiementType getPaiementType(String paiementType){
        if (paiementType.equalsIgnoreCase("carte_bancaire")){
            return CARTE_BANCAIRE;
        }
        else if (paiementType.equalsIgnoreCase("paypal")){
            return PAYPAL;
        }
        else {
            return INCONNU;
        }
    }

    public static String getPaiementType(PaiementType paiementType){
        if (paiementType == CARTE_BANCAIRE){
            return "carte_bancaire";
        }
        else if (paiementType == PAYPAL){
            return "paypal";
        }
        else {
            return "inconnu";
        }
    }
}
