package com.fitj.models;

import kotlin.Pair;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModelClient extends Model{


    public ModelClient(String client) {
        super("client");
    }

    public void createClient(String mail, String pseudo, String password, float poids, int taille, String photo) throws SQLException {
        List<Pair<String,Object>> data = new ArrayList<>();
        data.add(new Pair<>("mail", mail));
        data.add(new Pair<>("pseudo", pseudo));
        data.add(new Pair<>("password", password));
        data.add(new Pair<>("poids", poids));
        data.add(new Pair<>("taille", taille));
        data.add(new Pair<>("photo", photo));
        this.insert(data);
    }

    public ResultSet connexionClient(String mail) throws SQLException {
        ResultSet compte = null;
        try {
            List<Pair<String,Object>> data = new ArrayList<>();
            data.add(new Pair<>("mail", mail));
            compte = this.selectWhere(data);
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        try {
            if (compte.next() == true){
                return compte;
            }
        }
        catch (SQLException e){
            System.out.println("Email non existant");
            throw new SQLException();
        }
        return compte;
    }

    //exception à gérer mieux
    public boolean verifierEmail(String mail) throws SQLException {
        List<Pair<String,Object>> data = new ArrayList<>();
        data.add(new Pair<>("mail", mail));
        ResultSet result = this.selectWhere(data);
        if (result.next() == false){
            return true;
        }
        else return false;

    }




}
