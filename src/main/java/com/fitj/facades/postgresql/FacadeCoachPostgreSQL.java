package com.fitj.facades.postgresql;

import com.fitj.facades.FacadeCoach;

public class FacadeCoachPostgreSQL extends FacadeCoach {
    private static FacadeCoachPostgreSQL instance = null;
    protected FacadeCoachPostgreSQL(){

    }

    public static FacadeCoachPostgreSQL getInstance(){
        if (instance == null){
            instance = new FacadeCoachPostgreSQL();
        }
        return instance;
    }
}
