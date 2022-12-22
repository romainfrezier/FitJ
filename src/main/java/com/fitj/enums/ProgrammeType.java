package com.fitj.enums;

public enum ProgrammeType {
    FACILE,
    MOYEN,
    DIFFCILE,
    INCONNU;

    public static ProgrammeType getProgrammeType(String programmeType){
        if (programmeType.equalsIgnoreCase("facile")){
            return FACILE;
        }
        else if (programmeType.equalsIgnoreCase("moyen")){
            return MOYEN;
        }
        else if (programmeType.equalsIgnoreCase("difficile")){
            return DIFFCILE;
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
        else if (programmeType == DIFFCILE){
            return "difficile";
        }
        else {
            return "inconnu";
        }
    }

}
