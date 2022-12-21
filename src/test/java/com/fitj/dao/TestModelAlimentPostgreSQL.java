package com.fitj.dao;

import com.fitj.classes.Aliment;
import com.fitj.classes.Exercice;
import com.fitj.dao.postgresql.DAOAlimentPostgreSQL;
import com.fitj.dao.postgresql.DAOExercicePostgreSQL;
import kotlin.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestModelAlimentPostgreSQL {

    private static Aliment aliment;

    private static DAOAlimentPostgreSQL daoAlimentPostgreSQL;


    @BeforeAll
    public static void init(){
        daoAlimentPostgreSQL = new DAOAlimentPostgreSQL();
        aliment = new Aliment(5,"Fraise");
    }

    @Test
    public void testGetAlimentById() throws Exception {
        Aliment alimentBD = daoAlimentPostgreSQL.createAliment("Fraise");
        daoAlimentPostgreSQL.supprimerAliment(alimentBD.getId());
        Assertions.assertTrue(alimentBD.getNom().equals("Fraise"));
    }

    @Test
    public void testAlimentUpdate() throws Exception {
        Aliment alimentBD = daoAlimentPostgreSQL.createAliment("Fraise");
        List<Pair<String,Object>> updateList = new ArrayList<>();
        updateList.add(new Pair<>("nom","Chocolat"));
        alimentBD = daoAlimentPostgreSQL.updateAliment(updateList,alimentBD.getId());
        daoAlimentPostgreSQL.supprimerAliment(alimentBD.getId());
        Assertions.assertTrue(alimentBD.getNom().equals("Chocolat"));
    }

    @Test
    public void testAlimentDelete() throws Exception {
        Aliment alimentBD = daoAlimentPostgreSQL.createAliment("Fraise");
        daoAlimentPostgreSQL.supprimerAliment(alimentBD.getId());
        Assertions.assertThrows(SQLException.class,
                () -> daoAlimentPostgreSQL.getAlimentById(alimentBD.getId()));
    }

}
