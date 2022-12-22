package com.fitj.dao;

import com.fitj.classes.Exercice;
import com.fitj.classes.Sport;
import com.fitj.dao.postgresql.DAOExercicePostgreSQL;
import kotlin.Pair;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe de test de la classe DAOExercicePostgreSQL
 * @see DAOExercicePostgreSQL
 * @author Etienne Tillier, Romain Frezier
 */
public class TestDAOExercicePostgreSQL {

    /**
     * Objets utilisés pour les tests
     */
    private static Exercice exercice;

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
        Assertions.assertThrows(SQLException.class,
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

}
