package com.fitj.dao;

import com.fitj.classes.*;
import com.fitj.dao.factory.FactoryDAOPostgreSQL;
import com.fitj.dao.postgresql.DAOPackPostgreSQL;
import com.fitj.enums.Sexe;
import kotlin.Pair;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class DAOPackPostgreSQLTest {

    /**
     * Objet utilisé pour les tests
     */
    private static ProgrammeNutrition programmeNutrition;

    /**
     * Objet utilisé pour les tests
     */
    private static ProgrammePersonnalise programmePersonnalise;

    /**
     * Objet utilisé pour les tests
     */
    private static ProgrammeSportif programmeSportif;

    /**
     * Objet utilisé pour les tests
     */
    private static Pack packBD;

    /**
     * DAO utilisé pour les tests
     */
    private static DAOPackPostgreSQL daoPackPostgreSQL;

    /**
     * Objet utilisé pour les tests
     */
    private static Coach coach;

    /**
     * Objet utilisé pour les tests
     */
    private static ArrayList<Produit> listeProduits;

    /**
     * Initialisation des objets utilisés pour les tests
     * @throws Exception si la requête SQL échoue
     */
    @BeforeAll
    public static void init() throws Exception {
        daoPackPostgreSQL = new DAOPackPostgreSQL();
        coach = new Coach("coach@gmail.com", "elcocho", 100, "dadada", 174, Sexe.HOMME, "test", 44);
        packBD = daoPackPostgreSQL.createPack("PackTest", "Pack de test", 0, coach);
        programmeNutrition = FactoryDAOPostgreSQL.getInstance().getDAOProgrammeNutrition().getAllProgrammeNutrition().get(0);
        programmePersonnalise = FactoryDAOPostgreSQL.getInstance().getDAOProgrammePersonnalise().getAllProgrammePersonnalise().get(0);
        programmeSportif = FactoryDAOPostgreSQL.getInstance().getDAOProgrammeSportif().getAllProgrammeSportif().get(0);
    }

    /**
     * Méthode utilisée après tous les tests pour supprimer le pack créé
     * @throws Exception si la requête SQL échoue
     */
    @AfterAll
    public static void clean() throws Exception {
        daoPackPostgreSQL.deletePack(packBD.getId());
    }

    /**
     * Test de la méthode createPack de la classe DAOPackPostgreSQL
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testCreatePack() throws Exception {
        List<Pair<String, Object>> updateList = new ArrayList<>();
        updateList.add(new Pair<>("description", "Pack de test"));
        packBD = daoPackPostgreSQL.updatePack(updateList, packBD.getId());
        Assertions.assertEquals(packBD.getDescription(), "Pack de test");
    }

    /**
     * Test de la méthode updatePack de la classe DAOPackPostgreSQL
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testPackUpdate() throws Exception {
        List<Pair<String, Object>> updateList = new ArrayList<>();
        updateList.add(new Pair<>("description", "Pack de test modifié"));
        packBD = daoPackPostgreSQL.updatePack(updateList, packBD.getId());
        Assertions.assertEquals(daoPackPostgreSQL.getPackById(packBD.getId()).getDescription(), packBD.getDescription());
    }

    /**
     * Test de la méthode deletePack de la classe DAOPackPostgreSQL
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testPackDelete() throws Exception {
        Pack newPack = daoPackPostgreSQL.createPack("PackTest", "Pack de test", 0, coach);
        daoPackPostgreSQL.deletePack(newPack.getId());
        Assertions.assertThrows(Exception.class, () -> daoPackPostgreSQL.getPackById(newPack.getId()));
    }

    /**
     * Test de la méthode getAllPack de la classe DAOPackPostgreSQL
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testGetAllPack() throws Exception {
        int nbSeanceBD = daoPackPostgreSQL.getAllPack().size();
        Pack pack1 = daoPackPostgreSQL.createPack("PackTest", "Pack de test", 0, coach);
        int nbSeanceBD2 = daoPackPostgreSQL.getAllPack().size();
        daoPackPostgreSQL.deletePack(pack1.getId());
        Assertions.assertEquals(nbSeanceBD + 1, nbSeanceBD2);
    }

    /**
     * Test de la méthode supprimerProduitPack de la classe DAOPackPostgreSQL
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testSupprimerProduitPack() throws Exception {
        daoPackPostgreSQL.ajouterProduit(programmeNutrition,packBD.getId());
        daoPackPostgreSQL.ajouterProduit(programmeSportif,packBD.getId());
        daoPackPostgreSQL.ajouterProduit(programmePersonnalise,packBD.getId());
        int nbProduit = daoPackPostgreSQL.getPackById(packBD.getId()).getListeProduit().size();
        daoPackPostgreSQL.supprimerProduit(programmeNutrition,packBD.getId());
        daoPackPostgreSQL.supprimerProduit(programmeSportif,packBD.getId());
        daoPackPostgreSQL.supprimerProduit(programmePersonnalise,packBD.getId());
        int nbProduit2 = daoPackPostgreSQL.getPackById(packBD.getId()).getListeProduit().size();
        Assertions.assertEquals(nbProduit2 + 3, nbProduit);
    }

    /**
     * Test de la méthode ajouterProduitPack de la classe DAOPackPostgreSQL
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testAjouterProduitPack() throws Exception {
        Pack pack1 = daoPackPostgreSQL.createPack("PackTest2", "Pack de test", 0, coach);
        int size = pack1.getListeProduit().size();
        daoPackPostgreSQL.ajouterProduit(programmeNutrition,pack1.getId());
        int size2 = daoPackPostgreSQL.getPackById(pack1.getId()).getListeProduit().size();
        Assertions.assertTrue(size2 > size);
    }
}
