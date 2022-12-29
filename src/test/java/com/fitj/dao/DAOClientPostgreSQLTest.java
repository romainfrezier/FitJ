package com.fitj.dao;

import com.fitj.classes.Client;
import com.fitj.classes.Materiel;
import com.fitj.classes.Sport;
import com.fitj.dao.postgresql.DAOClientPostgreSQL;
import com.fitj.dao.postgresql.DAOMaterielPostgreSQL;
import com.fitj.dao.postgresql.DAOSportPostgreSQL;
import com.fitj.dao.tool.PasswordAuthentication;
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
        client = new Client("test@gmail.com","Antoine",77,"testimage.com",178, Sexe.HOMME,"123456", 2);
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
    void testGetClientByEmail() throws Exception {
        Assertions.assertEquals(daoClientPostgreSQL.getClientByEmail(clientBD.getEmail()).getEmail(), clientBD.getEmail());
    }

    /**
     * Test de la méthode getClientById de la classe DAOClientPostgreSQL
     * @throws Exception si la requête SQL échoue
     */
    @Test
    void tesGetClientById() throws Exception {
        Assertions.assertEquals(daoClientPostgreSQL.getClientById(clientBD.getId()).getId(), clientBD.getId());
    }

    /**
     * Test de la méthode supprimerClientById de la classe DAOClientPostgreSQL
     * @throws Exception si la requête SQL échoue
     */
    @Test
    void testSupprimerClientById() throws Exception {
        Client newClient = daoClientPostgreSQL.createClient("totototo@gmail.coiiii", client.getPseudo(), client.getPassword(), client.getPoids(), client.getTaille(), client.getPhoto(), client.getSexe());
        daoClientPostgreSQL.supprimerClientById(newClient.getId());
        Assertions.assertThrows(Exception.class, () -> daoClientPostgreSQL.getClientById(newClient.getId()));
    }

    @Test
    void updateClientPhoto() throws Exception {
        String newPhoto = "newPhotoUpdated.jpg";
        clientBD =  daoClientPostgreSQL.updateClientPhoto(newPhoto, clientBD.getId());
        Assertions.assertEquals(clientBD.getPhoto(), newPhoto);
    }

    @Test
    void updateClientPseudo() throws Exception {
        String newPseudo = "Mon nouveau super pseudo";
        clientBD =  daoClientPostgreSQL.updateClientPseudo(newPseudo, clientBD.getId());
        Assertions.assertEquals(clientBD.getPseudo(), newPseudo);
    }

    @Test
    void updateClientPoids() throws Exception {
        double newPoids = 180.5;
        clientBD =  daoClientPostgreSQL.updateClientPoids(newPoids, clientBD.getId());
        Assertions.assertEquals(clientBD.getPoids(), newPoids);
    }

    @Test
    void updateClientTaille() throws Exception {
        int newTaille = 300;
        clientBD =  daoClientPostgreSQL.updateClientTaille(newTaille, clientBD.getId());
        Assertions.assertEquals(clientBD.getTaille(), newTaille);
    }

    @Test
    void updateClientPassword() throws Exception {
        String newPassword = "newPassword";
        clientBD =  daoClientPostgreSQL.updateClientPassword(newPassword, clientBD.getId());
        PasswordAuthentication passwordAuthentication = new PasswordAuthentication();
        Assertions.assertTrue(passwordAuthentication.authenticate(newPassword.toCharArray(), clientBD.getPassword()));
    }

    @Test
    void updateClientSexe() throws Exception {
        Sexe newSexe = Sexe.INCONNU;
        clientBD =  daoClientPostgreSQL.updateClientSexe(newSexe, clientBD.getId());
        Assertions.assertEquals(clientBD.getSexe(), newSexe);
    }

    @Test
    void updateClientMail() throws Exception {
        String newMail = "mon super nouveau mail";
        clientBD =  daoClientPostgreSQL.updateClientMail(newMail, clientBD.getId());
        Assertions.assertEquals(clientBD.getEmail(), newMail);
    }

    @Test
    void getAllClient() throws Exception {
        int size = daoClientPostgreSQL.getAllClient().size();
        Client newClient = daoClientPostgreSQL.createClient("totototo@gmail.coiiii", client.getPseudo(), client.getPassword(), client.getPoids(), client.getTaille(), client.getPhoto(), client.getSexe());
        int newSize = daoClientPostgreSQL.getAllClient().size();
        daoClientPostgreSQL.supprimerClientById(newClient.getId());
        Assertions.assertEquals(size + 1, newSize);
    }

    @Test
    void getAllCoach() throws Exception {
        int size = daoClientPostgreSQL.getAllCoach().size();
        Assertions.assertTrue(size > 0);
    }

    @Test
    void getAllAdmin() throws Exception {
        int size = daoClientPostgreSQL.getAllAdmin().size();
        Assertions.assertTrue(size > 0);
    }

    @Test
    void getAllClientWhere() throws Exception {
        Client newClient = daoClientPostgreSQL.createClient("totototo@gmail.coiiii", client.getPseudo(), client.getPassword(), client.getPoids(), client.getTaille(), client.getPhoto(), client.getSexe());
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("client.id", newClient.getId()));
        whereList.add(new Pair<>("client.mail", newClient.getEmail()));
        int result = daoClientPostgreSQL.getAllClientWhere(whereList).get(0).getId();
        daoClientPostgreSQL.supprimerClientById(newClient.getId());
        Assertions.assertEquals(result, newClient.getId());
    }

    @Test
    void addMaterielToClient() throws Exception {
        int size = new DAOMaterielPostgreSQL().getMaterielByIdClient(clientBD.getId()).size();
        clientBD = daoClientPostgreSQL.addMaterielToClient(clientBD.getId(), materiel.getId());
        int newSize = new DAOMaterielPostgreSQL().getMaterielByIdClient(clientBD.getId()).size();
        clientBD = daoClientPostgreSQL.deleteMaterielToClient(clientBD.getId(), materiel.getId());
        Assertions.assertEquals(size + 1, newSize);
    }

    @Test
    void deleteMaterielToClient() throws Exception {
        clientBD = daoClientPostgreSQL.addMaterielToClient(clientBD.getId(), materiel.getId());
        int size = new DAOMaterielPostgreSQL().getMaterielByIdClient(clientBD.getId()).size();
        clientBD = daoClientPostgreSQL.deleteMaterielToClient(clientBD.getId(), materiel.getId());
        int newSize = new DAOMaterielPostgreSQL().getMaterielByIdClient(clientBD.getId()).size();
        Assertions.assertEquals(size - 1, newSize);
    }

    @Test
    void addSportToClient() throws Exception {
        int size = new DAOSportPostgreSQL().getSportByIdClient(clientBD.getId()).size();
        clientBD = daoClientPostgreSQL.addSportToClient(clientBD.getId(), sport.getId());
        int newSize = new DAOSportPostgreSQL().getSportByIdClient(clientBD.getId()).size();
        clientBD = daoClientPostgreSQL.deleteSportToClient(clientBD.getId(), sport.getId());
        Assertions.assertEquals(size + 1, newSize);
    }

    @Test
    void deleteSportToClient() throws Exception {
        clientBD = daoClientPostgreSQL.addSportToClient(clientBD.getId(), sport.getId());
        int size = new DAOSportPostgreSQL().getSportByIdClient(clientBD.getId()).size();
        clientBD = daoClientPostgreSQL.deleteSportToClient(clientBD.getId(), sport.getId());
        int newSize = new DAOSportPostgreSQL().getSportByIdClient(clientBD.getId()).size();
        Assertions.assertEquals(size - 1, newSize);
    }

    @Test
    void testClientToCoach() throws Exception {
        int size = daoClientPostgreSQL.getAllCoach().size();
        daoClientPostgreSQL.clientBecomeCoach(clientBD.getId());
        int newSize = daoClientPostgreSQL.getAllCoach().size();
        daoClientPostgreSQL.clientBecomeCoach(clientBD.getId());
        Assertions.assertEquals(size + 1, newSize);
    }

    @Test
    void testClientToAdmin() throws Exception {
        int size = daoClientPostgreSQL.getAllAdmin().size();
        daoClientPostgreSQL.clientBecomeAdmin(clientBD.getId());
        int newSize = daoClientPostgreSQL.getAllAdmin().size();
        daoClientPostgreSQL.clientBecomeAdmin(clientBD.getId());
        Assertions.assertEquals(size + 1, newSize);
    }
}
