package com.fitj.dao;

import com.fitj.classes.Client;
import com.fitj.classes.Materiel;
import com.fitj.classes.Sport;
import com.fitj.dao.postgresql.DAOClientPostgreSQL;
import com.fitj.dao.postgresql.DAOMaterielPostgreSQL;
import com.fitj.dao.postgresql.DAOSportPostgreSQL;
import com.fitj.dao.tools.PasswordAuthentication;
import com.fitj.enums.Sexe;
import kotlin.Pair;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe de test de la classe DAOClientPostgreSQL
 * @see DAOClientPostgreSQL
 * @author Etienne Tillier, Romain Frezier
 */
public class DAOClientPostgreSQLTest {

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
     * DAO utilisé pour les tests
     */
    private static DAOMaterielPostgreSQL daoMaterielPostgreSQL;

    /**
     * DAO utilisé pour les tests
     */
    private static DAOSportPostgreSQL daoSportPostgreSQL;

    /**
     * Objet utilisé pour les tests
     */
    private static Materiel materiel;

    /**
     * Objet utilisé pour les tests
     */
    private static Sport sport;

    /**
     * Initialisation des objets utilisés pour les tests
     * @throws Exception si la requête SQL échoue
     */
    @BeforeAll
    public static void init() throws Exception {
        daoClientPostgreSQL = new DAOClientPostgreSQL();
        daoMaterielPostgreSQL = new DAOMaterielPostgreSQL();
        daoSportPostgreSQL = new DAOSportPostgreSQL();
        client = new Client("test@gmail.com","Antoine",77,"testimage.com",178, Sexe.HOMME,"123456", 2, false);
        clientBD = daoClientPostgreSQL.createClient(client.getEmail(), client.getPseudo(), client.getPassword(), client.getPoids(), client.getTaille(), client.getPhoto(), client.getSexe());
        materiel = daoMaterielPostgreSQL.createMateriel("Mon nouveau super matos");
        sport = daoSportPostgreSQL.createSport("Mon nouveau super sport");
    }

    /**
     * Méthode appelée après tous les tests pour supprimer le client créé
     * @throws Exception si la requête SQL échoue
     */
    @AfterAll
    public static void clean() throws Exception {
        daoClientPostgreSQL.supprimerClientById(clientBD.getId());
        daoMaterielPostgreSQL.supprimerMateriel(materiel.getId());
        daoSportPostgreSQL.supprimerSport(sport.getId());
    }

    /**
     * Test de la méthode createClient de la classe DAOClientPostgreSQL
     */
    @Test
    public void testCreateClient() throws Exception {
        Assertions.assertEquals(clientBD.getId(), daoClientPostgreSQL.getClientById(clientBD.getId()).getId());
    }

