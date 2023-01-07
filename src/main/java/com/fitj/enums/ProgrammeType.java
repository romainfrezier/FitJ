package com.fitj.enums;
/**
 * Énumération représentant les différents types de programmes.
 *
 * @author Etienne Tillier
 */
public enum ProgrammeType {

    /**
     * Programme facile.
     */
    FACILE,

    /**
     * Programme de niveau moyen.
     */
    MOYEN,

    /**
     * Programme difficile.
     */
    DIFFICILE,

    /**
     * Type de programme inconnu.
     */
    INCONNU;

    /**
     * Retourne l'enum {@link ProgrammeType} correspondant au type de programme passé en paramètre sous forme de chaîne de caractères.
     *
     * @param programmeType String, le type de programme sous forme de chaîne de caractères.
     * @return l'enum {@link ProgrammeType} correspondant.
     */
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

    /**
     * Retourne le type de programme correspondant à l'enum {@link ProgrammeType} passé en paramètre sous forme de chaîne de caractères.
     *
     * @param programmeType ProgrammeType, l'enum {@link ProgrammeType}.
     * @return le type de programme sous forme de chaîne de caractères.
     */
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
