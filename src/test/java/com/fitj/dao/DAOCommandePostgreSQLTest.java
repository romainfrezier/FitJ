package com.fitj.dao;

import com.fitj.classes.*;
import com.fitj.dao.postgresql.*;
import com.fitj.enums.PaiementType;
import com.fitj.enums.ProgrammeType;
import kotlin.Pair;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe de test de la classe DAOCommandePostgreSQL
 * @author Romain Frezier
 * @see DAOCommandePostgreSQL
 */
class DAOCommandePostgreSQLTest {

    /**
     * Instance de la classe DAOCommandePostgreSQL utilisée pour les tests
     */
    private static DAOCommandePostgreSQL daoCommandePostgreSQL;

    /**
     * Instance de la classe Commande utilisée pour les tests
     */
    private static Commande commande;

    /**
     * Instance de la classe Commande utilisée pour les tests
     */
    private static Commande commandeBD;

    /**
     * Instance de la classe Produit utilisée pour les tests
     */
    private static Produit produit;

    /**
     * Instance de la classe PaiementType utilisée pour les tests
     */
    private static PaiementType paiementType;

    /**
     * Instance de la classe Client utilisée pour les tests
     */
    private static Client client;

    /**
     * Instance de la classe Coach utilisée pour les tests
     */
    private static Coach coach;

    /**
     * Initialisation des instances utilisées pour les tests
     * @throws Exception si une erreur est survenue lors de l'initialisation des tests
     */
    @BeforeAll
    public static void init() throws Exception {
        daoCommandePostgreSQL = new DAOCommandePostgreSQL();
        coach = new DAOClientPostgreSQL().getAllCoach().get(0);
        client = new DAOClientPostgreSQL().getAllClient().get(0);
        produit = new DAOProgrammeSportifPostgreSQL().createProgrammeSportif("Programme", "desc", 23, ProgrammeType.DIFFCILE,4, coach, new ArrayList<>());
        commande = new CommandePayante(client, coach, produit, 1);
        paiementType = PaiementType.CARTE_BANCAIRE;
        commandeBD = daoCommandePostgreSQL.createCommande(client.getId(), coach.getId(), produit, paiementType);
    }

    /**
     * Méthode appelée après que tous les tests ont été exécutés
     * @throws Exception si une erreur est survenue lors de la suppression des données de tests
     */
    @AfterAll
    public static void clean() throws Exception {
        daoCommandePostgreSQL.deleteCommande(commandeBD.getId());
        new DAOProgrammeSportifPostgreSQL().supprimerProgrammeSportif(produit.getId());
    }

    /**
     * Test de la méthode createCommande
     */
    @Test
    public void createCommande() {
        Assertions.assertEquals(commandeBD.getClient().getId(), commande.getClient().getId());
    }

    /**
     * Test de la méthode createCommande avec une demande
     * @throws Exception si une erreur est survenue lors de la récupération de la commande
     */
    @Test
    public void createCommandeWithDemande() throws Exception {
        DAODemandePostgreSQL daoDemandePostgreSQL = new DAODemandePostgreSQL();

        ProgrammePersonnalise produit_pers = new DAOProgrammePersonnalisePostgreSQL().getAllProgrammePersonnalise().get(0);
        Sport sport = new DAOSportPostgreSQL().getAllSport().get(0);
        Demande demande = new DAODemandePostgreSQL().createDemande(3, "description", true, false, 3, 0, sport, produit_pers);
        Commande commandeBD_pers = daoCommandePostgreSQL.createCommande(client.getId(), coach.getId(), produit_pers, paiementType, demande);

        daoDemandePostgreSQL.supprimerDemande(demande.getId());
        daoCommandePostgreSQL.deleteCommande(commandeBD_pers.getId());

        Assertions.assertEquals(commandeBD_pers.getProduit().getId(), produit_pers.getId());
    }

    /**
     * Test de la méthode getCommandeById
     * @throws Exception si une erreur est survenue lors de la récupération de la commande
     */
    @Test
    public void getCommandeById() throws Exception {
        Commande commandeBD1 = daoCommandePostgreSQL.getCommandeById(commandeBD.getId());
        Assertions.assertEquals(commandeBD.getClient().getId(), commandeBD1.getClient().getId());
        Assertions.assertEquals(commandeBD.getCoach().getId(), commandeBD1.getCoach().getId());
        Assertions.assertEquals(commandeBD.getProduit().getId(), commandeBD1.getProduit().getId());
    }

