package com.fitj.dao;

import com.fitj.classes.Client;
import com.fitj.classes.Sport;
import com.fitj.dao.postgresql.DAOSportPostgreSQL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class TestModelSportPostgreSQL {

    private static Sport sport;

    private static DAOSportPostgreSQL daoSportPostgreSQL;


    @BeforeAll
    public static void init(){
        daoSportPostgreSQL = new DAOSportPostgreSQL();
        sport = new Sport(1,"Foot");
    }

    @Test
    public void testSportCreate() throws SQLException {
        daoSportPostgreSQL.createSport("Foot");
        Sport sportBD = daoSportPostgreSQL.getSportById(1);
        Assertions.assertTrue(sportBD.getNom().equals("Foot"));
    }



}
