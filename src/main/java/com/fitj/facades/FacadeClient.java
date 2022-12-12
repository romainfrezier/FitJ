package com.fitj.facades;

import com.fitj.Constante;
import com.fitj.controllers.ControllerClient;
import com.fitj.models.ModelClientPostgreSQL;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FacadeClient extends Facade {

    public FacadeClient(){
        this.model = this.factoryModel.getModelClient();
        this.controller = new ControllerClient();
    }

    public String connexion(String mail, String password) throws SQLException {
        try {
            ResultSet compte = ((ModelClientPostgreSQL)this.model).connexionClient(mail);
            if (compte.getString("password").equals(password)){
                return Constante.CONNECTED;
            }
            else {
                return Constante.BAD_PASSWORD;
            }
        }
        catch (SQLException e){
            return Constante.BAD_LOGIN;
        }
    }

    public static void main(String[] args) throws SQLException {
        FacadeClient facadeClient = new FacadeClient();
        System.out.println(facadeClient.connexion("etiennet@gmail.coaaaa","123456"));
    }

}
