package com.fitj.models.postgresql;

import com.fitj.models.ModelRecette;

public class ModelRecettePostgreSQL extends ModelRecette {
    @Override
    public boolean verifier(Object data, String name) throws Exception {
        return false;
    }
}
