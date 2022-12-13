package com.fitj.models.connexions;

/**
 * Classe parente de toutes les connexionsBD qui permettent de se connecter à une base de donnée
 *
 * Classe abstraite non instanciable
 *
 * @author Etienne Tillier
 */
public abstract class ConnexionBD {


    /**
     * Se connecte à une base de donnée
     * @return un objet qui permet d'y executer des requêtes
     */
    public abstract Object connection();

    /**
     * @return un objet permettant d'exectuer des requêtes sur la base de donnée
     */
    public abstract Object getConnection();
}
