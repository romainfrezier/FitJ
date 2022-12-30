package com.fitj.dao;

import com.fitj.classes.*;
import com.fitj.dao.tool.PasswordAuthentication;
import com.fitj.enums.Sexe;
import kotlin.Pair;

import java.util.List;

/**
 * Classe parente de tous les modèles client qui permettent d'intéragir avec tout type de base de données
 * pour toutes modifications de cette dernière en rapport avec les clients
 * Classe abstraite non instanciable
 *
 * @author Etienne Tillier
 */
public abstract class DAOClient extends DAO {

    /**
     * L'instance de l'objet qui permet de hasher les mot de passe
     * Elle permet également de savoir si un mot de passe correspond à un mot de passe hashé
     */
    protected PasswordAuthentication passwordAuthentication = new PasswordAuthentication();
    public DAOClient() {
        super("client");
    }

    /**
     * Créer un client dans la base de donnée avec les données rentrées en paramètre
     *
     * @param mail String, l'émail du client
     * @param pseudo String, le pseudo du client
     * @param password String,  le mot de passe rentré par le client
     * @param poids float,  le poids du client
     * @param taille int, la taille du client
     * @param photo String, le lien de la photo du client
     * @throws Exception si une erreur survient lors de la création du client
     */
    public abstract Client createClient(String mail, String pseudo, String password, double poids, int taille, String photo, Sexe sexe) throws Exception;


    /**
     * @param mail, le mail du client
     * @return un objet contenant toutes les informations du client
     * @throws Exception, une exception en cas de problème lors de la requête SQL avec l'email rentré du client
     */
    public abstract Client getClientByEmail(String mail) throws Exception;


    /**
     * @param id, l'id du client
     * @return un objet contenant toutes les informations du client
     * @throws Exception, une exception en cas de problème lors de la requête SQL avec l'id rentré du client
     */
    public abstract Client getClientById(int id) throws Exception;

    /**
     * Supprimer le client de la base de donnée
     * @param mail, le mail du client
     * @throws Exception si une erreur survient lors de la suppression du client
     */
    public abstract void supprimerClientByMail(String mail) throws Exception;

    /**
     * Supprimer le client de la base de donnée
     * @param id int, l'id du client
     * @throws Exception si une erreur survient lors de la suppression du client
     */
    public abstract void supprimerClientById(int id) throws Exception;

    /**
     * Met à jour le client de la base de donnée
     * @param id int, l'id du client
     * @param data List<Pair<String,Object>>, la liste des champs à mettre à jour
     * @throws Exception si une erreur survient lors de la requête SQL
     */
    public abstract Client updateClient(List<Pair<String,Object>> data, int id) throws Exception;

    /**
     * Met à jour la photo du client dans la base de donnée
     * @param photo, la photo du client
     * @param id int, l'id du client
     * @throws Exception si une erreur survient lors de la requête SQL
     */
    public abstract Client updateClientPhoto(String photo, int id) throws Exception;

    /**
     * Met à jour le pseudo du client dans la base de donnée
     * @param pseudo, le pseudo du client
     * @param id int, l'id du client
     * @throws Exception si une erreur survient lors de la requête SQL
     */
    public abstract Client updateClientPseudo(String pseudo, int id) throws Exception;

    /**
     * Met à jour le poids du client dans la base de donnée
     * @param poids, le poids du client
     * @param id int, l'id du client
     * @throws Exception si une erreur survient lors de la requête SQL
     */
    public abstract Client updateClientPoids(double poids, int id) throws Exception;

    /**
     * Met à jour la taille du client dans la base de donnée
     * @param taille, la taille du client
     * @param id int, l'id du client
     * @throws Exception si une erreur survient lors de la requête SQL
     */
    public abstract Client updateClientTaille(int taille, int id) throws Exception;

    /**
     * Met à jour le password du client dans la base de donnée
     * @param password, le password du client
     * @param id int, l'id du client
     * @throws Exception si une erreur survient lors de la requête SQL
     */
    public abstract Client updateClientPassword(String password, int id) throws Exception;

