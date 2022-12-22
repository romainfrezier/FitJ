package com.fitj.dao;

import com.fitj.classes.Materiel;
import com.fitj.dao.postgresql.DAOMaterielPostgreSQL;
import com.fitj.exceptions.DBProblemException;
import kotlin.Pair;
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
     * Objet DAOMaterielPostgreSQL
     */
    private static DAOMaterielPostgreSQL daoMaterielPostgreSQL;

    /**
     * Méthode d'initialisation réalisée avant chaque test
     */
    @BeforeAll
    public static void init(){
        daoMaterielPostgreSQL = new DAOMaterielPostgreSQL();
        materiel = new Materiel(1,"Tapis de course");
    }

    /**
     * Test de la méthode createMateriel
     * @throws DBProblemException si la création du matériel ne s'est pas bien passée
     */
    @Test
    public void testMaterielCreate() throws DBProblemException {
        Materiel materielBD = daoMaterielPostgreSQL.createMateriel(materiel.getNom());
        daoMaterielPostgreSQL.supprimerMateriel(materielBD.getId());
        Assertions.assertEquals(materiel.getNom(), materielBD.getNom());
    }

    /**
     * Test de la méthode updateMateriel
     * @throws DBProblemException si la récupération du matériel ne s'est pas bien passée
     */
    @Test
    public void testMaterielUpdate() throws Exception {
        Materiel materielBD = daoMaterielPostgreSQL.createMateriel(materiel.getNom());
        List<Pair<String, Object>> updateList = new ArrayList<>();
        updateList.add(new Pair<>("nom", materiel.getNom() + " motorisé"));
        materielBD = daoMaterielPostgreSQL.updateMateriel(updateList, materielBD.getId());
        daoMaterielPostgreSQL.supprimerMateriel(materielBD.getId());
        Assertions.assertEquals(materiel.getNom() + " motorisé", materielBD.getNom());
    }

    /**
     * Test de la méthode deleteMateriel
     * @throws DBProblemException si la suppression du matériel ne s'est pas bien passée
     */
    @Test
    public void testMaterielDelete() throws DBProblemException {
        Materiel materielBD = daoMaterielPostgreSQL.createMateriel(materiel.getNom());
        daoMaterielPostgreSQL.supprimerMateriel(materielBD.getId());
        Assertions.assertThrows(DBProblemException.class, () -> daoMaterielPostgreSQL.getMaterielById(materielBD.getId()));
    }

    /**
     * Test de la méthode getAllMateriel
     * @throws DBProblemException si la récupération du matériel ne s'est pas bien passée
     */
    @Test
    public void testGetAllMateriel() throws DBProblemException {
        Materiel materielBD1 = daoMaterielPostgreSQL.createMateriel(materiel.getNom());
        Materiel materielBD2 = daoMaterielPostgreSQL.createMateriel(materiel.getNom() + " motorisé");
        Materiel materielBD3 = daoMaterielPostgreSQL.createMateriel(materiel.getNom() + " électrique");
        int size = daoMaterielPostgreSQL.getAllMateriel().size();
        daoMaterielPostgreSQL.supprimerMateriel(materielBD1.getId());
        daoMaterielPostgreSQL.supprimerMateriel(materielBD2.getId());
        daoMaterielPostgreSQL.supprimerMateriel(materielBD3.getId());
        Assertions.assertEquals(size, daoMaterielPostgreSQL.getAllMateriel().size() + 3);
    }

    /**
     * Test de la méthode getMaterielById
     * @throws DBProblemException si la récupération du matériel ne s'est pas bien passée
     */
    @Test
    public void testGetMaterielById() throws DBProblemException {
        Materiel materielBD = daoMaterielPostgreSQL.createMateriel(materiel.getNom());
        Materiel materielBD2 = daoMaterielPostgreSQL.getMaterielById(materielBD.getId());
        daoMaterielPostgreSQL.supprimerMateriel(materielBD.getId());
        Assertions.assertEquals(materielBD.getId(), materielBD2.getId());
    }

    /**
     * Test de la méthode getMaterielByNom
     * @throws DBProblemException si la récupération du matériel ne s'est pas bien passée
     */
    @Test
    public void testGetMaterielByNom() throws DBProblemException {
        Materiel materielBD = daoMaterielPostgreSQL.createMateriel(materiel.getNom());
        Materiel materielBD2 = daoMaterielPostgreSQL.getMaterielByNom(materielBD.getNom());
        daoMaterielPostgreSQL.supprimerMateriel(materielBD.getId());
        Assertions.assertEquals(materielBD.getId(), materielBD2.getId());
    }

    /**
     * Test de la méthode getAllMaterielWhere
     * @throws DBProblemException si la récupération du matériel ne s'est pas bien passée
     */
    @Test
    public void testGetAllMaterielWhere() throws DBProblemException {
        Materiel materielBD1 = daoMaterielPostgreSQL.createMateriel(materiel.getNom());
        Materiel materielBD2 = daoMaterielPostgreSQL.createMateriel(materiel.getNom() + " motorisé");
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("nom", materiel.getNom() + " motorisé"));
        int size = daoMaterielPostgreSQL.getAllMaterielWhere(whereList).size();
        daoMaterielPostgreSQL.supprimerMateriel(materielBD1.getId());
        daoMaterielPostgreSQL.supprimerMateriel(materielBD2.getId());
        Assertions.assertEquals(size, 1);
    }
}
