package com.fitj.models.postgresql;

import com.fitj.models.ModelMateriel;

public class ModelMaterielPostgreSQL extends ModelMateriel {
    @Override
    public boolean verifier(Object data, String name) throws Exception {
        return false;
    }
}
