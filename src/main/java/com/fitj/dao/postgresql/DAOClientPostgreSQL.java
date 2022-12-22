package com.fitj.dao.postgresql;

import com.fitj.classes.*;
import com.fitj.dao.methodesBD.MethodesPostgreSQL;
import com.fitj.dao.DAOClient;
import com.fitj.enums.Sexe;
import com.fitj.exceptions.DBProblemException;
import kotlin.Pair;
import kotlin.Triple;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe qui permet d'intéragir avec la base de données PostgreSQL pour ce qui fait référence aux clients
 *
 * @author Etienne Tillier, Romain Frezier
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
     * @param password String, le mot de passe rentré par le client
     * @param poids    float, le poids du client
     * @param taille   int, la taille du client
     * @param photo    String, le lien de la photo du client
     * @throws Exception si une erreur SQL survient
     */
    @Override
    public Client createClient(String mail, String pseudo, String password, double poids, int taille, String photo, Sexe sexe) throws Exception {
        List<Pair<String,Object>> data = new ArrayList<>();
        data.add(new Pair<>("mail", mail));
        data.add(new Pair<>("pseudo", pseudo));
        data.add(new Pair<>("password", passwordAuthentication.hash(password.toCharArray())));
        data.add(new Pair<>("poids", poids));
        data.add(new Pair<>("taille", taille));
        data.add(new Pair<>("photo", photo));
        data.add(new Pair<>("sexe", Sexe.getSexe(sexe)));
        try {
            int id = ((MethodesPostgreSQL)this.methodesBD).insert(data, this.table);
            return getClientAccount(id);
        }
        catch (Exception e){
            throw new DBProblemException("La création du client a échoué");
        }
    }

    /**
     * Fais le choix du role du client entre client, coach ou admin
     * @param compte ResultSet, le résultat de la requête SQL
     * @return Client, le client avec le bon role correspondant au résultat de la requête SQL
     * @throws Exception si une erreur SQL survient
     */
    private Client chooseRole(ResultSet compte) throws Exception {
        Client connectedClient;
        if (compte.getBoolean("isAdmin")){
            connectedClient = new Admin(compte.getString("mail"), compte.getString("pseudo"), compte.getDouble("poids"), compte.getString("photo"), compte.getInt("taille"), Sexe.getSexe(compte.getString("sexe")), compte.getString("password"), compte.getInt("id"));
        } else if (compte.getBoolean("isCoach")){
            connectedClient = new Coach(compte.getString("mail"), compte.getString("pseudo"), compte.getDouble("poids"), compte.getString("photo"), compte.getInt("taille"), Sexe.getSexe(compte.getString("sexe")), compte.getString("password"), compte.getInt("id"));
        } else {
            connectedClient = new Client(compte.getString("mail"), compte.getString("pseudo"), compte.getDouble("poids"), compte.getString("photo"), compte.getInt("taille"), Sexe.getSexe(compte.getString("sexe")), compte.getString("password"), compte.getInt("id"));
        }
        return connectedClient;
    }

    /**
     * @param mail String, l'email du client
     * @return un objet de type Client contenant toutes les informations du client qui contient l'email rentré en paramètre
     * @throws Exception si une erreur SQL survient
     */
    public Client getClientAccount(String mail) throws Exception {
        ResultSet compte;
        List<Pair<String,Object>> data = new ArrayList<>();
        data.add(new Pair<>("mail", mail));
        compte = ((MethodesPostgreSQL)this.methodesBD).selectWhere(data, this.table);
        try {
            if (compte.next() == true){
                Client client = chooseRole(compte);
                client.setListeCommande(new ArrayList<>());
                client.setListeMateriel(this.getClientMateriel(client.getId()));
                client.setListeSport(new ArrayList<>());
                //client.setListeCommande(this.getClientCommandes(compte.getInt("id")));
                //client.setListeMateriel(this.getClientMateriel(compte.getInt("id")));
                //client.setListeSport(this.getClientSport(compte.getInt("id")));
                return client;
            }
            else {
                throw new DBProblemException("Aucun client avec cet email n'existe");
            }
        }
        catch (Exception e){
            throw new DBProblemException("La sélection du client a échoué");
        }
    }

    /**
     * @param id int, l'id du client
     * @return un objet de type Client contenant toutes les informations du client qui contient l'id rentré en paramètre
     * @throws Exception si une erreur SQL survient
     */
    public Client getClientAccount(int id) throws Exception {
        ResultSet compte;
        List<Pair<String,Object>> data = new ArrayList<>();
        data.add(new Pair<>("id", id));
        compte = ((MethodesPostgreSQL)this.methodesBD).selectWhere(data, this.table);
        try {
            if (compte.next() == true){
                Client client = chooseRole(compte);
                client.setListeCommande(new ArrayList<>());
                client.setListeMateriel(this.getClientMateriel(client.getId()));
                client.setListeSport(new ArrayList<>());
                //client.setListeCommande(this.getClientCommandes(compte.getInt("id")));
                //client.setListeMateriel(this.getClientMateriel(compte.getInt("id")));
                //client.setListeSport(this.getClientSport(compte.getInt("id")));
                return client;
            }
            else {
                throw new DBProblemException("Aucun client avec cet id n'existe");
            }
        }
        catch (Exception e){
            throw new DBProblemException("La sélection du client a échoué");
        }
    }

    /**
     * Supprimer le client de la base de donnée
     * @param mail, le mail du client
     * @throws Exception si une erreur SQL survient
     */
    public void supprimerClientByMail(String mail) throws Exception{
        List<Pair<String,Object>> wherelist = new ArrayList<>();
        wherelist.add(new Pair<>("mail", mail));
        try {
            ((MethodesPostgreSQL)this.methodesBD).delete(wherelist, this.table);
        }
        catch(Exception e){
            throw new DBProblemException("La suppresion du client a échoué");
        }
    }

    /**
     * Supprimer le client de la base de donnée
     * @param id int, l'id du client
     * @throws Exception si une erreur SQL survient
     */
    public void supprimerClientById(int id) throws Exception{
        List<Pair<String,Object>> wherelist = new ArrayList<>();
        wherelist.add(new Pair<>("id", id));
        try {
            ((MethodesPostgreSQL)this.methodesBD).delete(wherelist, this.table);
        }
        catch(Exception e){
            throw new DBProblemException("La suppresion du client a échoué");
        }
    }

    /**
     * Met à jour le client de la base de donnée
     * @param mail, le mail du client
     * @throws Exception si une erreur SQL survient
     */
    public Client updateClient(List<Pair<String,Object>> data, String mail) throws Exception{
        List<Pair<String,Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("mail",mail));
        try {
            ((MethodesPostgreSQL)this.methodesBD).update(data,whereList,this.table);
            return this.getClientAccount(mail);
        }
        catch (Exception e){
            throw new DBProblemException("La mise à jour du client a échoué");
        }
    }

    /**
     * Met à jour la photo du client dans la base de donnée
     * @param photo, la photo du client
     * @param mail, le mail du client
     * @throws Exception si une erreur SQL survient
     */
    @Override
    public Client updateClientPhoto(String photo, String mail) throws Exception {
        List<Pair<String,Object>> updateList = new ArrayList<>();
        updateList.add(new Pair<>("photo",photo));
        try {
            return this.updateClient(updateList, mail);
        }
        catch (Exception e){
            throw new DBProblemException("La mise à jour de la photo du client a échoué");
        }
    }

    /**
     * Met à jour le pseudo du client dans la base de donnée
     * @param pseudo, le pseudo du client
     * @param mail, le mail du client
     * @throws Exception si une erreur SQL survient
     */
    @Override
    public Client updateClientPseudo(String pseudo, String mail) throws Exception {
        List<Pair<String,Object>> updateList = new ArrayList<>();
        updateList.add(new Pair<>("pseudo",pseudo));
        try {
            return this.updateClient(updateList, mail);
        }
        catch (Exception e){
            throw new DBProblemException("La mise à jour du pseudo du client a échoué");
        }
    }

    /**
     * Met à jour le poids du client dans la base de donnée
     * @param poids, le poids du client
     * @param mail, le mail du client
     * @throws Exception si une erreur SQL survient
     */
    @Override
    public Client updateClientPoids(double poids, String mail) throws Exception {
        List<Pair<String,Object>> updateList = new ArrayList<>();
        updateList.add(new Pair<>("poids",poids));
        try {
            return this.updateClient(updateList, mail);
        }
        catch (Exception e){
            throw new DBProblemException("La mise à jour du poids du client a échoué");
        }
    }

    /**
     * Met à jour la taille du client dans la base de donnée
     * @param taille, la taille du client
     * @param mail, le mail du client
     * @throws Exception si une erreur SQL survient
     */
    @Override
    public Client updateClientTaille(int taille, String mail) throws Exception {
        List<Pair<String,Object>> updateList = new ArrayList<>();
        updateList.add(new Pair<>("taille",taille));
        try {
            return this.updateClient(updateList, mail);
        }
        catch (Exception e){
            throw new DBProblemException("La mise à jour de la taille du client a échoué");
        }
    }

    /**
     * Met à jour le password du client dans la base de donnée
     * @param password, le password du client
     * @param mail, le mail du client
     * @throws Exception si une erreur SQL survient
     */
    @Override
    public Client updateClientPassword(String password, String mail) throws Exception {
        List<Pair<String,Object>> updateList = new ArrayList<>();
        updateList.add(new Pair<>("password",passwordAuthentication.hash(password.toCharArray())));
        try {
            return this.updateClient(updateList, mail);
        }
        catch (Exception e){
            throw new DBProblemException("La mise à jour du mot de passe du client a échoué");
        }
    }

    /**
     * @param id int, l'id du client
     * @return la liste de matériel du client
     * @throws Exception si une erreur SQL survient
     */
    @Override
    public List<Materiel> getClientMateriel(int id) throws Exception {
        List<Triple<String,String,String>> dataJoin = new ArrayList<>();
        dataJoin.add(new Triple<>("clientMateriel", "idMateriel", "materiel.id"));
        List<Pair<String,Object>> dataWhere = new ArrayList<>();
        dataWhere.add(new Pair<>("clientMateriel.idClient",id));
        try {
            ResultSet result = ((MethodesPostgreSQL)this.methodesBD).selectJoin(dataJoin,dataWhere,"Materiel");
            List<Materiel> listeMateriel = new ArrayList<>();
            if (result.next() == true){
                do {
                    listeMateriel.add(new Materiel(result.getInt("id"), result.getString("nom")));
                }while (result.next());
            }
            return listeMateriel;
        }
        catch (Exception e){
            throw new DBProblemException("La selection du matériel du client a échoué");
        }
    }


    /**
     * @param id int, l'id du client
     * @return la liste de commandes du client
     * @throws Exception si une erreur SQL survient
     */
    @Override
    public List<Commande> getClientCommandes(int id) throws Exception {
        List<Triple<String,String,String>> dataJoin = new ArrayList<>();
        dataJoin.add(new Triple<>("clientCommande", "idCommande", "commande.id"));
        List<Pair<String,Object>> dataWhere = new ArrayList<>();
        dataWhere.add(new Pair<>("clientCommande.idClient",id));
        try {
            ResultSet result = ((MethodesPostgreSQL)this.methodesBD).selectJoin(dataJoin,dataWhere,"Commande");
            List<Commande> listeCommande = new ArrayList<>();
            if (result.next() == true){
                do {
                    Commande commande;
                    Coach coach = (Coach)this.getClientAccount(result.getInt("clientCommande.idCoach"));
                    Client client = this.getClientAccount(id);
                    if (((Integer)result.getInt("prix")) != null){
                        //     commande = new CommandePayante();
                    }
                    else {
                        //   commande = new CommandeNonPayante();
                    }

                    //listeCommande.add(new Commande(this.getClientAccount(result.getInt("clientcommande.idclient")));
                }while (result.next());
            }
            return listeCommande;
        }
        catch (Exception e){
            throw new DBProblemException("La sélection des commandes du client a échoué");
        }

    }

    /**
     * @param id int, l'id du client
     * @return la liste des sports du client
     * @throws Exception si une erreur SQL survient
     */
    @Override
    public List<Sport> getClientSport(int id) throws Exception {
        return null;
    }

    /**
     * @param data Object, le contenu de l'attribut à vérifier
     * @param name String, le nom de l'attribut à vérifier
     * @return true si l'attribut est présent dans la table client sinon return false
     * @throws Exception si une erreur SQL survient
     */
    public boolean verifier(Object data,String name) throws Exception {
        return (((MethodesPostgreSQL)this.methodesBD).exist(data, name, this.table));
    }



}
