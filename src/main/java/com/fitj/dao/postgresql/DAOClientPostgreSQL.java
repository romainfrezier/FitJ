package com.fitj.dao.postgresql;

import com.fitj.classes.Client;
import com.fitj.classes.Commande;
import com.fitj.classes.Materiel;
import com.fitj.classes.Sport;
import com.fitj.dao.methodesBD.MethodesPostgreSQL;
import com.fitj.dao.DAOClient;
import com.fitj.enums.Sexe;
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
     * @throws SQLException
     */
    @Override
    public void createClient(String mail, String pseudo, String password, double poids, int taille, String photo, Sexe sexe) throws SQLException {
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
     * @throws SQLException
     */
    public Client getClientAccount(String mail) throws SQLException {
        ResultSet compte;
        List<Pair<String,Object>> data = new ArrayList<>();
        data.add(new Pair<>("mail", mail));
        compte = ((MethodesPostgreSQL)this.methodesBD).selectWhere(data, this.table);
        try {
            if (compte.next() == true){
                Client client = new Client(compte.getString("mail"), compte.getString("pseudo"), compte.getDouble("poids"), compte.getString("photo"), compte.getInt("taille"), Sexe.getSexe(compte.getString("sexe")), compte.getString("password"), compte.getInt("id"));
                 client.setListeCommande(this.getClientCommandes(compte.getInt("id")));
                 client.setListeMateriel(this.getClientMateriel(compte.getInt("id")));
                 client.setListeSport(this.getClientSport(compte.getInt("id")));
                return client;
            }
            else {
                return null;
            }
        }
        catch (SQLException e){
            System.out.println("Email non existant");
            throw new SQLException();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Supprimer le client de la base de donnée
     * @param mail, le mail du client
     * @throws Exception
     */
    public void supprimerClient(String mail) throws SQLException{
        List<Pair<String,Object>> wherelist = new ArrayList<>();
        wherelist.add(new Pair<>("mail", mail));
        ((MethodesPostgreSQL)this.methodesBD).delete(wherelist, this.table);
    }

    /**
     * Met à jour le client de la base de donnée
     * @param mail, le mail du client
     * @throws Exception
     */
    public Client updateClient(List<Pair<String,Object>> data, String mail) throws SQLException{
        List<Pair<String,Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("mail",mail));
        ((MethodesPostgreSQL)this.methodesBD).update(data,whereList,this.table);
        return this.getClientAccount(mail);
    }

    /**
     * Met à jour la photo du client dans la base de donnée
     * @param photo, la photo du client
     * @param mail, le mail du client
     * @throws Exception
     */
    @Override
    public Client updateClientPhoto(String photo, String mail) throws Exception {
        List<Pair<String,Object>> updateList = new ArrayList<>();
        updateList.add(new Pair<>("photo",photo));
        return this.updateClient(updateList, mail);
    }

    /**
     * Met à jour le pseudo du client dans la base de donnée
     * @param pseudo, le pseudo du client
     * @param mail, le mail du client
     * @throws Exception
     */
    @Override
    public Client updateClientPseudo(String pseudo, String mail) throws Exception {
        List<Pair<String,Object>> updateList = new ArrayList<>();
        updateList.add(new Pair<>("pseudo",pseudo));
        return this.updateClient(updateList, mail);
    }

    /**
     * Met à jour le poids du client dans la base de donnée
     * @param poids, le poids du client
     * @param mail, le mail du client
     * @throws Exception
     */
    @Override
    public Client updateClientPoids(double poids, String mail) throws Exception {
        List<Pair<String,Object>> updateList = new ArrayList<>();
        updateList.add(new Pair<>("poids",poids));
        return this.updateClient(updateList, mail);
    }

    /**
     * Met à jour la taille du client dans la base de donnée
     * @param taille, la taille du client
     * @param mail, le mail du client
     * @throws Exception
     */
    @Override
    public Client updateClientTaille(int taille, String mail) throws Exception {
        List<Pair<String,Object>> updateList = new ArrayList<>();
        updateList.add(new Pair<>("taille",taille));
        return this.updateClient(updateList, mail);
    }

    /**
     * Met à jour le password du client dans la base de donnée
     * @param password, le password du client
     * @param mail, le mail du client
     * @throws Exception
     */
    @Override
    public Client updateClientPassword(String password, String mail) throws Exception {
        List<Pair<String,Object>> updateList = new ArrayList<>();
        updateList.add(new Pair<>("password",passwordAuthentication.hash(password.toCharArray())));
        return this.updateClient(updateList, mail);
    }

    /**
     * @param id int, l'id du client
     * @return la liste de matériel du client
     * @throws Exception
     */
    @Override
    public List<Materiel> getClientMateriel(int id) throws SQLException {
        List<Pair<String,String>> dataJoin = new ArrayList<>();
        dataJoin.add(new Pair<>("clientMateriel", "idMateriel"));
        List<Pair<String,Object>> dataWhere = new ArrayList<>();
        dataWhere.add(new Pair<>("clientMateriel.idClient",id));
        ResultSet result = ((MethodesPostgreSQL)this.methodesBD).selectJoin(dataJoin,dataWhere,"Materiel");
        try {
            List<Materiel> listeMateriel = new ArrayList<>();
            if (result.next() == true){
                do {
                    listeMateriel.add(new Materiel(result.getInt("id"), result.getString("nom")));
                }while (result.next());
                return listeMateriel;
            }
            else {
                return listeMateriel;
            }
        }
        catch (SQLException e){
            throw new SQLException();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
