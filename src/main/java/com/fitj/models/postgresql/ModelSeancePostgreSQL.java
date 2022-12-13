package com.fitj.models.postgresql;

import com.fitj.models.ModelSeance;

public class ModelSeancePostgreSQL extends ModelSeance {
    @Override
    public boolean verifier(Object data, String name) throws Exception {
        return false;
    }
}
