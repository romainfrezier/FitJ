package com.fitj.models;

import kotlin.Pair;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

    public abstract class ModelClient extends Model {


    public ModelClient() {
        super("client");
    }

    public abstract void createClient(String mail, String pseudo, String password, float poids, int taille, String photo) throws Exception;

    public abstract Object connexionClient(String mail) throws Exception;

    public abstract boolean verifierEmail(String mail) throws Exception;



}
