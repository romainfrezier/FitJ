package com.fitj.dao;

import com.fitj.classes.*;
import com.fitj.dao.factory.FactoryDAOPostgreSQL;
import com.fitj.dao.postgresql.DAOSeancePostgreSQL;
import com.fitj.enums.Sexe;
import kotlin.Pair;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe de test de la DAO SeancePostgreSQL
 * @see DAOSeancePostgreSQL
 * @author Etienne Tillier, Romain Frezier
 */
public class TestDAOSeancePostgreSQL {

    /**
     * Objet utilisé pour les tests
     */
    private static Seance seance;

    /**
     * Objet utilisé pour les tests
     */
    private static Seance seanceBD;

    /**
     * Objet utilisé pour les tests
     */
    private static DAOSeancePostgreSQL daoSeancePostgreSQL;

    /**
     * Objet utilisé pour les tests
     */
    private static Coach coach;

    /**
     * Objet utilisé pour les tests
     */
    private static Sport sport;

    /**
     * Objet utilisé pour les tests
     */
    private static ArrayList<Exercice> listeExercice;

    /**
     * Initialisation des objets utilisés pour les tests
     * @throws Exception si la requête SQL échoue
     */
    @BeforeAll
    public static void init() throws Exception {
        daoSeancePostgreSQL = new DAOSeancePostgreSQL();
        coach = new Coach("coach@gmail.com", "elcocho", 100, "dadada", 174, Sexe.HOMME, "test", 44);
        sport = FactoryDAOPostgreSQL.getInstance().getDAOSport().getSportByNom("Foot");
        Exercice exercice1 = FactoryDAOPostgreSQL.getInstance().getDAOExercice().getExerciceById(1);
        Exercice exercice2 = FactoryDAOPostgreSQL.getInstance().getDAOExercice().getExerciceById(2);
        listeExercice = new ArrayList<>();
        listeExercice.add(exercice1);
        listeExercice.add(exercice2);
        seance = new Seance(1,"TestCoach", "Belle séance de sport en plein air", 100, coach,sport,listeExercice);
        seanceBD = daoSeancePostgreSQL.createSeance(seance.getNom(),seance.getDescription(),seance.getPrix(),seance.getCoach(),seance.getSport(),seance.getListeExercice());
    }

    /**
     * Méthode appelée après tous les tests
     * @throws Exception si la requête SQL échoue
     */
    @AfterAll
    public static void clean() throws Exception {
        daoSeancePostgreSQL.supprimerSeance(seanceBD.getId());
    }

    /**
     * Test de la méthode createSeance
     */
    @Test
    public void testCreateSeance() {
        Assertions.assertEquals(seanceBD.getDescription(), seance.getDescription());
    }

    /**
     * Test de la méthode updateSeance
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testSeanceUpdate() throws Exception {
        List<Pair<String,Object>> updateList = new ArrayList<>();
        updateList.add(new Pair<>("nom","Haut du corps"));
        seanceBD = daoSeancePostgreSQL.updateSeance(updateList,seanceBD.getId());
        Assertions.assertEquals("Haut du corps", seanceBD.getNom());
    }

    /**
     * Test de la méthode supprimerSeance
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testSeanceDelete() throws Exception {
        Seance seance1 = daoSeancePostgreSQL.createSeance("Full body","On fait le haut du corps et le bas du corps",150, seance.getCoach(), seance.getSport(), seance.getListeExercice());
        daoSeancePostgreSQL.supprimerSeance(seance1.getId());
        Assertions.assertThrows(SQLException.class, () -> daoSeancePostgreSQL.getSeanceById(seance1.getId()));
    }

    /**
     * Test de la méthode getAllSeance
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testGetAllSeance() throws Exception {
        Seance seance1 = daoSeancePostgreSQL.createSeance("Muscu","test",140, coach, sport, listeExercice);
        int nbSeanceBD = daoSeancePostgreSQL.getAllSeances().size();
        daoSeancePostgreSQL.supprimerSeance(seance1.getId());
        Assertions.assertEquals(nbSeanceBD, daoSeancePostgreSQL.getAllSeances().size() + 1);
    }

    /**
     * Test de la méthode supprimerExercice
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testSupprimerExerciceSeance() throws Exception {
        Exercice exercice = FactoryDAOPostgreSQL.getInstance().getDAOExercice().createExercice("Exercice", "Super exo");
        daoSeancePostgreSQL.ajouterExercice(exercice,seanceBD.getId());
        int size = daoSeancePostgreSQL.getSeanceById(seanceBD.getId()).getListeExercice().size();
        daoSeancePostgreSQL.supprimerExercice(exercice,seanceBD.getId());
        Assertions.assertEquals(size, daoSeancePostgreSQL.getSeanceById(seanceBD.getId()).getListeExercice().size() + 1);
    }

    /**
     * Test de la méthode ajouterExercice
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testAjouterExerciceSeance() throws Exception {
        Exercice exercice = FactoryDAOPostgreSQL.getInstance().getDAOExercice().createExercice("Exercice", "Super exo");
        daoSeancePostgreSQL.ajouterExercice(exercice,seanceBD.getId());
        int size = daoSeancePostgreSQL.getSeanceById(seanceBD.getId()).getListeExercice().size();
        daoSeancePostgreSQL.supprimerExercice(exercice,seanceBD.getId());
        Assertions.assertEquals(size - 1, daoSeancePostgreSQL.getSeanceById(seanceBD.getId()).getListeExercice().size());
    }




}
