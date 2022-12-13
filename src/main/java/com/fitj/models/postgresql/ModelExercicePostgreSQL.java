package com.fitj.models.postgresql;

import com.fitj.models.ModelExercice;

public class ModelExercicePostgreSQL extends ModelExercice {
    @Override
    public boolean verifier(Object data, String name) throws Exception {
        return false;
    }
}
