package com.fitj.dao;

import com.fitj.classes.Client;
import com.fitj.classes.Materiel;
import com.fitj.classes.Sport;
import com.fitj.dao.postgresql.DAOClientPostgreSQL;
import com.fitj.enums.Sexe;
import kotlin.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestModelClientPostgreSQL {

    private static Client client;

    private static DAOClientPostgreSQL daoClientPostgreSQL;


    @BeforeAll
    public static void init(){
        daoClientPostgreSQL = new DAOClientPostgreSQL();
        client = new Client("test@gmail.com","Antoine",77,"testimage.com",178, Sexe.HOMME,"123456", 2);
    }


    @Test
    public void testCreateClient() throws Exception {
        Client newClient = daoClientPostgreSQL.createClient(client.getEmail(), client.getPseudo(),client.getPassword(),client.getPoids(),client.getTaille(),client.getPhoto(),client.getSexe());
        daoClientPostgreSQL.supprimerClient(newClient.getEmail());
        Assertions.assertTrue(newClient.getEmail().equals(client.getEmail()));
    }

    @Test
    public void testDeleteClient() throws Exception {
        String monEmail = "totot@gmail.com";
        daoClientPostgreSQL.createClient(monEmail, client.getPseudo(),client.getPassword(),client.getPoids(),client.getTaille(),client.getPhoto(),client.getSexe());
        daoClientPostgreSQL.supprimerClient(monEmail);
        Assertions.assertThrows(SQLException.class,
                () -> daoClientPostgreSQL.getClientAccount(monEmail));
    }


    @Test
    public void testUpdateClient() throws Exception {
        daoClientPostgreSQL.createClient(client.getEmail(), client.getPseudo(),client.getPassword(),client.getPoids(),client.getTaille(),client.getPhoto(),client.getSexe());
        List<Pair<String,Object>> updateList = new ArrayList<>();
        updateList.add(new Pair<>("taille", 201));
        updateList.add(new Pair<>("pseudo", "newPseud"));
        Client newClient = daoClientPostgreSQL.updateClient(updateList,client.getEmail());
        Assertions.assertTrue(newClient.getPseudo().equals("newPseud") && newClient.getTaille() != client.getTaille());
    }

    @Test
    public void testClientMateriel() throws Exception {
        //A refaire quand usecase matériel fait
        List<Materiel> materiels = daoClientPostgreSQL.getClientMateriel(15);
        for (Materiel materiel : materiels){
            System.out.println(materiel.getNom());
        }
    }
    @Test
    public void testClientSport() throws Exception {

    }

    @Test
    public void testClientCommande() throws Exception {

    }


    @Test
    public void testSelectClient() throws Exception {
        daoClientPostgreSQL.createClient(client.getEmail(), client.getPseudo(),client.getPassword(),client.getPoids(),client.getTaille(),client.getPhoto(),client.getSexe());
        Client newClient = daoClientPostgreSQL.getClientAccount("test@gmail.com");
        Assertions.assertTrue(newClient.getEmail().equals(client.getEmail()));
    }



}
