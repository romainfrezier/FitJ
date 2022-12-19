package com.fitj.dao.postgresql;

import com.fitj.classes.*;
import com.fitj.dao.methodesBD.MethodesPostgreSQL;
import com.fitj.dao.DAOClient;
import com.fitj.enums.Sexe;
import com.fitj.exceptions.BadLoginException;
import kotlin.Pair;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe qui permet d'intéragir avec la base de données PostgreSQL pour ce qui fait référence aux clients
 *
 * @author Etienne Tillier
 */
public class DAOClientPostgreSQL extends DAOClient {

    public DAOClientPostgreSQL(){
        super();
        this.methodesBD = new MethodesPostgreSQL();
    }

    /**
     * Créer un client dans la base de donnée avec les données rentrées en paramètre
     * @param mail     String, l'émail du client
     * @param pseudo   String, le pseudo du client
     * @param password String,  le mot de passe rentré par le client
     * @param poids    float,  le poids du client
     * @param taille   int, la taille du client
     * @param photo    String, le lien de la photo du client
     * @throws SQLException si une erreur SQL survient
     */
    public void createClient(String mail, String pseudo, String password, float poids, int taille, String photo, Sexe sexe) throws SQLException {
        List<Pair<String,Object>> data = new ArrayList<>();
        data.add(new Pair<>("mail", mail));
        data.add(new Pair<>("pseudo", pseudo));
        data.add(new Pair<>("password", passwordAuthentication.hash(password.toCharArray())));
        data.add(new Pair<>("poids", poids));
        data.add(new Pair<>("taille", taille));
        data.add(new Pair<>("photo", photo));
        data.add(new Pair<>("sexe", Sexe.getSexe(sexe)));
        ((MethodesPostgreSQL)this.methodesBD).insert(data, this.table);
    }

    /**
     * @param mail String, l'email du client
     * @return un objet de type Client contenant toutes les informations du client qui contient l'email rentré en paramètre
     * @throws BadLoginException si l'email rentré en paramètre ne correspond à aucun client
     */
    public Client getClientAccount(String mail) throws SQLException {
        ResultSet compte;
        List<Pair<String,Object>> data = new ArrayList<>();
        data.add(new Pair<>("mail", mail));
        compte = ((MethodesPostgreSQL)this.methodesBD).selectWhere(data, this.table);
        try {
            if (compte.next() == true){
                Client connectedClient;
                if (compte.getBoolean("isAdmin")){
                    connectedClient = new Admin(compte.getString("mail"), compte.getString("pseudo"), compte.getDouble("poids"), compte.getString("photo"), compte.getInt("taille"), Sexe.getSexe(compte.getString("sexe")), compte.getString("password"));
                } else if (compte.getBoolean("isCoach")){
                    connectedClient = new Coach(compte.getString("mail"), compte.getString("pseudo"), compte.getDouble("poids"), compte.getString("photo"), compte.getInt("taille"), Sexe.getSexe(compte.getString("sexe")), compte.getString("password"));
                } else {
                    connectedClient = new Client(compte.getString("mail"), compte.getString("pseudo"), compte.getDouble("poids"), compte.getString("photo"), compte.getInt("taille"), Sexe.getSexe(compte.getString("sexe")), compte.getString("password"));
                }
                connectedClient.setListeCommande(this.getClientCommandes(compte.getInt("id")));
                connectedClient.setListeMateriel(this.getClientMateriel(compte.getInt("id")));
                connectedClient.setListeSport(this.getClientSport(compte.getInt("id")));
                return connectedClient;
            }
            else {
                return null;
            }
        }
        catch (SQLException e){
            throw new SQLException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param id int, l'id du client
     * @return la liste de matériel du client
     * @throws Exception
     */
    @Override
    public List<Materiel> getClientMateriel(int id) throws Exception {
        return null;
    }

    /**
     * @param id int, l'id du client
     * @return la liste de commandes du client
     * @throws Exception
     */
    @Override
    public List<Commande> getClientCommandes(int id) throws Exception {
        return null;
    }

    /**
     * @param id int, l'id du client
     * @return la liste des sport du client
     * @throws Exception
     */
    @Override
    public List<Sport> getClientSport(int id) throws Exception {
        return null;
    }

    /**
     * @param data Object, le contenu de l'attribut à vérifier
     * @param name String, le nom de l'attribut à vérifier
     * @return true si l'attribut est présent dans la table client sinon return false
     * @throws Exception
     */
    public boolean verifier(Object data,String name) throws Exception {
        return (((MethodesPostgreSQL)this.methodesBD).exist(data, name, this.table));
    }



}
