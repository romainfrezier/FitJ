package com.fitj.models.postgresql;

import com.fitj.models.ModelSport;

public class ModelSportPostgreSQL extends ModelSport {
    @Override
    public boolean verifier(Object data, String name) throws Exception {
        return false;
    }
}
