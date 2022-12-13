package com.fitj.models.methodesBD;

import com.fitj.models.connexions.ConnexionBD;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Classe parente de toutes les classes de méthodeBD
 *
 * Classe abstraite non instanciable
 *
 * @author Etienne Tillier
 */
public abstract class MethodesBD {

    /**
     * Une instance de la connexionBD pour exectuer les requêtes
     */
    protected ConnexionBD connexionBD;


    public MethodesBD(ConnexionBD connexionBD){
        this.connexionBD = connexionBD;
    }

    /**
     * Se connecte à la base de donnée
     */
    public void connect(){
        this.connexionBD.connection();
    }

    /**
     * Ferme la connexion à la base de donnée
     * @throws SQLException
     */
    public abstract void close() throws SQLException;

    /**
     * @param connexion
     */
    public void setConnexion(ConnexionBD connexion){
        this.connexionBD = connexion;
    }


    /**
     * @return la connexion à la base de donnée
     */
    public abstract Connection getConnexion();




}
