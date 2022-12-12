package com.fitj.models;

import com.fitj.models.tool.PasswordAuthentication;

public abstract class ModelClient extends Model {

    protected PasswordAuthentication passwordAuthentication = new PasswordAuthentication();
    public ModelClient() {
        super("client");
    }

    public abstract void createClient(String mail, String pseudo, String password, float poids, int taille, String photo) throws Exception;

    public abstract Object getClientAccount(String mail) throws Exception;

    public abstract boolean verifier(Object data, String name) throws Exception;




}
