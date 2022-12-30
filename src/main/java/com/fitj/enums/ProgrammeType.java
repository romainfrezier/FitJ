package com.fitj.enums;

public enum ProgrammeType {
    FACILE,
    MOYEN,
    DIFFICILE,
    INCONNU;


    public static ProgrammeType getProgrammeType(String programmeType){
        if (programmeType.equalsIgnoreCase("facile")){
            return FACILE;
        }
        else if (programmeType.equalsIgnoreCase("moyen")){
            return MOYEN;
        }
        else if (programmeType.equalsIgnoreCase("difficile")){
            return DIFFICILE;
        }
        else {
            return INCONNU;
        }
    }

    public static String getProgrammeType(ProgrammeType programmeType){
        if (programmeType == FACILE){
            return "facile";
        }
        else if (programmeType == MOYEN){
            return "moyen";
        }
        else if (programmeType == DIFFICILE){
            return "difficile";
        }
        else {
            return "inconnu";
        }
    }

}
