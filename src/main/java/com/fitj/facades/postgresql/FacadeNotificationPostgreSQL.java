package com.fitj.facades.postgresql;

import com.fitj.facades.FacadeNotification;

public class FacadeNotificationPostgreSQL extends FacadeNotification {

    private static FacadeNotificationPostgreSQL instance = null;
    protected FacadeNotificationPostgreSQL(){

    }

    public static FacadeNotificationPostgreSQL getInstance(){
        if (instance == null){
            instance = new FacadeNotificationPostgreSQL();
        }
        return instance;
    }
}
