package com.fitj.classes;

import com.fitj.enums.Sexe;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;



public class TestClient {

    private static Client client;

    @BeforeAll
    public static void initForTests(){
        client = new Client("test@gmail.com","Joe", 56, "monimage.com", 178, Sexe.HOMME, "123456",1);
    }

    @Test
    public void testCreationClient(){
        Assertions.assertTrue(client.getListeCommande().isEmpty() && client.getListeMateriel().isEmpty() && client.getListeSport().isEmpty());
    }

    @Test
    public void testAjouterMateriel(){

    }
    @Test
    public void testAjouterCommande(){

    }

    @Test
    public void testAjouterSport(){

    }
}
