package com.fitj.dao;

import com.fitj.classes.Client;
import com.fitj.classes.Commande;
import com.fitj.classes.Materiel;
import com.fitj.classes.Sport;
import com.fitj.dao.tool.PasswordAuthentication;
import com.fitj.enums.Sexe;
import kotlin.Pair;

import java.util.List;

/**
 * Classe parente de tous les modèles client qui permettent d'intéragir avec tout type de base de données
 * pour toutes modifications de cette dernière en rapport avec les clients
 *
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
     * @throws Exception
     */
    public abstract void createClient(String mail, String pseudo, String password, double poids, int taille, String photo, Sexe sexe) throws Exception;


    /**
     * @param mail, le mail du client
     * @return un objet contenant toutes les informations du client
     * @throws Exception, une exception en cas de problème lors de la requête SQL avec l'email rentré du client
     */
    public abstract Client getClientAccount(String mail) throws Exception;

    /**
     * @param id, l'id du client
     * @return un objet contenant toutes les informations du client
     * @throws Exception, une exception en cas de problème lors de la requête SQL avec l'id rentré du client
     */
    public abstract Client getClientAccount(int id) throws Exception;

    /**
     * Supprimer le client de la base de donnée
     * @param mail, le mail du client
     * @throws Exception
     */
    public abstract void supprimerClient(String mail) throws Exception;

    /**
     * Met à jour le client de la base de donnée
     * @param mail, le mail du client
     * @throws Exception
     */
    public abstract Client updateClient(List<Pair<String,Object>> data, String mail) throws Exception;

    /**
     * Met à jour la photo du client dans la base de donnée
     * @param photo, la photo du client
     * @param mail, le mail du client
     * @throws Exception
     */
    public abstract Client updateClientPhoto(String photo, String mail) throws Exception;

    /**
     * Met à jour le pseudo du client dans la base de donnée
     * @param pseudo, le pseudo du client
     * @param mail, le mail du client
     * @throws Exception
     */
    public abstract Client updateClientPseudo(String pseudo, String mail) throws Exception;

    /**
     * Met à jour le poids du client dans la base de donnée
     * @param poids, le poids du client
     * @param mail, le mail du client
     * @throws Exception
     */
    public abstract Client updateClientPoids(double poids, String mail) throws Exception;

    /**
     * Met à jour la taille du client dans la base de donnée
     * @param taille, la taille du client
     * @param mail, le mail du client
     * @throws Exception
     */
    public abstract Client updateClientTaille(int taille, String mail) throws Exception;

    /**
     * Met à jour le password du client dans la base de donnée
     * @param password, le password du client
     * @param mail, le mail du client
     * @throws Exception
     */
    public abstract Client updateClientPassword(String password, String mail) throws Exception;

    /**
     * @param id int, l'id du client
     * @return la liste de matériel du client
     * @throws Exception
     */
    public abstract List<Materiel> getClientMateriel(int id) throws Exception;

    /**
     * @param id int, l'id du client
     * @return la liste de commandes du client
     * @throws Exception
     */
    public abstract List<Commande> getClientCommandes(int id) throws Exception;

    /**
     * @param id int, l'id du client
     * @return la liste des sport du client
     * @throws Exception
     */
    public abstract List<Sport> getClientSport(int id) throws Exception;


    /**
     * @param data, la donnée que le client veut vérifier
     * @param name, le nom de la donnée
     * @return true si la donnée existe dans la table sinon false
     * @throws Exception, s'il y a eu un problème lors de la requête SQL
     */
    public abstract boolean verifier(Object data, String name) throws Exception;


}
