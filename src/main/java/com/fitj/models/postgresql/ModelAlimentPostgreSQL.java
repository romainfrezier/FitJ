package com.fitj.models.postgresql;

import com.fitj.models.ModelAliment;

public class ModelAlimentPostgreSQL extends ModelAliment {
    @Override
    public boolean verifier(Object data, String name) throws Exception {
        return false;
    }
}
