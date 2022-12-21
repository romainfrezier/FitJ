package com.fitj.dao;

import com.fitj.classes.Exercice;
import com.fitj.classes.Sport;
import com.fitj.dao.postgresql.DAOExercicePostgreSQL;
import kotlin.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestDAOExercicePostgreSQL {

    private static Exercice exercice;

    private static DAOExercicePostgreSQL daoExercicePostgreSQL;


    @BeforeAll
    public static void init(){
        daoExercicePostgreSQL = new DAOExercicePostgreSQL();
        exercice = new Exercice(5,"Pompe", "Se mettre au sol et se relever (je suis le test)");
    }

    @Test
    public void testGetExerciceById() throws Exception {
        Exercice exerciceBD = daoExercicePostgreSQL.createExercice(exercice.getNom(), exercice.getDescription());
        daoExercicePostgreSQL.supprimerExercice(exerciceBD.getId());
        Assertions.assertTrue(exerciceBD.getDescription().equals(exercice.getDescription()));
    }

    @Test
    public void testExerciceUpdate() throws Exception {
        Exercice exerciceBD = daoExercicePostgreSQL.createExercice("Chaise", "Se mettre dos au mur");
        List<Pair<String,Object>> updateList = new ArrayList<>();
        updateList.add(new Pair<>("nom","Burpies"));
        exerciceBD = daoExercicePostgreSQL.updateExercice(updateList,exerciceBD.getId());
        daoExercicePostgreSQL.supprimerExercice(exerciceBD.getId());
        Assertions.assertTrue(exerciceBD.getNom().equals("Burpies"));
    }

    @Test
    public void testExerciceDelete() throws Exception {
        Exercice exerciceBD = daoExercicePostgreSQL.createExercice("Chaise", "Se mettre dos au mur");
        daoExercicePostgreSQL.supprimerExercice(exerciceBD.getId());
        Assertions.assertThrows(SQLException.class,
                () -> daoExercicePostgreSQL.getExerciceById(exerciceBD.getId()));
    }

    @Test
    public void testGetAllExercice() throws Exception {
        Exercice exercice1 = daoExercicePostgreSQL.createExercice("Traction", "Se lever au dessus de la barre");
        int nbExerciceBD = daoExercicePostgreSQL.getAllExercices(new ArrayList<>()).size();
        daoExercicePostgreSQL.supprimerExercice(exercice1.getId());
        Assertions.assertTrue(nbExerciceBD == daoExercicePostgreSQL.getAllExercices(new ArrayList<>()).size() + 1);
    }

}