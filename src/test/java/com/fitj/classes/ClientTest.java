package com.fitj.classes;

import com.fitj.enums.Sexe;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

/**
 * Classe de test de la classe Client
 * @see Client
 * @author Etienne Tillier, Romain Frezier
 */
public class ClientTest {

    /**
     * Objets clients utilisés pour les tests
     */
    private static Client client;

    /**
     * Initialisation des objets utilisés pour les tests
     */
    @BeforeAll
    public static void init(){
        client = new Client("test@gmail.com","Joe", 56, "monimage.com", 178, Sexe.HOMME, "123456",1, false);
    }

    /**
     * Test du constructeur de la classe Client
     */
    @Test
    public void testCreationClient(){
        Assertions.assertTrue(client.getListeCommande().isEmpty() && client.getListeMateriel().isEmpty() && client.getListeSport().isEmpty());
    }

    /**
     * Test de la méthode ajouterMateriel de la classe Client
     */
    @Test
    public void testAjouterMateriel(){

    }

    /**
     * Test de la méthode ajouterCommande de la classe Client
     */
    @Test
    public void testAjouterCommande(){

    }

    /**
     * Test de la méthode ajouterSport de la classe Client
     */
    @Test
    public void testAjouterSport(){

    }
}
