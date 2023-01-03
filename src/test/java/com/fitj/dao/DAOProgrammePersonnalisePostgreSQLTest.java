package com.fitj.dao;

import com.fitj.classes.*;
import com.fitj.dao.factory.FactoryDAOPostgreSQL;
import com.fitj.dao.postgresql.DAOProgrammeNutritionPostgreSQL;
import com.fitj.dao.postgresql.DAOProgrammePersonnalisePostgreSQL;
import com.fitj.dao.postgresql.DAOProgrammeSportifPostgreSQL;
import com.fitj.enums.ProgrammeType;
import com.fitj.enums.Sexe;
import kotlin.Pair;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe de test de la DAO ProgrammePersonnalisePostgreSQL
 * @see DAOProgrammePersonnalisePostgreSQL
 * @author Etienne Tillier, Romain Frezier
 */
public class DAOProgrammePersonnalisePostgreSQLTest {

    /**
     * Objet utilisé pour les tests
     */
    private static ProgrammePersonnalise programmePersonnalise;

    /**
     * Objet utilisé pour les tests
     */
    private static ProgrammePersonnalise programmePersonnaliseDemande;

    /**
     * Objet utilisé pour les tests
     */
    private static DAOProgrammePersonnalisePostgreSQL daoProgrammePersonnalisePostgreSQL;

    /**
     * Objet utilisé pour les tests
     */
    private static Coach coach;

    /**
     * Objet utilisé pour les tests
     */
    private static ArrayList<Programme> listeProgramme;

    /**
     * Initialisation des objets utilisés pour les tests
     */
    private static Demande demande;

    /**
     * Objet utilisé pour les tests
     */
    private static Sport sport;

    /**
     * Objet utilisé pour les tests
     */
    private static ProgrammeSportif programmeSportif;

    /**
     * Objet utilisé pour les tests
     */
    private static ProgrammeNutrition programmeNutrition;

    /**
     * Initialisation des objets utilisés pour les tests
     * @throws Exception si une erreur survient
     */
    @BeforeAll
    public static void init() throws Exception {
        daoProgrammePersonnalisePostgreSQL = new DAOProgrammePersonnalisePostgreSQL();
        sport = FactoryDAOPostgreSQL.getInstance().getDAOSport().getAllSport().get(0);
        coach = new Coach("coach@gmail.com", "elcocho", 100, "dadada", 174, Sexe.HOMME, "test", 44,false);
        programmePersonnalise = daoProgrammePersonnalisePostgreSQL.createProgrammePersonnalise("ProgrammeMaster", "Je veux devenir superman", 1000, coach);
        demande = FactoryDAOPostgreSQL.getInstance().getDAODemande().createDemande(12,"Je suis faible je veux devenir fort", true, true, 5, 12, sport, programmePersonnalise);
        programmePersonnaliseDemande = daoProgrammePersonnalisePostgreSQL.getProgrammePersonnaliseById(demande.getProgrammePersonnalise().getId());
        listeProgramme = new ArrayList<>();
        programmeSportif = new DAOProgrammeSportifPostgreSQL().createProgrammeSportif("ProgrammeSportif", "Je veux devenir superman", 1000, ProgrammeType.FACILE, 4, coach, new ArrayList<>());
        programmeNutrition = new DAOProgrammeNutritionPostgreSQL().createProgrammeNutrition("ProgrammeNutrition", "Je veux devenir superman", 1000, ProgrammeType.FACILE, 4, coach, new ArrayList<>());
    }

    /**
     * Suppression des objets utilisés pour les tests
     * @throws Exception si la suppression échoue
     */
    @AfterAll
    public static void fin() throws Exception{
        daoProgrammePersonnalisePostgreSQL.supprimerProgrammePersonnalise(programmePersonnalise.getId());
        daoProgrammePersonnalisePostgreSQL.supprimerProgrammePersonnalise(programmePersonnaliseDemande.getId());
        new DAOProgrammeSportifPostgreSQL().supprimerProgrammeSportif(programmeSportif.getId());
        new DAOProgrammeNutritionPostgreSQL().supprimerProgrammeNutrition(programmeNutrition.getId());
    }

    /**
     * Test de la méthode createProgrammePersonnalise
     */
    @Test
    public void testCreateProgrammePersonnalise() {
        Assertions.assertEquals("Je veux devenir superman", programmePersonnalise.getDescription());
    }

    /**
     * Test de la méthode createDemandeProgrammePersonnalise
     */
    @Test
    public void testCreateDemandeProgrammePersonnalise() {
        Assertions.assertEquals("Je veux devenir superman", programmePersonnaliseDemande.getDescription());
    }

