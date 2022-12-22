package com.fitj.dao;

import com.fitj.classes.Materiel;
import com.fitj.dao.postgresql.DAOMaterielPostgreSQL;
import com.fitj.exceptions.DBProblemException;
import kotlin.Pair;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Class de test de la classe DAOMaterielPostgreSQL
 * @see DAOMaterielPostgreSQL
 * @author Romain Frezier
 */
public class TestDAOMaterielPostgreSQL {

    /**
     * Objet Materiel
     */
    private static Materiel materiel;

    /**
     * Objet utilisé pour les tests
     */
    private static Materiel materielBD;

    /**
     * Objet DAOMaterielPostgreSQL
     */
    private static DAOMaterielPostgreSQL daoMaterielPostgreSQL;

    /**
     * Méthode d'initialisation réalisée avant chaque test
     * @throws Exception si la requête SQL échoue
     */
    @BeforeAll
    public static void init() throws Exception {
        daoMaterielPostgreSQL = new DAOMaterielPostgreSQL();
        materiel = new Materiel(1,"Tapis de course");
        materielBD = daoMaterielPostgreSQL.createMateriel(materiel.getNom());
    }

    /**
     * Méthode effectuée après tous les tests pour supprimer le matériel créé
     * @throws Exception si la requête SQL échoue
     */
    @AfterAll
    public static void clean() throws Exception {
        daoMaterielPostgreSQL.supprimerMateriel(materielBD.getId());
    }

    /**
     * Test de la méthode createMateriel
     */
    @Test
    public void testMaterielCreate() {
        Assertions.assertEquals(materiel.getNom(), materielBD.getNom());
    }

    /**
     * Test de la méthode updateMateriel
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testMaterielUpdate() throws Exception {
        List<Pair<String, Object>> updateList = new ArrayList<>();
        updateList.add(new Pair<>("nom", materiel.getNom() + " motorisé"));
        materielBD = daoMaterielPostgreSQL.updateMateriel(updateList, materielBD.getId());
        Assertions.assertEquals(materiel.getNom() + " motorisé", materielBD.getNom());
    }

    /**
     * Test de la méthode deleteMateriel
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testMaterielDelete() throws Exception {
        Materiel materiel = daoMaterielPostgreSQL.createMateriel("Banc de musculation");
        daoMaterielPostgreSQL.supprimerMateriel(materiel.getId());
        Assertions.assertThrows(DBProblemException.class, () -> daoMaterielPostgreSQL.getMaterielById(materiel.getId()));
    }

    /**
     * Test de la méthode getAllMateriel
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testGetAllMateriel() throws Exception {
        Materiel materielBD2 = daoMaterielPostgreSQL.createMateriel(materiel.getNom() + " motorisé");
        Materiel materielBD3 = daoMaterielPostgreSQL.createMateriel(materiel.getNom() + " électrique");
        int size = daoMaterielPostgreSQL.getAllMateriel().size();
        daoMaterielPostgreSQL.supprimerMateriel(materielBD2.getId());
        daoMaterielPostgreSQL.supprimerMateriel(materielBD3.getId());
        Assertions.assertEquals(size, daoMaterielPostgreSQL.getAllMateriel().size() + 2);
    }

    /**
     * Test de la méthode getMaterielById
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testGetMaterielById() throws Exception {
        Materiel materielBD2 = daoMaterielPostgreSQL.getMaterielById(materielBD.getId());
        Assertions.assertEquals(materielBD.getId(), materielBD2.getId());
    }

    /**
     * Test de la méthode getMaterielByNom
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testGetMaterielByNom() throws Exception {
        Materiel materielBD2 = daoMaterielPostgreSQL.getMaterielByNom(materielBD.getNom());
        Assertions.assertEquals(materielBD.getId(), materielBD2.getId());
    }

    /**
     * Test de la méthode getAllMaterielWhere
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testGetAllMaterielWhere() throws Exception {
        Materiel materielBD2 = daoMaterielPostgreSQL.createMateriel(materiel.getNom() + " motorisé");
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("nom", materiel.getNom() + " motorisé"));
        int size = daoMaterielPostgreSQL.getAllMaterielWhere(whereList).size();
        daoMaterielPostgreSQL.supprimerMateriel(materielBD2.getId());
        Assertions.assertEquals(size, 1);
    }
}
