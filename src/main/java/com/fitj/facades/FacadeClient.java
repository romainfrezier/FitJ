package com.fitj.facades;

import com.fitj.Constante;
import com.fitj.controllers.ControllerClient;
import com.fitj.models.ModelClientPostgreSQL;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Facade Client, permet de gérer les clients en applelant le model et le controller
 * @see Facade
 */
public class FacadeClient extends Facade {

    /**
     * Constructeur de la facade, instancie le model et le controller en récupérant le modèle correspondant à la facade
     */
    public FacadeClient(){
        this.model = this.factoryModel.getModelClient();
        this.controller = new ControllerClient();
    }

    /**
     * Méthode permettant de récupérer un client en fonction de son id
     * @param mail String, mail du client
     * @param password String, mot de passe du client
     * @return String, message de retour de la fonction (succès ou erreur) d'après les constantes
     */
    public String connexion(String mail, String password) {
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
