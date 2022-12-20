package com.fitj.dao;

import com.fitj.classes.*;
import com.fitj.dao.factory.FactoryDAOPostgreSQL;
import com.fitj.dao.postgresql.DAOSeancePostgreSQL;
import com.fitj.enums.Sexe;
import kotlin.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestModelSeancePostgreSQL {

    private static Seance seance;

    private static DAOSeancePostgreSQL daoSeancePostgreSQL;

    private static Coach coach;

    private static Sport sport;

    private static ArrayList<Exercice> listeExercice;


    @BeforeAll
    public static void init() throws Exception {
        daoSeancePostgreSQL = new DAOSeancePostgreSQL();
        coach = new Coach("coach@gmail.com", "elcocho", 100, "dadada", 174, Sexe.HOMME, "test", 44);
        sport = FactoryDAOPostgreSQL.getInstance().getModelSport().getSportByNom("Foot");
        Exercice exercice1 = FactoryDAOPostgreSQL.getInstance().getModelExercice().getExerciceById(1);
        Exercice exercice2 = FactoryDAOPostgreSQL.getInstance().getModelExercice().getExerciceById(2);
        listeExercice = new ArrayList<>();
        listeExercice.add(exercice1);
        listeExercice.add(exercice2);
        seance = new Seance(1,"TestCoach", "Belle séance de sport en plein air", 100, coach,sport,listeExercice);
    }

    @Test
    public void testCreateSeance() throws Exception {
        Seance seanceBD = daoSeancePostgreSQL.createSeance(seance.getNom(),seance.getDescription(),seance.getPrix(),seance.getCoach(),seance.getSport(),seance.getListeExercice());
        daoSeancePostgreSQL.supprimerSeance(seanceBD.getId());
        Assertions.assertTrue(seanceBD.getDescription().equals(seance.getDescription()));
    }


    @Test
    public void testSeanceUpdate() throws Exception {
        Seance seanceBD = daoSeancePostgreSQL.createSeance("Full body","On fait le haut du corps et le bas du corps",150, seance.getCoach(), seance.getSport(), seance.getListeExercice());
        List<Pair<String,Object>> updateList = new ArrayList<>();
        updateList.add(new Pair<>("nom","Haut du corps"));
        seanceBD = daoSeancePostgreSQL.updateSeance(updateList,seanceBD.getId());
        daoSeancePostgreSQL.supprimerSeance(seanceBD.getId());
        Assertions.assertTrue(seanceBD.getNom().equals("Haut du corps"));
    }


    @Test
    public void testSeanceDelete() throws Exception {
        Seance seanceBD = daoSeancePostgreSQL.createSeance("Full body","On fait le haut du corps et le bas du corps",150, seance.getCoach(), seance.getSport(), seance.getListeExercice());
        daoSeancePostgreSQL.supprimerSeance(seanceBD.getId());
        Assertions.assertThrows(SQLException.class,
                () -> daoSeancePostgreSQL.getSeanceById(seanceBD.getId()));
    }

    @Test
    public void testGetAllSeance() throws Exception {
        Seance seance1 = daoSeancePostgreSQL.createSeance("Muscu","test",140, coach, sport, listeExercice);
        int nbSeanceBD = daoSeancePostgreSQL.getAllSeances(new ArrayList<>()).size();
        daoSeancePostgreSQL.supprimerSeance(seance1.getId());
        Assertions.assertTrue(nbSeanceBD == daoSeancePostgreSQL.getAllSeances(new ArrayList<>()).size() + 1);
    }



}