    /**
     * Test de la méthode updateProgrammePersonnalise
     * @throws Exception si la requête échoue
     */
    @Test
    public void testProgrammePersonnaliseUpdate() throws Exception {
        List<Pair<String,Object>> updateList = new ArrayList<>();
        updateList.add(new Pair<>("prix",600));
        programmePersonnaliseDemande = daoProgrammePersonnalisePostgreSQL.updateProgrammePersonnalise(updateList,programmePersonnaliseDemande.getId());
        Assertions.assertEquals(600, programmePersonnaliseDemande.getPrix());
    }

    /**
     * Test de la méthode deleteProgrammePersonnalise
     * @throws Exception si la requête échoue
     */
    @Test
    public void testProgrammePersonnaliseDelete() throws Exception {
        ProgrammePersonnalise programmePersonnalise = daoProgrammePersonnalisePostgreSQL.createProgrammePersonnalise("ProgrammeMaster", "Je veux devenir superman", 1000, coach);
        daoProgrammePersonnalisePostgreSQL.supprimerProgrammePersonnalise(programmePersonnalise.getId());
        Assertions.assertThrows(Exception.class, () -> daoProgrammePersonnalisePostgreSQL.getProgrammePersonnaliseById(programmePersonnalise.getId()));
    }

    /**
     * Test de la méthode getAllProgrammePersonnalise
     * @throws Exception si la requête échoue
     */
    @Test
    public void testGetAllProgrammePersonnalise() throws Exception {
        ProgrammePersonnalise programmePersonnalise = daoProgrammePersonnalisePostgreSQL.createProgrammePersonnalise("ProgrammeMaster", "Je veux devenir superman", 1000, coach);
        int size = daoProgrammePersonnalisePostgreSQL.getAllProgrammePersonnalise().size();
        daoProgrammePersonnalisePostgreSQL.supprimerProgrammePersonnalise(programmePersonnalise.getId());
        Assertions.assertEquals(size, daoProgrammePersonnalisePostgreSQL.getAllProgrammePersonnalise().size() + 1);
    }

    /**
     * Test de la méthode supprimerProgrammeProgrammePersonnalise
     * @throws Exception si la requête échoue
     */
    @Test
    public void testSupprimerProgrammeProgrammePersonnalise() throws Exception {
        daoProgrammePersonnalisePostgreSQL.ajouterProgrammeProgrammePersonnalise(programmeNutrition,programmePersonnaliseDemande.getId());
        ProgrammePersonnalise programmePersonnalise1 = daoProgrammePersonnalisePostgreSQL.getProgrammePersonnaliseById(programmePersonnaliseDemande.getId());
        daoProgrammePersonnalisePostgreSQL.supprimerProgrammeProgrammePersonnalise(programmeNutrition,programmePersonnaliseDemande.getId());
        ProgrammePersonnalise programmePersonnalise2 = daoProgrammePersonnalisePostgreSQL.getProgrammePersonnaliseById(programmePersonnaliseDemande.getId());
        Assertions.assertEquals(programmePersonnalise1.getListeProgrammes().size(), programmePersonnalise2.getListeProgrammes().size() + 1);
    }

    /**
     * Test de la méthode ajouterProgrammeProgrammePersonnalise
     * @throws Exception si la requête échoue
     */
    @Test
    public void testAjouterProgrammeProgrammePersonnalise() throws Exception {
        daoProgrammePersonnalisePostgreSQL.ajouterProgrammeProgrammePersonnalise(programmeNutrition,programmePersonnaliseDemande.getId());
        daoProgrammePersonnalisePostgreSQL.ajouterProgrammeProgrammePersonnalise(programmeSportif,programmePersonnaliseDemande.getId());
        ProgrammePersonnalise programmePersonnalise1 = daoProgrammePersonnalisePostgreSQL.getProgrammePersonnaliseById(programmePersonnaliseDemande.getId());
        daoProgrammePersonnalisePostgreSQL.supprimerProgrammeProgrammePersonnalise(programmeNutrition,programmePersonnaliseDemande.getId());
        daoProgrammePersonnalisePostgreSQL.supprimerProgrammeProgrammePersonnalise(programmeSportif,programmePersonnaliseDemande.getId());
        ProgrammePersonnalise programmePersonnalise2 = daoProgrammePersonnalisePostgreSQL.getProgrammePersonnaliseById(programmePersonnaliseDemande.getId());
        Assertions.assertEquals(programmePersonnalise2.getListeProgrammes().size(), programmePersonnalise1.getListeProgrammes().size() - 2);
    }

}
