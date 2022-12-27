package com.fitj.dao;

import com.fitj.classes.Coach;
import com.fitj.classes.Exercice;
import com.fitj.classes.Seance;
import com.fitj.classes.Sport;
import com.fitj.dao.postgresql.DAOClientPostgreSQL;
import com.fitj.dao.postgresql.DAOExercicePostgreSQL;
import com.fitj.dao.postgresql.DAOSeancePostgreSQL;
import com.fitj.dao.postgresql.DAOSportPostgreSQL;
import kotlin.Pair;
import kotlin.Triple;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe de test de la classe DAOExercicePostgreSQL
 * @see DAOExercicePostgreSQL
 * @author Etienne Tillier, Romain Frezier
 */
public class DAOExercicePostgreSQLTest {

    /**
     * Objets utilisés pour les tests
     */
    private static Exercice exercice;

    /**
     * Objets utilisés pour les tests
     */
    private static Coach coach;

    /**
     * Objets utilisés pour les tests
     */
    private static Exercice exerciceBD;

    /**
     * DAO utilisé pour les tests
     */
    private static DAOExercicePostgreSQL daoExercicePostgreSQL;


    /**
     * Initialisation des objets utilisés pour les tests
     * @throws Exception si la requête SQL échoue
     */
    @BeforeAll
    public static void init() throws Exception {
        daoExercicePostgreSQL = new DAOExercicePostgreSQL();
        coach = new DAOClientPostgreSQL().getAllCoach().get(0);
        exercice = new Exercice(5,"Pompe", "Se mettre au sol et se relever (je suis le test)");
        exerciceBD = daoExercicePostgreSQL.createExercice(exercice.getNom(), exercice.getDescription());
    }

    /**
     * Méthode utilisée après tous les tests pour supprimer l'exercice créé
     * @throws Exception si la requête SQL échoue
     */
    @AfterAll
    public static void clean() throws Exception {
        daoExercicePostgreSQL.supprimerExercice(exerciceBD.getId());

    }

    /**
     * Test de la méthode getExerciceById de la classe DAOExercicePostgreSQL
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testGetExerciceById() throws Exception {
        Exercice exercice = daoExercicePostgreSQL.getExerciceById(exerciceBD.getId());
        Assertions.assertEquals(exerciceBD.getDescription(), exercice.getDescription());
    }

    /**
     * Test de la méthode updateExercice de la classe DAOExercicePostgreSQL
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testExerciceUpdate() throws Exception {
        List<Pair<String, Object>> updateList = new ArrayList<>();
        updateList.add(new Pair<>("nom", "Burpies"));
        exerciceBD = daoExercicePostgreSQL.updateExercice(updateList, exerciceBD.getId());
        Assertions.assertEquals("Burpies", exerciceBD.getNom());
    }

    /**
     * Test de la méthode deleteExercice de la classe DAOExercicePostgreSQL
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testExerciceDelete() throws Exception {
        Exercice exerciceBD1 = daoExercicePostgreSQL.createExercice(exercice.getNom(), exercice.getDescription());
        daoExercicePostgreSQL.supprimerExercice(exerciceBD1.getId());
        Assertions.assertThrows(Exception.class,
                () -> daoExercicePostgreSQL.getExerciceById(exerciceBD1.getId()));
    }

    /**
     * Test de la méthode createExercice de la classe DAOExercicePostgreSQL
     */
    @Test
    public void testExerciceCreate() {
            Assertions.assertEquals(exerciceBD.getNom(), exercice.getNom());
    }

    /**
     * Test de la méthode getAllExercice de la classe DAOExercicePostgreSQL
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testGetAllExercice() throws Exception {
        Exercice exercice1 = daoExercicePostgreSQL.createExercice(exercice.getNom(), exercice.getDescription());
        int nbExerciceBD = daoExercicePostgreSQL.getAllExercice().size();
        daoExercicePostgreSQL.supprimerExercice(exercice1.getId());
        Assertions.assertEquals(nbExerciceBD, daoExercicePostgreSQL.getAllExercice().size() + 1);
    }

    /**
     * Test de la méthode getAllExerciceByCoachId de la classe DAOExercicePostgreSQL
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testGetAllExerciceByCoachId() throws Exception {
        int size = daoExercicePostgreSQL.getAllExerciceByCoachId(coach.getId()).size();
        Exercice exercice1 = daoExercicePostgreSQL.createExercice(exercice.getNom(), exercice.getDescription());
        Sport sport = new DAOSportPostgreSQL().getAllSport().get(0);
        Triple<Exercice, Integer, Integer> exoSeance = new Triple<>(exercice1, 1, 1);
        List<Triple<Exercice, Integer, Integer>> exercices = new ArrayList<>();
        exercices.add(exoSeance);
        Seance seance = new DAOSeancePostgreSQL().createSeance("Test", "Test", 34, coach, sport, exercices);
        int size1 = daoExercicePostgreSQL.getAllExerciceByCoachId(coach.getId()).size();
        new DAOSeancePostgreSQL().supprimerSeance(seance.getId());
        daoExercicePostgreSQL.supprimerExercice(exercice1.getId());
        Assertions.assertEquals(size + 1, size1);
    }
}
