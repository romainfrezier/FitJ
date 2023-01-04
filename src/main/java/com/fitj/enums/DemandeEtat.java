package com.fitj.enums;

/**
 * Énumération représentant les différents états d'une demande.
 *
 * @author Etienne Tillier
 */
public enum DemandeEtat {

    /**
     * Demande en attente.
     */
    EN_ATTENTE,

    /**
     * Demande en cours de traitement.
     */
    EN_COURS,

    /**
     * Demande validée.
     */
    VALIDE,

    /**
     * Demande annulée.
     */
    ANNULE,

    /**
     * État de la demande inconnu.
     */
    INCONNU;

    /**
     * Retourne l'enum {@link DemandeEtat} correspondant à l'état de la demande passé en paramètre sous forme de chaîne de caractères.
     *
     * @param demandeEtat String, l'état de la demande sous forme de chaîne de caractères.
     * @return l'enum {@link DemandeEtat} correspondant.
     */
    public static DemandeEtat getDemandeEtat(String demandeEtat){
        if (demandeEtat.equalsIgnoreCase("en_attente")){
            return EN_ATTENTE;
        }
        else if (demandeEtat.equalsIgnoreCase("en_cours")){
            return EN_COURS;
        }
        else if (demandeEtat.equalsIgnoreCase("valide")){
            return VALIDE;
        }
        else if (demandeEtat.equalsIgnoreCase("annule")){
            return ANNULE;
        }
        else {
            return INCONNU;
        }
    }

    /**
     * Retourne l'état de la demande correspondant à l'enum {@link DemandeEtat} passé en paramètre sous forme de chaîne de caractères.
     *
     * @param demandeEtat DemandeEtat, l'enum {@link DemandeEtat}.
     * @return l'état de la demande sous forme de chaîne de caractères.
     */
    public static String getDemandeEtat(DemandeEtat demandeEtat){
        if (demandeEtat == EN_ATTENTE){
            return "en_attente";
        }
        else if (demandeEtat == EN_COURS){
            return "en_cours";
        }
        else if (demandeEtat == VALIDE){
            return "valide";
        }
        else if (demandeEtat == ANNULE){
            return "annule";
        }
        else {
            return "inconnu";
        }
    }

}