    /**
     * Test de la méthode getCommandeByIdClient
     * @throws Exception si une erreur est survenue lors de la récupération des commandes
     */
    @Test
    public void getCommandeByIdClient() throws Exception {
        List<Commande> commandes = daoCommandePostgreSQL.getCommandeByIdClient(client.getId());
        Assertions.assertEquals(commandes.get(0).getClient().getId(), client.getId());
    }

    /**
     * Test de la méthode getCommandeByIdCoach
     * @throws Exception si une erreur est survenue lors de la récupération des commandes
     */
    @Test
    public void getCommandeByIdCoach() throws Exception {
        List<Commande> commandes = daoCommandePostgreSQL.getCommandeByIdCoach(coach.getId());
        Assertions.assertEquals(commandes.get(0).getCoach().getId(), coach.getId());
    }

    /**
     * Test de la méthode getCommandeByProduit
     * @throws Exception si une erreur est survenue lors de la récupération des commandes
     */
    @Test
    public void getCommandeByProduit() throws Exception {
        List<Commande> commandes = daoCommandePostgreSQL.getCommandeByProduit(produit);
        Assertions.assertEquals(commandes.get(0).getProduit().getId(), produit.getId());
    }

    /**
     * Test de la méthode getAllCommande
     * @throws Exception si une erreur est survenue lors de la récupération des commandes
     */
    @Test
    public void getAllCommande() throws Exception {
        int size = daoCommandePostgreSQL.getAllCommande().size();
        Commande commandeBD_pers = daoCommandePostgreSQL.createCommande(client.getId(), coach.getId(), produit, paiementType);
        int size1 = daoCommandePostgreSQL.getAllCommande().size();
        daoCommandePostgreSQL.deleteCommande(commandeBD_pers.getId());
        Assertions.assertEquals(size1, size + 1);
    }

    /**
     * Test de la méthode getAllCommandeWhere
     * @throws Exception si une erreur est survenue lors de la récupération des commandes
     */
    @Test
    public void getAllCommandeWhere() throws Exception {
        List<Pair<String,Object>> where = new ArrayList<>();
        where.add(new Pair<>("id", commandeBD.getId()));
        List<Commande> commandes = daoCommandePostgreSQL.getAllCommandeWhere(where);
        Assertions.assertEquals(commandes.get(0).getId(), commandeBD.getId());
    }

    /**
     * Test de la méthode deleteCommande
     * @throws Exception si une erreur est survenue lors de la suppression de la commande
     */
    @Test
    public void deleteCommande() throws Exception {
        DAODemandePostgreSQL daoDemandePostgreSQL = new DAODemandePostgreSQL();
        ProgrammePersonnalise produit_pers = new DAOProgrammePersonnalisePostgreSQL().getAllProgrammePersonnalise().get(0);
        Sport sport = new DAOSportPostgreSQL().getAllSport().get(0);
        Demande demande = new DAODemandePostgreSQL().createDemande(3, "description", true, false, 3, 0, sport, produit_pers);
        Commande commandeBD_pers = daoCommandePostgreSQL.createCommande(client.getId(), coach.getId(), produit_pers, paiementType, demande);
        daoDemandePostgreSQL.supprimerDemande(demande.getId());
        daoCommandePostgreSQL.deleteCommande(commandeBD_pers.getId());
        Assertions.assertThrows(Exception.class, () -> daoCommandePostgreSQL.getCommandeById(commandeBD_pers.getId()));
    }

    /**
     * Test de la méthode updateCommande
     * @throws Exception si une erreur est survenue lors de la mise à jour de la commande
     */
    @Test
    public void updateCommande() throws Exception {
        Client client1 = new DAOClientPostgreSQL().getClientById(48);
        List<Pair<String,Object>> updateList = new ArrayList<>();
        updateList.add(new Pair<>("idclient", client1.getId()));
        Commande updatedCommande = daoCommandePostgreSQL.updateCommande(updateList, commandeBD.getId());
        Assertions.assertEquals(updatedCommande.getClient().getId(), client1.getId());
    }
}