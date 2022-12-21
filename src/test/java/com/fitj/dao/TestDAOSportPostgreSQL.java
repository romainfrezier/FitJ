package com.fitj.dao;

import com.fitj.classes.Sport;
import com.fitj.dao.postgresql.DAOSportPostgreSQL;
import kotlin.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestDAOSportPostgreSQL {

    private static Sport sport;

    private static DAOSportPostgreSQL daoSportPostgreSQL;


    @BeforeAll
    public static void init(){
        daoSportPostgreSQL = new DAOSportPostgreSQL();
        sport = new Sport(1,"Foot");
    }

    @Test
    public void testSportCreate() throws Exception {
        Sport sportBD = daoSportPostgreSQL.createSport("Foot");
        daoSportPostgreSQL.supprimerSport(sportBD.getId());
        Assertions.assertTrue(sportBD.getNom().equals("Foot"));
    }

    @Test
    public void testSportUpdate() throws Exception {
        Sport sportBD = daoSportPostgreSQL.createSport("Muscu");
        List<Pair<String,Object>> updateList = new ArrayList<>();
        updateList.add(new Pair<>("nom","Snowboard"));
        sportBD = daoSportPostgreSQL.updateSport(updateList,sportBD.getId());
        daoSportPostgreSQL.supprimerSport(sportBD.getId());
        Assertions.assertTrue(sportBD.getNom().equals("Snowboard"));
    }

    @Test
    public void testSportDelete() throws Exception {
        Sport sportBD = daoSportPostgreSQL.createSport("Muscu");
        daoSportPostgreSQL.supprimerSport(sportBD.getId());
        Assertions.assertThrows(SQLException.class,
                () -> daoSportPostgreSQL.getSportById(sportBD.getId()));
    }

    @Test
    public void testGetAllSport() throws Exception {
        Sport sportBD1 = daoSportPostgreSQL.createSport("Muscu");
        Sport sportBD2 = daoSportPostgreSQL.createSport("Badminton");
        Sport sportBD3 = daoSportPostgreSQL.createSport("Natation");
        int nbSportBD = daoSportPostgreSQL.getAllSport(new ArrayList<>()).size();
        daoSportPostgreSQL.supprimerSport(sportBD1.getId());
        daoSportPostgreSQL.supprimerSport(sportBD2.getId());
        daoSportPostgreSQL.supprimerSport(sportBD3.getId());
        Assertions.assertTrue(nbSportBD == daoSportPostgreSQL.getAllSport(new ArrayList<>()).size() + 3);
    }




}
