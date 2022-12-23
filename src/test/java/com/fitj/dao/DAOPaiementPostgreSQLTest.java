package com.fitj.dao;

import com.fitj.classes.Client;
import com.fitj.classes.Commande;
import com.fitj.classes.Paiement;
import com.fitj.dao.postgresql.DAOClientPostgreSQL;
import com.fitj.dao.postgresql.DAOCommandePostgreSQL;
import com.fitj.dao.postgresql.DAOPaiementPostgreSQL;
import com.fitj.dao.postgresql.DAOProgrammeSportifPostgreSQL;
import com.fitj.enums.PaiementType;
import kotlin.Pair;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe de test de la classe DAOPaiementPostgreSQL
 * @author Romain Frezier
 * @see DAOPaiementPostgreSQL
 */
class DAOPaiementPostgreSQLTest {

    /**
     * Instance de la classe DAOPaiementPostgreSQL utilisée pour les tests
     */
    private static DAOPaiementPostgreSQL daoPaiementPostgreSQL;

    /**
     * Instance de la classe Paiement utilisée pour les tests
     */
    private static Paiement paiement;

    /**
     * Instance de la classe Paiement utilisée pour les tests
     */
    private static Paiement paiementBD;

    /**
     * Instance de la classe Client utilisée pour les tests
     */
    private static Client client;

    /**
     * Instance de la classe Commande utilisée pour les tests
     */
    private static Commande commande;

    /**
     * Initialisation des instances utilisées pour les tests
     * @throws Exception si une erreur est survenue lors de l'initialisation des tests
     */
    @BeforeAll
    public static void init() throws Exception {
        daoPaiementPostgreSQL = new DAOPaiementPostgreSQL();
        client = new DAOClientPostgreSQL().getClientAccount(24);
        paiement = new Paiement(0, 10, PaiementType.CARTE_BANCAIRE);
        commande = new DAOCommandePostgreSQL().createCommande(client.getId(), 44, new DAOProgrammeSportifPostgreSQL().getAllProgrammeSportif().get(0), PaiementType.CARTE_BANCAIRE);
        paiementBD = daoPaiementPostgreSQL.createPaiement(commande.getId(), 10, paiement.getType());
    }

    /**
     * Suppression des instances utilisées pour les tests
     * @throws Exception si une erreur est survenue lors de la suppression des tests
     */
    @AfterAll
    public static void clean() throws Exception {
        daoPaiementPostgreSQL.deletePaiement(paiementBD.getId());
        new DAOCommandePostgreSQL().deleteCommande(commande.getId());
    }

/**
     * Test de la méthode createPaiement
     */
    @Test
    public void createPaiement() {
        Assertions.assertEquals(paiementBD.getMontant(), paiement.getMontant());
    }

    /**
     * Test de la méthode getPaiementById
     * @throws Exception si une erreur est survenue lors de l'exécution du test
     */
    @Test
    public void getPaiementById() throws Exception {
        Assertions.assertEquals(daoPaiementPostgreSQL.getPaiementById(paiementBD.getId()).getId(), paiementBD.getId());
    }

    /**
     * Test de la méthode getAllPaiements
     * @throws Exception si une erreur est survenue lors de l'exécution du test
     */
    @Test
    public void getAllPaiements() throws Exception {
        int size = daoPaiementPostgreSQL.getAllPaiements().size();

        Paiement paiementBD2 = daoPaiementPostgreSQL.createPaiement(commande.getId(), paiement.getMontant(), paiement.getType());

        int size2 = daoPaiementPostgreSQL.getAllPaiements().size();

        daoPaiementPostgreSQL.deletePaiement(paiementBD2.getId());

        Assertions.assertEquals(size2, size + 1);
    }

    /**
     * Test de la méthode getAllPaiementsWhere
     * @throws Exception si une erreur est survenue lors de l'exécution du test
     */
    @Test
    public void getAllPaiementsWhere() throws Exception {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("id", paiementBD.getId()));
        Assertions.assertEquals(daoPaiementPostgreSQL.getAllPaiementsWhere(whereList).get(0).getId(), paiementBD.getId());
    }

    /**
     * Test de la méthode updatePaiement
     * @throws Exception si une erreur est survenue lors de l'exécution du test
     */
    @Test
    void updatePaiement() throws Exception {
        List<Pair<String, Object>> setList = new ArrayList<>();
        setList.add(new Pair<>("montant", paiementBD.getMontant() + 1));
        Paiement newPaiement = daoPaiementPostgreSQL.updatePaiement(setList, paiementBD.getId());
        Assertions.assertEquals(newPaiement.getMontant(), paiementBD.getMontant() + 1);
    }

    /**
     * Test de la méthode deletePaiement
     * @throws Exception si une erreur est survenue lors de l'exécution du test
     */
    @Test
    public void deletePaiement() throws Exception {
        Paiement newPaiement = daoPaiementPostgreSQL.createPaiement(commande.getId(), paiement.getMontant(), paiement.getType());
        daoPaiementPostgreSQL.deletePaiement(newPaiement.getId());
        Assertions.assertThrows(Exception.class, () -> daoPaiementPostgreSQL.getPaiementById(newPaiement.getId()));
    }
}