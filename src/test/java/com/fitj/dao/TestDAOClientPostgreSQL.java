package com.fitj.dao;

import com.fitj.classes.Client;
import com.fitj.classes.Materiel;
import com.fitj.dao.postgresql.DAOClientPostgreSQL;
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
 * Classe de test de la classe DAOClientPostgreSQL
 * @see DAOClientPostgreSQL
 * @author Etienne Tillier, Romain Frezier
 */
public class TestDAOClientPostgreSQL {

    /**
     * Objets utilisés pour les tests
     */
    private static Client client;

    /**
     * Objet utilisé pour les tests
     */
    private static Client clientBD;

    /**
     * DAO utilisé pour les tests
     */
    private static DAOClientPostgreSQL daoClientPostgreSQL;

    /**
     * Initialisation des objets utilisés pour les tests
     * @throws Exception si la requête SQL échoue
     */
    @BeforeAll
    public static void init() throws Exception {
        daoClientPostgreSQL = new DAOClientPostgreSQL();
        client = new Client("test@gmail.com","Antoine",77,"testimage.com",178, Sexe.HOMME,"123456", 2);
        clientBD = daoClientPostgreSQL.createClient(client.getEmail(), client.getPseudo(), client.getPassword(), client.getPoids(), client.getTaille(), client.getPhoto(), client.getSexe());
    }

    /**
     * Méthode appelée après tous les tests pour supprimer le client créé
     * @throws Exception si la requête SQL échoue
     */
    @AfterAll
    public static void clean() throws Exception {
        daoClientPostgreSQL.supprimerClientById(clientBD.getId());
    }

    /**
     * Test de la méthode createClient de la classe DAOClientPostgreSQL
     */
    @Test
    public void testCreateClient() {
        Assertions.assertEquals(clientBD.getEmail(), client.getEmail());
    }

    /**
     * Test de la méthode deleteClient de la classe DAOClientPostgreSQL
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testDeleteClient() throws Exception {
        String email = "nouveauMail";
        Client newClient = daoClientPostgreSQL.createClient(email, client.getPseudo(), client.getPassword(), client.getPoids(), client.getTaille(), client.getPhoto(), client.getSexe());
        daoClientPostgreSQL.supprimerClientByMail(newClient.getEmail());
        Assertions.assertThrows(SQLException.class,
                () -> daoClientPostgreSQL.getClientAccount(newClient.getEmail()));
    }

    /**
     * Test de la méthode updateClient de la classe DAOClientPostgreSQL
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testUpdateClient() throws Exception {
        List<Pair<String, Object>> updateList = new ArrayList<>();
        updateList.add(new Pair<>("taille", 201));
        updateList.add(new Pair<>("pseudo", "newPseudo"));
        clientBD = daoClientPostgreSQL.updateClient(updateList, clientBD.getEmail());
        Assertions.assertEquals(clientBD.getPseudo(), "newPseudo");
        Assertions.assertEquals(clientBD.getTaille(), 201);
    }

    /**
     * Test de la méthode getClientMateriel de la classe DAOClientPostgreSQL
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testClientMateriel() throws Exception {
            //A refaire quand usecase matériel fait
        List<Materiel> materiels = daoClientPostgreSQL.getClientMateriel(15);
        for (Materiel materiel : materiels) {
            System.out.println(materiel.getNom());
        }
    }
    @Test
    public void testClientSport() {

    }

    @Test
    public void testClientCommande() {

    }

    /**
     * Test de la méthode getClientAccount de la classe DAOClientPostgreSQL
     */
    @Test
    public void testSelectClient() throws SQLException {
        Client newClient = daoClientPostgreSQL.getClientAccount(clientBD.getEmail());
        Assertions.assertEquals(newClient.getEmail(), clientBD.getEmail());
    }



}
