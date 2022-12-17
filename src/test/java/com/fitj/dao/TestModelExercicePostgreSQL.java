package com.fitj.dao;

import com.fitj.classes.Client;
import com.fitj.classes.Exercice;
import com.fitj.dao.postgresql.DAOClientPostgreSQL;
import com.fitj.dao.postgresql.DAOExercicePostgreSQL;
import com.fitj.enums.Sexe;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class TestModelExercicePostgreSQL {

    private static Exercice exercice;

    private static DAOExercicePostgreSQL daoExercicePostgreSQL;


    @BeforeAll
    public static void init(){
        daoExercicePostgreSQL = new DAOExercicePostgreSQL();
        exercice = new Exercice(5,"Pompe", "Se mettre au sol et se relever (je suis le test)");
    }

    @Test
    public void testGetExerciceById() throws SQLException {
        daoExercicePostgreSQL.createExercice(exercice.getNom(), exercice.getDescription());
        Exercice exerciceBD = daoExercicePostgreSQL.getExerciceById(1);
        Assertions.assertTrue(exerciceBD.getDescription().equals(exercice.getDescription()));
    }

}
