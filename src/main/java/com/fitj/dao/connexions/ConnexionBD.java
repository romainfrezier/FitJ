package com.fitj.dao.connexions;

import com.fitj.exceptions.DBProblemException;

/**
 * Classe parente de toutes les connexionsBD qui permettent de se connecter à une base de donnée
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
     * Récupère la connexion à la base de donnée
     * @return Object, un objet permettant d'exectuer des requêtes sur la base de donnée
     * @throws DBProblemException en cas d'erreur
     */
    public abstract Object getConnection() throws DBProblemException;
}