package com.fitj.models.postgresql;

import com.fitj.models.ModelCommande;

public class ModelCommandePostgreSQL extends ModelCommande {
    @Override
    public boolean verifier(Object data, String name) throws Exception {
        return false;
    }
}