    /**
     * Test de la méthode supprimerClientByMail de la classe DAOClientPostgreSQL
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testSupprimerClientByMail() throws Exception {
        String email = "nouveauMail";
        Client newClient = daoClientPostgreSQL.createClient(email, client.getPseudo(), client.getPassword(), client.getPoids(), client.getTaille(), client.getPhoto(), client.getSexe());
        daoClientPostgreSQL.supprimerClientByMail(newClient.getEmail());
        Assertions.assertThrows(Exception.class,
                () -> daoClientPostgreSQL.getClientByEmail(newClient.getEmail()));
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
        clientBD = daoClientPostgreSQL.updateClient(updateList, clientBD.getId());
        Assertions.assertEquals(clientBD.getPseudo(), "newPseudo");
        Assertions.assertEquals(clientBD.getTaille(), 201);
    }

    /**
     * Test de la méthode getClientByEmail de la classe DAOClientPostgreSQL
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testGetClientByEmail() throws Exception {
        Assertions.assertEquals(daoClientPostgreSQL.getClientByEmail(clientBD.getEmail()).getEmail(), clientBD.getEmail());
    }

    /**
     * Test de la méthode getClientById de la classe DAOClientPostgreSQL
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void tesGetClientById() throws Exception {
        Assertions.assertEquals(daoClientPostgreSQL.getClientById(clientBD.getId()).getId(), clientBD.getId());
    }

    /**
     * Test de la méthode supprimerClientById de la classe DAOClientPostgreSQL
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testSupprimerClientById() throws Exception {
        Client newClient = daoClientPostgreSQL.createClient("totototo@gmail.coiiii", client.getPseudo(), client.getPassword(), client.getPoids(), client.getTaille(), client.getPhoto(), client.getSexe());
        daoClientPostgreSQL.supprimerClientById(newClient.getId());
        Assertions.assertThrows(Exception.class, () -> daoClientPostgreSQL.getClientById(newClient.getId()));
    }

    @Test
    public void testUpdateClientPhoto() throws Exception {
        String newPhoto = "newPhotoUpdated.jpg";
        clientBD =  daoClientPostgreSQL.updateClientPhoto(newPhoto, clientBD.getId());
        Assertions.assertEquals(clientBD.getPhoto(), newPhoto);
    }

    /**
     * Test de la méthode updateClientPseudo de la classe DAOClientPostgreSQL
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testUpdateClientPseudo() throws Exception {
        String newPseudo = "Mon nouveau super pseudo";
        clientBD =  daoClientPostgreSQL.updateClientPseudo(newPseudo, clientBD.getId());
        Assertions.assertEquals(clientBD.getPseudo(), newPseudo);
    }

    /**
     * Test de la méthode updateClientPoids de la classe DAOClientPostgreSQL
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testUpdateClientPoids() throws Exception {
        double newPoids = 180.5;
        clientBD =  daoClientPostgreSQL.updateClientPoids(newPoids, clientBD.getId());
        Assertions.assertEquals(clientBD.getPoids(), newPoids);
    }

    /**
     * Test de la méthode updateClientTaille de la classe DAOClientPostgreSQL
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testUpdateClientTaille() throws Exception {
        int newTaille = 300;
        clientBD =  daoClientPostgreSQL.updateClientTaille(newTaille, clientBD.getId());
        Assertions.assertEquals(clientBD.getTaille(), newTaille);
    }

    /**
     * Test de la méthode updateClientPassword de la classe DAOClientPostgreSQL
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testUpdateClientPassword() throws Exception {
        String newPassword = "newPassword";
        clientBD =  daoClientPostgreSQL.updateClientPassword(newPassword, clientBD.getId());
        PasswordAuthentication passwordAuthentication = new PasswordAuthentication();
        Assertions.assertTrue(passwordAuthentication.authenticate(newPassword.toCharArray(), clientBD.getPassword()));
    }

    /**
     * Test de la méthode updateClientSexe de la classe DAOClientPostgreSQL
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testUpdateClientSexe() throws Exception {
        Sexe newSexe = Sexe.INCONNU;
        clientBD =  daoClientPostgreSQL.updateClientSexe(newSexe, clientBD.getId());
        Assertions.assertEquals(clientBD.getSexe(), newSexe);
    }

    /**
     * Test de la méthode updateClientMail de la classe DAOClientPostgreSQL
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testUpdateClientMail() throws Exception {
        String newMail = "mon super nouveau mail";
        clientBD =  daoClientPostgreSQL.updateClientMail(newMail, clientBD.getId());
        Assertions.assertEquals(clientBD.getEmail(), newMail);
    }

    /**
     * Test de la méthode getAllClient de la classe DAOClientPostgreSQL
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testGetAllClient() throws Exception {
        int size = daoClientPostgreSQL.getAllClient().size();
        Client newClient = daoClientPostgreSQL.createClient("totototo@gmail.coiiii", client.getPseudo(), client.getPassword(), client.getPoids(), client.getTaille(), client.getPhoto(), client.getSexe());
        int newSize = daoClientPostgreSQL.getAllClient().size();
        daoClientPostgreSQL.supprimerClientById(newClient.getId());
        Assertions.assertEquals(size + 1, newSize);
    }

    /**
     * Test de la méthode getAllCoach de la classe DAOClientPostgreSQL
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testGetAllCoach() throws Exception {
        int size = daoClientPostgreSQL.getAllCoach().size();
        Assertions.assertTrue(size > 0);
    }

    /**
     * Test de la méthode getAllAdmin de la classe DAOClientPostgreSQL
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testGetAllAdmin() throws Exception {
        int size = daoClientPostgreSQL.getAllAdmin().size();
        Assertions.assertTrue(size > 0);
    }

    /**
     * Test de la méthode getAllClientWhere de la classe DAOClientPostgreSQL
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testGetAllClientWhere() throws Exception {
        Client newClient = daoClientPostgreSQL.createClient("totototo@gmail.coiiii", client.getPseudo(), client.getPassword(), client.getPoids(), client.getTaille(), client.getPhoto(), client.getSexe());
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("client.id", newClient.getId()));
        whereList.add(new Pair<>("client.mail", newClient.getEmail()));
        int result = daoClientPostgreSQL.getAllClientWhere(whereList).get(0).getId();
        daoClientPostgreSQL.supprimerClientById(newClient.getId());
        Assertions.assertEquals(result, newClient.getId());
    }

    /**
     * Test de la méthode addMaterielToClient de la classe DAOClientPostgreSQL
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testAddMaterielToClient() throws Exception {
        int size = new DAOMaterielPostgreSQL().getMaterielByIdClient(clientBD.getId()).size();
        clientBD = daoClientPostgreSQL.addMaterielToClient(clientBD.getId(), materiel.getId());
        int newSize = new DAOMaterielPostgreSQL().getMaterielByIdClient(clientBD.getId()).size();
        clientBD = daoClientPostgreSQL.deleteMaterielToClient(clientBD.getId(), materiel.getId());
        Assertions.assertEquals(size + 1, newSize);
    }

    /**
     * Test de la méthode deleteMaterielToClient de la classe DAOClientPostgreSQL
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testDeleteMaterielToClient() throws Exception {
        clientBD = daoClientPostgreSQL.addMaterielToClient(clientBD.getId(), materiel.getId());
        int size = new DAOMaterielPostgreSQL().getMaterielByIdClient(clientBD.getId()).size();
        clientBD = daoClientPostgreSQL.deleteMaterielToClient(clientBD.getId(), materiel.getId());
        int newSize = new DAOMaterielPostgreSQL().getMaterielByIdClient(clientBD.getId()).size();
        Assertions.assertEquals(size - 1, newSize);
    }

    /**
     * Test de la méthode addSportToClient de la classe DAOClientPostgreSQL
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testAddSportToClient() throws Exception {
        int size = new DAOSportPostgreSQL().getSportByIdClient(clientBD.getId()).size();
        clientBD = daoClientPostgreSQL.addSportToClient(clientBD.getId(), sport.getId());
        int newSize = new DAOSportPostgreSQL().getSportByIdClient(clientBD.getId()).size();
        clientBD = daoClientPostgreSQL.deleteSportToClient(clientBD.getId(), sport.getId());
        Assertions.assertEquals(size + 1, newSize);
    }

    /**
     * Test de la méthode deleteSportToClient de la classe DAOClientPostgreSQL
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testDeleteSportToClient() throws Exception {
        clientBD = daoClientPostgreSQL.addSportToClient(clientBD.getId(), sport.getId());
        int size = new DAOSportPostgreSQL().getSportByIdClient(clientBD.getId()).size();
        clientBD = daoClientPostgreSQL.deleteSportToClient(clientBD.getId(), sport.getId());
        int newSize = new DAOSportPostgreSQL().getSportByIdClient(clientBD.getId()).size();
        Assertions.assertEquals(size - 1, newSize);
    }

    /**
     * Test de la méthode clientBecomeCoach de la classe DAOClientPostgreSQL
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testClientToCoach() throws Exception {
        int size = daoClientPostgreSQL.getAllCoach().size();
        daoClientPostgreSQL.clientBecomeCoach(clientBD.getId());
        int newSize = daoClientPostgreSQL.getAllCoach().size();
        daoClientPostgreSQL.clientBecomeCoach(clientBD.getId());
        Assertions.assertEquals(size + 1, newSize);
    }

    /**
     * Test de la méthode clientBecomeAdmin de la classe DAOClientPostgreSQL
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testClientToAdmin() throws Exception {
        int size = daoClientPostgreSQL.getAllAdmin().size();
        daoClientPostgreSQL.clientBecomeAdmin(clientBD.getId());
        int newSize = daoClientPostgreSQL.getAllAdmin().size();
        daoClientPostgreSQL.clientBecomeAdmin(clientBD.getId());
        Assertions.assertEquals(size + 1, newSize);
    }

    /**
     * Test de la méthode getAllClientForACoach de la classe DAOClientPostgreSQL
     * @throws Exception si la requête SQL échoue
     */
    @Test
    public void testGetAllClientForACoach() throws Exception {
        Client coach = daoClientPostgreSQL.getClientById(203);
        int size = daoClientPostgreSQL.getAllClientForACoach(coach.getId()).size();
        Assertions.assertTrue(size > 0);
    }

}
