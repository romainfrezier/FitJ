package com.fitj.dao;

import com.fitj.classes.*;
import com.fitj.dao.postgresql.*;
import com.fitj.enums.PaiementType;
import kotlin.Pair;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

class DAOCommandePostgreSQLTest {

    private static DAOCommandePostgreSQL daoCommandePostgreSQL;

    private static Commande commande;

    private static Commande commandeBD;

    private static Produit produit;

    private static PaiementType paiementType;

    private static Client client;

    private static Coach coach;

    @BeforeAll
    public static void init() throws Exception {
        daoCommandePostgreSQL = new DAOCommandePostgreSQL();
        coach = (Coach) new DAOClientPostgreSQL().getClientAccount(44);
        client = new DAOClientPostgreSQL().getClientAccount(24);
        produit = new DAOProgrammeSportifPostgreSQL().getAllProgrammeSportif().get(0);
        commande = new CommandePayante(client, coach, produit, 1);
        paiementType = PaiementType.CARTE_BANCAIRE;
        commandeBD = daoCommandePostgreSQL.createCommande(client.getId(), coach.getId(), produit, paiementType);
    }

    @AfterAll
    public static void clean() throws Exception {
        daoCommandePostgreSQL.deleteCommande(commandeBD.getId());
    }

    @Test
    public void createCommande() {
        Assertions.assertEquals(commandeBD.getClient().getId(), commande.getClient().getId());
    }

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

    @Test
    public void getCommandeById() throws Exception {
        Commande commandeBD1 = daoCommandePostgreSQL.getCommandeById(commandeBD.getId());
        Assertions.assertEquals(commandeBD.getClient().getId(), commandeBD1.getClient().getId());
        Assertions.assertEquals(commandeBD.getCoach().getId(), commandeBD1.getCoach().getId());
        Assertions.assertEquals(commandeBD.getProduit().getId(), commandeBD1.getProduit().getId());
    }

    @Test
    public void getCommandeByIdClient() throws Exception {
        List<Commande> commandes = daoCommandePostgreSQL.getCommandeByIdClient(client.getId());
        Assertions.assertEquals(commandes.get(0).getClient().getId(), client.getId());
    }

    @Test
    public void getCommandeByIdCoach() throws Exception {
        List<Commande> commandes = daoCommandePostgreSQL.getCommandeByIdCoach(coach.getId());
        Assertions.assertEquals(commandes.get(0).getCoach().getId(), coach.getId());
    }

    @Test
    public void getCommandeByProduit() throws Exception {
        List<Commande> commandes = daoCommandePostgreSQL.getCommandeByProduit(produit);
        Assertions.assertEquals(commandes.get(0).getProduit().getId(), produit.getId());
    }

    @Test
    public void getAllCommande() throws Exception {
        int size = daoCommandePostgreSQL.getAllCommande().size();
        Commande commandeBD_pers = daoCommandePostgreSQL.createCommande(client.getId(), coach.getId(), produit, paiementType);
        int size1 = daoCommandePostgreSQL.getAllCommande().size();
        daoCommandePostgreSQL.deleteCommande(commandeBD_pers.getId());
        Assertions.assertEquals(size1, size + 1);
    }

    @Test
    public void getAllCommandeWhere() throws Exception {
        List<Pair<String,Object>> where = new ArrayList<>();
        where.add(new Pair<>("id", commandeBD.getId()));
        List<Commande> commandes = daoCommandePostgreSQL.getAllCommandeWhere(where);
        Assertions.assertEquals(commandes.get(0).getId(), commandeBD.getId());
    }

    @Test
    public void deleteCommande() throws Exception {
        DAODemandePostgreSQL daoDemandePostgreSQL = new DAODemandePostgreSQL();

        ProgrammePersonnalise produit_pers = new DAOProgrammePersonnalisePostgreSQL().getAllProgrammePersonnalise().get(0);
        Sport sport = new DAOSportPostgreSQL().getAllSport().get(0);
        Demande demande = new DAODemandePostgreSQL().createDemande(3, "description", true, false, 3, 0, sport, produit_pers);
        Commande commandeBD_pers = daoCommandePostgreSQL.createCommande(client.getId(), coach.getId(), produit_pers, paiementType, demande);

        int size = daoCommandePostgreSQL.getAllCommande().size();

        daoDemandePostgreSQL.supprimerDemande(demande.getId());
        daoCommandePostgreSQL.deleteCommande(commandeBD_pers.getId());

        int size1 = daoCommandePostgreSQL.getAllCommande().size();

        Assertions.assertEquals(size1, size - 1);
    }

    @Test
    public void updateCommande() throws Exception {
        Client client1 = new DAOClientPostgreSQL().getClientAccount(48);
        List<Pair<String,Object>> updateList = new ArrayList<>();
        updateList.add(new Pair<>("idclient", client1.getId()));
        Commande updatedCommande = daoCommandePostgreSQL.updateCommande(updateList, commandeBD.getId());
        Assertions.assertEquals(updatedCommande.getClient().getId(), client1.getId());
    }
}