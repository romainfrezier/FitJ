/**
 * Énumération représentant les différents sexes.
 *
 * @author Etienne Tillier
 */
public enum Sexe {

    /**
     * Sexe masculin.
     */
    HOMME,

    /**
     * Sexe féminin.
     */
    FEMME,

    /**
     * Autre sexe.
     */
    AUTRE,

    /**
     * Sexe inconnu.
     */
    INCONNU;

    /**
     * Retourne l'enum {@link Sexe} correspondant au sexe passé en paramètre sous forme de chaîne de caractères.
     *
     * @param sexe String, le sexe sous forme de chaîne de caractères.
     * @return l'enum {@link Sexe} correspondant.
     */
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

    /**
     * Retourne le sexe correspondant à l'enum {@link Sexe} passé en paramètre sous forme de chaîne de caractères.
     *
     * @param sexe Sexe, l'enum {@link Sexe}.
     * @return le sexe sous forme de chaîne de caractères.
     */
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
