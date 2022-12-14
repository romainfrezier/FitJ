package com.fitj.enums;

public enum Sexe {
    HOMME,
    FEMME,
    AUTRE,
    INCONNU;


    public static Sexe getSexe(String sexe){
        if (sexe.equalsIgnoreCase("homme")){
            return HOMME;
        }
        else if (sexe.equalsIgnoreCase("femme")){
            return FEMME;
        }
        else if (sexe.equalsIgnoreCase("autre")){
            return AUTRE;
        }
        else {
            return INCONNU;
        }
    }

    public static String getSexe(Sexe sexe){
        if (sexe == HOMME){
            return "homme";
        }
        else if (sexe == FEMME){
            return "femme";
        }
        else if (sexe == AUTRE){
            return "autre";
        }
        else {
            return "inconnu";
        }
    }
}
