package com.fitj.dao.methodesBD;

import com.fitj.dao.connexions.ConnexionBD;
import com.fitj.exceptions.DBProblemException;

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


    /**
     * Constructeur de la classe MethodesBD
     * @param connexionBD une instance de la connexionBD
     */
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
     * @throws Exception
     */
    public abstract void close() throws Exception;





}
