package com.fitj.dao;

import com.fitj.classes.Sport;
import com.fitj.dao.postgresql.DAOSportPostgreSQL;
import kotlin.Pair;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe de test de la DAO SportPostgreSQL
 * @see DAOSportPostgreSQL
 * @author Etienne Tillier, Romain Frezier
 */
public class DAOSportPostgreSQLTest {

    /**
     * Objet utilisé pour les tests
     */
    private static Sport sport;

    /**
     * Objet utilisé pour les tests
     */
    private static Sport sportBD;

    /**
     * DAO utilisé pour les tests
     */
    private static DAOSportPostgreSQL daoSportPostgreSQL;

    /**
     * Initialisation des objets utilisés pour les tests
     * @throws Exception si la requête SQL échoue
     */
    @BeforeAll
    public static void init() throws Exception {
        daoSportPostgreSQL = new DAOSportPostgreSQL();
        sport = new Sport(1,"Foot");
        sportBD = daoSportPostgreSQL.createSport(sport.getNom());
    }

    /**
     * Méthode appelée après tous les tests
     * @throws Exception si la requête SQL échoue
     */
    @AfterAll
    public static void clean() throws Exception {
        daoSportPostgreSQL.supprimerSport(sportBD.getId());
    }

    /**
     * Test de la méthode de création d'un sport
     */
    @Test
    public void testSportCreate() {
        Assertions.assertEquals(sport.getNom(), sportBD.getNom());
    }

    /**
     * Test de la méthode de modification d'un sport par son id
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testSportUpdate() throws Exception {
        List<Pair<String,Object>> updateList = new ArrayList<>();
        updateList.add(new Pair<>("nom","Snowboard"));
        sportBD = daoSportPostgreSQL.updateSport(updateList,sportBD.getId());
        Assertions.assertEquals("Snowboard", sportBD.getNom());
    }

    /**
     * Test de la méthode de suppression d'un sport par son id
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testSportDelete() throws Exception {
        Sport sport1 = daoSportPostgreSQL.createSport("Muscu");
        daoSportPostgreSQL.supprimerSport(sport1.getId());
        Assertions.assertThrows(SQLException.class,
                () -> daoSportPostgreSQL.getSportById(sport1.getId()));
    }

    /**
     * Test de la méthode de récupération de tous les sports
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testGetAllSport() throws Exception {
        Sport sportBD1 = daoSportPostgreSQL.createSport("Muscu");
        Sport sportBD2 = daoSportPostgreSQL.createSport("Badminton");
        Sport sportBD3 = daoSportPostgreSQL.createSport("Natation");

        int nbSportBD = daoSportPostgreSQL.getAllSport().size();

        daoSportPostgreSQL.supprimerSport(sportBD1.getId());
        daoSportPostgreSQL.supprimerSport(sportBD2.getId());
        daoSportPostgreSQL.supprimerSport(sportBD3.getId());

        Assertions.assertEquals(nbSportBD, daoSportPostgreSQL.getAllSport().size() + 3);
    }




}
