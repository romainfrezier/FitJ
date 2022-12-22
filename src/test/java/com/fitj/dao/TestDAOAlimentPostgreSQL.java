package com.fitj.dao;

import com.fitj.classes.Aliment;
import com.fitj.dao.postgresql.DAOAlimentPostgreSQL;
import kotlin.Pair;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe de test de la classe DAOAlimentPostgreSQL
 * @see DAOAlimentPostgreSQL
 * @author Etienne Tillier, Romain Frezier
 */
public class TestDAOAlimentPostgreSQL {

    /**
     * Objets utilisés pour les tests
     */
    private static Aliment aliment;

    /**
     * Objet utilisé pour les tests
     */
    private static Aliment alimentBD;

    /**
     * DAO utilisé pour les tests
     */
    private static DAOAlimentPostgreSQL daoAlimentPostgreSQL;

    /**
     * Initialisation des objets utilisés pour les tests
     * @throws Exception si la requête SQL échoue
     */
    @BeforeAll
    public static void init() throws Exception {
        daoAlimentPostgreSQL = new DAOAlimentPostgreSQL();
        aliment = new Aliment(5,"Fraise");
        alimentBD = daoAlimentPostgreSQL.createAliment(aliment.getNom());
    }

    /**
     * Méthode utilisée après tous les tests pour supprimer l'aliment créé
     * @throws Exception si la requête SQL échoue
     */
    @AfterAll
    public static void clean() throws Exception {
        daoAlimentPostgreSQL.supprimerAliment(alimentBD.getId());
    }

    /**
     * Test de la méthode getAlimentById de la classe DAOAlimentPostgreSQL
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testGetAlimentById() throws Exception {
        Aliment alimentBD1 = daoAlimentPostgreSQL.getAlimentById(alimentBD.getId());
        Assertions.assertEquals(alimentBD1.getId(), alimentBD.getId());
    }

    /**
     * Test de la méthode updateAliment de la classe DAOAlimentPostgreSQL
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testAlimentUpdate() throws Exception {
        List<Pair<String, Object>> updateList = new ArrayList<>();
        updateList.add(new Pair<>("nom", "Chocolat"));
        alimentBD = daoAlimentPostgreSQL.updateAliment(updateList, alimentBD.getId());
        Assertions.assertEquals("Chocolat", alimentBD.getNom());
    }

    /**
     * Test de la méthode createAliment de la classe DAOAlimentPostgreSQL
     */
    @Test
    public void testCreateAliment() {
        Assertions.assertEquals(aliment.getNom(), alimentBD.getNom());
    }

    /**
     * Test de la méthode deleteAliment de la classe DAOAlimentPostgreSQL
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testAlimentDelete() throws Exception {
        Aliment alimentBD = daoAlimentPostgreSQL.createAliment("Framboise");
        daoAlimentPostgreSQL.supprimerAliment(alimentBD.getId());
        Assertions.assertThrows(SQLException.class, () -> daoAlimentPostgreSQL.getAlimentById(alimentBD.getId()));
    }

}
