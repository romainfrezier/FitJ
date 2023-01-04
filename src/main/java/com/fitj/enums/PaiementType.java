package com.fitj.enums;

/**
 * Énumération représentant les différents types de paiements.
 *
 * @author Romain Frezier
 */
public enum PaiementType {

    /**
     * Paiement par carte bancaire.
     */
    CARTE_BANCAIRE,

    /**
     * Paiement via PayPal.
     */
    PAYPAL,

    /**
     * Type de paiement inconnu.
     */
    INCONNU;

    /**
     * Retourne l'enum {@link PaiementType} correspondant au type de paiement passé en paramètre sous forme de chaîne de caractères.
     *
     * @param paiementType le type de paiement sous forme de chaîne de caractères.
     * @return l'enum {@link PaiementType} correspondant.
     */
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

    /**
     * Retourne le type de paiement correspondant à l'enum {@link PaiementType} passé en paramètre sous forme de chaîne de caractères.
     *
     * @param paiementType l'enum {@link PaiementType}.
     * @return le type de paiement sous forme de chaîne de caractères.
     */
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

