package com.fitj.methodesBD;

import com.fitj.connexions.ConnexionBD;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class MethodesBD {

    protected ConnexionBD connexionBD;

    public MethodesBD(ConnexionBD connexionBD){
        this.connexionBD = connexionBD;
    }

    public void connect(){
        this.connexionBD.connection();
    }

    public abstract void close() throws SQLException;

    public void setConnexion(ConnexionBD connexion){
        this.connexionBD = connexion;
    }

    public abstract Connection getConnexion();




}
