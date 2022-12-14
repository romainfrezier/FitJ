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
}
