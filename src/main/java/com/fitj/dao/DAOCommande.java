package com.fitj.dao;

import com.fitj.classes.Commande;
import com.fitj.classes.Demande;
import com.fitj.classes.Produit;
import com.fitj.enums.PaiementType;
import kotlin.Pair;

import java.util.List;

/**
 * Classe parente de tous les modèles commande qui permettent d'intéragir avec tout type de base de données
 * pour toutes modifications de cette dernière en rapport avec les commandes
 * @see DAO
 * @author Etienne Tillier, Romain Frezier
 */
public abstract class DAOCommande extends DAO {
    public DAOCommande() {
        super("commande");
    }

    /**
     * Ajoute une commande dans la base de donnée
     * @param client int, l'id du client
     * @param coach int, l'id du coach
     * @param produit Produit, le produit associé à la commande
     * @param paiementType PaiementType, le type de paiement
     * @throws Exception si une erreur SQL survient
     * @see Produit
     * @see PaiementType
     */
    public abstract Commande createCommande(int client, int coach, Produit produit, PaiementType paiementType) throws Exception;

    /**
     * Ajoute une commande dans la base de donnée
     * @param client int, l'id du client
     * @param coach int, l'id du coach
     * @param produit Produit, le produit associé à la commande
     * @param paiementType PaiementType, le type de paiement
     * @param demande Demande, la demande associée à la commande
     * @throws Exception si une erreur SQL survient
     * @see Produit
     * @see PaiementType
     */
    public abstract Commande createCommande(int client, int coach, Produit produit, PaiementType paiementType, Demande demande) throws Exception;

    /**
     * @param id int, l'id de la commande
     * @return un objet de type Commande dans la base de donnée avec l'id rentré en paramètre
     * @throws Exception si une erreur SQL est rencontrée
     */
    public abstract Commande getCommandeById(int id) throws Exception;

    /**
     * @param client int, l'id du client
     * @return un objet de type Commande dans la base de donnée avec l'id du client rentré en paramètre
     * @throws Exception si une erreur SQL est rencontrée
     */
    public abstract Commande getCommandeByIdClient(int client) throws Exception;

    /**
     * @param coach int, l'id du coach
     * @return un objet de type Commande dans la base de donnée avec l'id du coach rentré en paramètre
     * @throws Exception si une erreur SQL est rencontrée
     */
    public abstract Commande getCommandeByIdCoach(int coach) throws Exception;

    /**
     * @param produit int, l'id du produit
     * @return un objet de type Commande dans la base de donnée avec l'id du produit rentré en paramètre
     * @throws Exception si une erreur SQL est rencontrée
     */
    public abstract Commande getCommandeByIdProduit(int produit) throws Exception;

    /**
     * @return List<Commande>, liste de toutes les commandes présentes dans la base de donnée
     * @throws Exception si une erreur SQL est rencontrée
     */
    public abstract List<Commande> getAllCommande() throws Exception;

    /**
     * @param whereList List<Pair<String,Object>>, la liste des conditions du where pour la requête
     * @return List<Commande>, liste de toutes les commandes présentes dans la base de donnée
     * @throws Exception si une erreur SQL est rencontrée
     */
    public abstract List<Commande> getAllCommandeWhere(List<Pair<String,Object>> whereList) throws Exception;

    /**
     * @param id int, l'id de la commande
     * @throws Exception si une erreur SQL est rencontrée
     */
    public abstract void deleteCommande(int id) throws Exception;

    /**
     * @param udpateList List<Pair<String,Object>>, la liste des modifications à effectuer
     * @param id int, l'id de la commande à modifier
     * @return Commande, la commande modifiée
     * @throws Exception si une erreur SQL est rencontrée
     */
    public abstract Commande updateCommande(List<Pair<String,Object>> udpateList, int id) throws Exception;
}
