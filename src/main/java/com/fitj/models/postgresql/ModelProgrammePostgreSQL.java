package com.fitj.models.postgresql;

import com.fitj.models.ModelProgramme;

public class ModelProgrammePostgreSQL extends ModelProgramme {
    @Override
    public boolean verifier(Object data, String name) throws Exception {
        return false;
    }
}
