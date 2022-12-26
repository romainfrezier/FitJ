package com.fitj.dao;

import com.fitj.classes.*;
import com.fitj.dao.factory.FactoryDAOPostgreSQL;
import com.fitj.dao.postgresql.DAOClientPostgreSQL;
import com.fitj.dao.postgresql.DAODemandePostgreSQL;
import com.fitj.dao.postgresql.DAOProgrammePersonnalisePostgreSQL;
import com.fitj.dao.postgresql.DAOSportPostgreSQL;
import com.fitj.enums.Sexe;
import kotlin.Pair;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe de test de la classe DAODemandePostgreSQL
 * @see DAODemandePostgreSQL
 * @author Etienne Tillier, Romain Frezier
 */
public class DAODemandePostgreSQLTest {

    /**
     * Objets utilisés pour les tests
     */
    private static ProgrammePersonnalise programmePersonnaliseBD;

    /**
     * Objet utilisé pour les tests
     */
    private static DAODemandePostgreSQL daoDemandePostgreSQL;

    /**
     * Objet utilisé pour les tests
     */
    private static Demande demande;

    /**
     * Objet utilisé pour les tests
     */
    private static Sport sport;

    /**
     * Objet utilisé pour les tests
     */
    private static Coach coach;

    /**
     * Initialisation des objets utilisés pour les tests
     * @throws Exception si la requête SQL échoue
     */
    @BeforeAll
    public static void init() throws Exception {
        daoDemandePostgreSQL = new DAODemandePostgreSQL();
        sport = new DAOSportPostgreSQL().createSport("temp sport");
        coach = new DAOClientPostgreSQL().getAllCoach().get(0);
        programmePersonnaliseBD = new DAOProgrammePersonnalisePostgreSQL().createProgrammePersonnalise("Super programme", "Je personnalise votre programme", 680, coach);
        demande = daoDemandePostgreSQL.createDemande(6,"Je suis Francis jaimerai manger plus et rester au meme poids", true, true,4, 9, sport, programmePersonnaliseBD);
    }

    /**
     * Méthode utilisée après tous les tests pour supprimer la demande créée et le programme personnalise créé
     * @throws Exception si la requête SQL échoue
     */
    @AfterAll
    public static void clean() throws Exception {
        new DAOProgrammePersonnalisePostgreSQL().supprimerProgrammePersonnalise(programmePersonnaliseBD.getId());
        new DAOSportPostgreSQL().supprimerSport(sport.getId());
        daoDemandePostgreSQL.supprimerDemande(demande.getId());
    }

    /**
     * Test de la méthode createDemande de la classe DAODemandePostgreSQL
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testCreateDemande() throws Exception {
        // TODO pb string ''
        Assertions.assertEquals("Je suis Francis jaimerai manger plus et rester au meme poids", demande.getDescription());
    }

    /**
     * Test de la méthode updateDemande de la classe DAODemandePostgreSQL
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testDemandeUpdate() throws Exception {
        List<Pair<String,Object>> updateList = new ArrayList<>();
        updateList.add(new Pair<>("nbmois",9));
        demande = daoDemandePostgreSQL.updateDemande(updateList, demande.getId());
        Assertions.assertEquals(9, demande.getNbMois());
    }

    /**
     * Test de la méthode deleteDemande de la classe DAODemandePostgreSQL
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testDemandeDelete() throws Exception {
        Demande demande1 = daoDemandePostgreSQL.createDemande(6,"Je suis Francis jaimerai manger plus et rester au meme poids", true, true,4, 9, sport, programmePersonnaliseBD);
        daoDemandePostgreSQL.supprimerDemande(demande1.getId());
        Assertions.assertThrows(Exception.class,
                () -> daoDemandePostgreSQL.getDemandeById(demande1.getId()));
    }

    /**
     * Test de la méthode getAllDemande de la classe DAODemandePostgreSQL
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testGetAllDemande() throws Exception {
        Demande demande1 = daoDemandePostgreSQL.createDemande(6,"Je suis Francis jaimerai manger plus et rester au meme poids", true, true,4, 9, sport, programmePersonnaliseBD);
        int nbSeanceBD = daoDemandePostgreSQL.getAllDemande().size();
        daoDemandePostgreSQL.supprimerDemande(demande1.getId());
        int nbSeanceBD2 = daoDemandePostgreSQL.getAllDemande().size();
        Assertions.assertEquals(nbSeanceBD, nbSeanceBD2 + 1);
    }
}