    /**
     * Modifie le sexe du client
     * @param sexe Sexe, le sexe du client
     * @param id int, l'id du client
     * @return Client, le client modifié
     * @throws Exception en cas de problème lors de la requête SQL
     */
    public abstract Client updateClientSexe(Sexe sexe, int id) throws Exception;

    /**
     * Modifie le mail du client
     * @param mail String, le mail du client
     * @param id int, l'id du client
     * @return Client, le client modifié
     * @throws Exception en cas de problème lors de la requête SQL
     */
    public abstract Client updateClientMail(String mail, int id) throws Exception;

    /**
     * Récupère tous les clients de la base de donnée
     * @return List<Client>, la liste des clients
     * @throws Exception en cas de problème lors de la requête SQL
     */
    public abstract List<Client> getAllClient() throws Exception;

    /**
     * Récupère tous les coachs de la base de donnée
     * @return List<Coach>, la liste des coachs
     * @throws Exception en cas de problème lors de la requête SQL
     */
    public abstract List<Coach> getAllCoach() throws Exception;

    /**
     * Récupère tous les admins de la base de donnée
     * @return List<Admin>, la liste des admins
     * @throws Exception en cas de problème lors de la requête SQL
     */
    public abstract List<Admin> getAllAdmin() throws Exception;

    /**
     * Récupère tous les clients en respectant certains critères
     * @param whereList List<Pair<String,Object>>, la liste des critères
     * @return List<Client>, la liste des clients
     * @throws Exception en cas de problème lors de la requête SQL
     */
    public abstract List<Client> getAllClientWhere(List<Pair<String,Object>> whereList) throws Exception;

    /**
     * Ajoute un matériel au client
     * @param idClient int, l'id du client
     * @param idMateriel int, l'id du matériel
     * @return Client, le client modifié
     * @throws Exception en cas de problème lors de la requête SQL
     */
    public abstract Client addMaterielToClient(int idClient, int idMateriel) throws Exception;

    /**
     * Supprime un matériel au client
     * @param idClient int, l'id du client
     * @param idMateriel int, l'id du matériel
     * @return Client, le client modifié
     * @throws Exception en cas de problème lors de la requête SQL
     */
    public abstract Client deleteMaterielToClient(int idClient, int idMateriel) throws Exception;

    /**
     * Ajoute un sport au client
     * @param idClient int, l'id du client
     * @param idSport int, l'id du sport
     * @return Client, le client modifié
     * @throws Exception en cas de problème lors de la requête SQL
     */
    public abstract Client addSportToClient(int idClient, int idSport) throws Exception;

    /**
     * Supprime un sport au client
     * @param idClient int, l'id du client
     * @param idSport int, l'id du sport
     * @return Client, le client modifié
     * @throws Exception en cas de problème lors de la requête SQL
     */
    public abstract Client deleteSportToClient(int idClient, int idSport) throws Exception;

    /**
     * Permet à un client de devenir coach
     * @param idClient int, l'id du client
     * @return Coach, le coach créé
     * @throws Exception en cas de problème lors de la requête SQL
     */
    public abstract Coach clientBecomeCoach(int idClient) throws Exception;

    /**
     * Permet à un client de devenir admin
     * @param idClient int, l'id du client
     * @return Admin, le coach créé
     * @throws Exception en cas de problème lors de la requête SQL
     */
    public abstract Admin clientBecomeAdmin(int idClient) throws Exception;

    /**
     * Permet à un admin de ban un client
     * @param client Client, client à bannir
     * @return Client, le client créé
     * @throws Exception en cas de problème lors de la requête SQL
     */
    public abstract Client banClient(Client client) throws Exception;

    /**
     * Permet de récupérer tous les clients d'un coach
     * @param coachId int, l'id du coach
     * @return List<Client>, la liste des clients du coach
     * @throws Exception en cas de problème lors de la requête SQL
     */
    public abstract List<Client> getAllClientForACoach(int coachId) throws Exception;
}
