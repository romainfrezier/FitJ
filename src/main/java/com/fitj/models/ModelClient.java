package com.fitj.models;

import com.fitj.models.tool.PasswordAuthentication;

/**
 * Classe parente de tous les modèles client qui permettent d'intéragir avec tout type de base de données
 * pour toutes modifications de cette dernière en rapport avec les clients
 *
 * Classe abstraite non instanciable
 *
 * @author Etienne Tillier
 */
public abstract class ModelClient extends Model {

    /**
     * L'instance de l'objet qui permet de hasher les mot de passe
     * Elle permet également de savoir si un mot de passe correspond à un mot de passe hashé
     */
    protected PasswordAuthentication passwordAuthentication = new PasswordAuthentication();
    public ModelClient() {
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
     *
     */
    public abstract void createClient(String mail, String pseudo, String password, float poids, int taille, String photo) throws Exception;

    /**
     * @param mail, le mail du client
     * @return un objet contenant toutes les informations du client
     * @throws Exception, une exception en cas de problème lors de la requête SQL avec l'email rentré du client
     */
    public abstract Object getClientAccount(String mail) throws Exception;


    /**
     * @param data, la donnée que le client veut vérifier
     * @param name, le nom de la donnée
     * @return true si la donnée existe dans la table sinon false
     * @throws Exception, s'il y a eu un problème lors de la requête SQL
     */
    public abstract boolean verifier(Object data, String name) throws Exception;


}
