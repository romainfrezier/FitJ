package com.fitj.models.postgresql;

import com.fitj.models.ModelDemande;

public class ModelDemandePostgreSQL extends ModelDemande {
    @Override
    public boolean verifier(Object data, String name) throws Exception {
        return false;
    }
}
