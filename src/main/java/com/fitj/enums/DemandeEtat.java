package com.fitj.enums;

public enum DemandeEtat {

    EN_ATTENTE,
    EN_COURS,
    VALIDE,
    ANNULE,
    INCONNU;

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
