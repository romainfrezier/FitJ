package com.fitj.models.postgresql;

import com.fitj.models.ModelPaiement;

public class ModelPaiementPostgreSQL extends ModelPaiement {
    @Override
    public boolean verifier(Object data, String name) throws Exception {
        return false;
    }
}
