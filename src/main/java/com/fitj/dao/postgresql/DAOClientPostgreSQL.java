package com.fitj.dao.postgresql;

import com.fitj.classes.*;
import com.fitj.dao.DAOMateriel;
import com.fitj.dao.DAOSport;
import com.fitj.dao.factory.FactoryDAO;
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
            return getClientById(id);
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
        if (compte.getBoolean("isadmin")){
            connectedClient = new Admin(compte.getString("mail"), compte.getString("pseudo"), compte.getDouble("poids"), compte.getString("photo"), compte.getInt("taille"), Sexe.getSexe(compte.getString("sexe")), compte.getString("password"), compte.getInt("id"));
        } else if (compte.getBoolean("iscoach")){
            connectedClient = new Coach(compte.getString("mail"), compte.getString("pseudo"), compte.getDouble("poids"), compte.getString("photo"), compte.getInt("taille"), Sexe.getSexe(compte.getString("sexe")), compte.getString("password"), compte.getInt("id"));
        } else {
            connectedClient = new Client(compte.getString("mail"), compte.getString("pseudo"), compte.getDouble("poids"), compte.getString("photo"), compte.getInt("taille"), Sexe.getSexe(compte.getString("sexe")), compte.getString("password"), compte.getInt("id"));
        }
        return connectedClient;
    }

    /**
     * Permet de mettre à jour les liste de sport, materiel et commande d'un client
     *
     * @param client Client, le client à mettre à jour
     * @param rs     ResultSet, le résultat de la requête SQL
     * @throws Exception si une erreur SQL survient
     */
    private void setClientList(Client client, ResultSet rs) throws Exception {
        client.setListeCommande(FactoryDAO.getInstance().getDAOCommande().getCommandeByIdClient(rs.getInt("id")));
        client.setListeMateriel(FactoryDAO.getInstance().getDAOMateriel().getMaterielByIdClient(rs.getInt("id")));
        client.setListeSport(FactoryDAO.getInstance().getDAOSport().getSportByIdClient(rs.getInt("id")));
    }

    @Override
    public Client getClientByEmail(String mail) throws Exception {
        ResultSet compte;
        List<Pair<String,Object>> data = new ArrayList<>();
        data.add(new Pair<>("mail", mail));
        compte = ((MethodesPostgreSQL)this.methodesBD).selectWhere(data, this.table);
        try {
            if (compte.next()){
                Client client = chooseRole(compte);
                setClientList(client, compte);
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

    @Override
    public Client getClientById(int id) throws Exception {
        ResultSet compte;
        List<Pair<String,Object>> data = new ArrayList<>();
        data.add(new Pair<>("id", id));
        compte = ((MethodesPostgreSQL)this.methodesBD).selectWhere(data, this.table);
        try {
            if (compte.next()){
                Client client = chooseRole(compte);
                setClientList(client, compte);
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



    @Override
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

    @Override
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

    @Override
    public Client updateClient(List<Pair<String,Object>> data, int id) throws Exception{
        List<Pair<String,Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("id",id));
        try {
            ((MethodesPostgreSQL)this.methodesBD).update(data,whereList,this.table);
            return this.getClientById(id);
        }
        catch (Exception e){
            throw new DBProblemException("La mise à jour du client a échoué");
        }
    }

    @Override
    public Client updateClientPhoto(String photo, int id) throws Exception {
        List<Pair<String,Object>> updateList = new ArrayList<>();
        updateList.add(new Pair<>("photo",photo));
        try {
            return this.updateClient(updateList, id);
        }
        catch (Exception e){
            throw new DBProblemException("La mise à jour de la photo du client a échoué");
        }
    }

    @Override
    public Client updateClientPseudo(String pseudo, int id) throws Exception {
        List<Pair<String,Object>> updateList = new ArrayList<>();
        updateList.add(new Pair<>("pseudo",pseudo));
        try {
            return this.updateClient(updateList, id);
        }
        catch (Exception e){
            throw new DBProblemException("La mise à jour du pseudo du client a échoué");
        }
    }

    @Override
    public Client updateClientPoids(double poids, int id) throws Exception {
        List<Pair<String,Object>> updateList = new ArrayList<>();
        updateList.add(new Pair<>("poids",poids));
        try {
            return this.updateClient(updateList, id);
        }
        catch (Exception e){
            throw new DBProblemException("La mise à jour du poids du client a échoué");
        }
    }

    @Override
    public Client updateClientTaille(int taille, int id) throws Exception {
        List<Pair<String,Object>> updateList = new ArrayList<>();
        updateList.add(new Pair<>("taille",taille));
        try {
            return this.updateClient(updateList, id);
        }
        catch (Exception e){
            throw new DBProblemException("La mise à jour de la taille du client a échoué");
        }
    }

    @Override
    public Client updateClientPassword(String password, int id) throws Exception {
        List<Pair<String,Object>> updateList = new ArrayList<>();
        updateList.add(new Pair<>("password",passwordAuthentication.hash(password.toCharArray())));
        try {
            return this.updateClient(updateList, id);
        }
        catch (Exception e){
            throw new DBProblemException("La mise à jour du mot de passe du client a échoué");
        }
    }

    @Override
    public Client updateClientSexe(Sexe sexe, int id) throws Exception {
        List<Pair<String,Object>> updateList = new ArrayList<>();
        updateList.add(new Pair<>("sexe", Sexe.getSexe(sexe)));
        try {
            return this.updateClient(updateList, id);
        }
        catch (Exception e){
            throw new DBProblemException("La mise à jour du sexe du client a échoué");
        }
    }

    @Override
    public Client updateClientMail(String mail, int id) throws Exception {
        List<Pair<String,Object>> updateList = new ArrayList<>();
        updateList.add(new Pair<>("mail", mail));
        try {
            return this.updateClient(updateList, id);
        }
        catch (Exception e){
            throw new DBProblemException("La mise à jour du sexe du client a échoué");
        }
    }

    @Override
    public List<Client> getAllClient() throws Exception {
        return this.getAllClientWhere(new ArrayList<>());
    }

    @Override
    public List<Coach> getAllCoach() throws Exception {
        List<Pair<String,Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("client.iscoach", "true"));
        List<Coach> coachs = new ArrayList<>();
        List<Client> clients = this.getAllClientWhere(whereList);
        for (Client client : clients){
            coachs.add((Coach) client);
        }
        return coachs;
    }

    @Override
    public List<Admin> getAllAdmin() throws Exception {
        List<Pair<String,Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("client.isadmin", "true"));
        List<Admin> admins = new ArrayList<>();
        List<Client> clients = this.getAllClientWhere(whereList);
        for (Client client : clients){
            admins.add((Admin) client);
        }
        return admins;
    }

    @Override
    public List<Client> getAllClientWhere(List<Pair<String, Object>> whereList) throws Exception {
        List<Client> listClients = new ArrayList<>();
        List<Triple<String,String,String>> joinList = new ArrayList<>();
        joinList.add(new Triple<>("clientsport","idclient", "client.id"));
        joinList.add(new Triple<>("clientmateriel","idclient", "client.id"));
        joinList.add(new Triple<>("clientavis","idclient", "client.id"));
        joinList.add(new Triple<>("commande","idclient", "client.id"));
        try {
            ResultSet resultSet = ((MethodesPostgreSQL)this.methodesBD).selectJoin(joinList,whereList,this.table);
            int idCurrentClient = -1;
            while(resultSet.next()){
                if (idCurrentClient != resultSet.getInt(1)){
                    listClients.add(new Client(resultSet.getString("mail"),resultSet.getString("pseudo"),resultSet.getDouble("poids"), resultSet.getString("photo"), resultSet.getInt("taille"), Sexe.getSexe(resultSet.getString("sexe")), resultSet.getString("password"), resultSet.getInt(1)));
                    idCurrentClient = resultSet.getInt(1);
                }
            }
            return listClients;
        }
        catch (Exception e){
            throw new DBProblemException("Impossible de récupérer tous les clients");
        }
    }

    @Override
    public Client addMaterielToClient(int idClient, int idMateriel) throws Exception {
        List<Pair<String,Object>> insertList = new ArrayList<>();
        insertList.add(new Pair<>("idclient",idClient));
        insertList.add(new Pair<>("idmateriel",idMateriel));
        ((MethodesPostgreSQL)this.methodesBD).insert(insertList,"clientmateriel");

        return this.getClientById(idClient);
    }

    @Override
    public Client deleteMaterielToClient(int idClient, int idMateriel) throws Exception {
        List<Pair<String,Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("idclient",idClient));
        whereList.add(new Pair<>("idmateriel",idMateriel));
        ((MethodesPostgreSQL)this.methodesBD).delete(whereList, "clientmateriel");

        return this.getClientById(idClient);
    }

    @Override
    public Client addSportToClient(int idClient, int idSport) throws Exception {
        List<Pair<String,Object>> insertList = new ArrayList<>();
        insertList.add(new Pair<>("idclient",idClient));
        insertList.add(new Pair<>("idsport",idSport));
        ((MethodesPostgreSQL)this.methodesBD).insert(insertList,"clientsport");

        return this.getClientById(idClient);
    }

    @Override
    public Client deleteSportToClient(int idClient, int idSport) throws Exception {
        List<Pair<String,Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("idclient",idClient));
        whereList.add(new Pair<>("idsport",idSport));
        ((MethodesPostgreSQL)this.methodesBD).delete(whereList,"clientsport");

        return this.getClientById(idClient);
    }
}
