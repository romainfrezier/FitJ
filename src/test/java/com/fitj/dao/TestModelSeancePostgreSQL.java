package com.fitj.dao;

import com.fitj.classes.*;
import com.fitj.dao.factory.FactoryModelPostgreSQL;
import com.fitj.dao.postgresql.DAOSeancePostgreSQL;
import com.fitj.enums.Sexe;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestModelSeancePostgreSQL {

    private static Seance seance;

    private static DAOSeancePostgreSQL daoSeancePostgreSQL;


    @BeforeAll
    public static void init() throws Exception {
        daoSeancePostgreSQL = new DAOSeancePostgreSQL();
        Coach coach = new Coach("coach@gmail.com", "elcocho", 100, "dadada", 174, Sexe.HOMME, "test", 44);
        Sport sport = FactoryModelPostgreSQL.getInstance().getModelSport().getSportById(1);
        Exercice exercice1 = FactoryModelPostgreSQL.getInstance().getModelExercice().getExerciceById(1);
        Exercice exercice2 = FactoryModelPostgreSQL.getInstance().getModelExercice().getExerciceById(2);
        ArrayList<Exercice> listeExercice = new ArrayList<>();
        listeExercice.add(exercice1);
        listeExercice.add(exercice2);
        seance = new Seance(1,"TestCoach", "Belle séance de sport en plein air", 100, coach,sport,listeExercice);
    }

    @Test
    public void testCreateSeance() throws Exception {
        daoSeancePostgreSQL.createSeance(seance.getNom(),seance.getDescription(),seance.getPrix(),seance.getCoach(),seance.getSport(),seance.getListeExercice());
        Seance seanceBD = daoSeancePostgreSQL.getSeanceById(daoSeancePostgreSQL.getIdSeanceByNom(seance.getNom()));
        Assertions.assertTrue(seanceBD.getDescription().equals(seance.getDescription()));
    }
}
