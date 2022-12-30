package com.fitj.dao.postgresql;

import com.fitj.classes.*;
import com.fitj.dao.methodesBD.MethodesPostgreSQL;
import com.fitj.dao.DAOClient;
import com.fitj.dao.tool.DaoMapper;
import com.fitj.enums.Sexe;
import com.fitj.exceptions.BannedAccountException;
import com.fitj.exceptions.DBProblemException;
import kotlin.Pair;
import kotlin.Triple;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
     */
    private Client chooseRole(Map<String,Object> compte){
        Client connectedClient;
        if ((Boolean)compte.get("isadmin")){
            connectedClient = new Admin((String)compte.get("mail"), (String)compte.get("pseudo"),((Number)compte.get("poids")).doubleValue(), (String)compte.get("photo"), ((Long)compte.get("taille")).intValue(), Sexe.getSexe((String)compte.get("sexe")), (String)compte.get("password"), ((Long)compte.get("id")).intValue(), (boolean) compte.get("isbanned"));
        } else if ((Boolean)compte.get("iscoach")){
            connectedClient = new Coach((String)compte.get("mail"), (String)compte.get("pseudo"), ((Number)compte.get("poids")).doubleValue(), (String)compte.get("photo"), ((Long)compte.get("taille")).intValue(), Sexe.getSexe((String)compte.get("sexe")), (String)compte.get("password"), ((Long)compte.get("id")).intValue(), (boolean) compte.get("isbanned"));
        } else {
            connectedClient = new Client((String)compte.get("mail"), (String)compte.get("pseudo"), ((Number)compte.get("poids")).doubleValue(), (String)compte.get("photo"), ((Long)compte.get("taille")).intValue(), Sexe.getSexe((String)compte.get("sexe")), (String)compte.get("password"), ((Long)compte.get("id")).intValue(), (boolean) compte.get("isbanned"));
        }
        return connectedClient;
    }

    @Override
    public Client getClientByEmail(String mail) throws Exception {
        List<Pair<String,Object>> data = new ArrayList<>();
        data.add(new Pair<>("mail", mail));
        try {
            DaoMapper compte = ((MethodesPostgreSQL)this.methodesBD).selectWhere(data, this.table);
            List<Map<String,Object>> result = compte.getListeData();
            if (!result.isEmpty()){
                if ((boolean)result.get(0).get("isbanned")) {
                    throw new BannedAccountException();
                }
                Client client = chooseRole(result.get(0));
                client.setListeCommande(new ArrayList<>());
                client.setListeMateriel(new ArrayList<>());
                client.setListeSport(new ArrayList<>());
                return client;
            }
            else {
                throw new DBProblemException("Aucun client avec cet email n'existe");
            }
        }
        catch (SQLException e){
            throw new DBProblemException("La sélection du client a échoué");
        }
    }

    @Override
    public Client getClientById(int id) throws Exception {
        List<Pair<String,Object>> data = new ArrayList<>();
        data.add(new Pair<>("id", id));
        try{
            DaoMapper compte = ((MethodesPostgreSQL)this.methodesBD).selectWhere(data, this.table);
            List<Map<String,Object>> result = compte.getListeData();
            if (!result.isEmpty()){
                Client client = chooseRole(result.get(0));
                client.setListeCommande(new ArrayList<>());
                client.setListeMateriel(new ArrayList<>());
                client.setListeSport(new ArrayList<>());
                return client;
            }
            else {
                throw new DBProblemException("Aucun client avec cet id n'existe");
            }
        }
        catch (SQLException e){
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
            coachs.add(new Coach(client.getEmail(), client.getPseudo(), client.getPoids(), client.getPhoto(), client.getTaille(), client.getSexe(), client.getPassword(), client.getId(), client.isBanni()));
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
            admins.add(new Admin(client.getEmail(), client.getPseudo(), client.getPoids(), client.getPhoto(), client.getTaille(), client.getSexe(), client.getPassword(), client.getId(), client.isBanni()));
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
        try {
            DaoMapper resultSet = ((MethodesPostgreSQL)this.methodesBD).selectJoin(joinList,whereList,this.table);
            List<Map<String,Object>> listData = resultSet.getListeData();
            int idCurrentClient = -1;
            int i = 0;
            while (i < listData.size()){
                Map<String,Object> data = listData.get(i);
                if (idCurrentClient != ((Long)data.get("id")).intValue()){
                    idCurrentClient = ((Long)data.get("id")).intValue();
                    Client client = new Client((String)data.get("mail"), (String)data.get("pseudo"), ((Number)data.get("poids")).doubleValue(), (String)data.get("photo"), ((Long)data.get("taille")).intValue(), Sexe.getSexe((String)data.get("sexe")), (String)data.get("password"), idCurrentClient, (boolean)data.get("isbanned"));
                    listClients.add(client);
                }
                i++;
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

    @Override
    public Coach clientBecomeCoach(int idClient) throws Exception {
        List<Pair<String,Object>> updateList = new ArrayList<>();
        updateList.add(new Pair<>("iscoach", "true"));
        List<Pair<String,Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("id", idClient));
        ((MethodesPostgreSQL)this.methodesBD).update(updateList, whereList, this.table);
        return (Coach) this.getClientById(idClient);
    }

    @Override
    public Admin clientBecomeAdmin(int idClient) throws Exception {
        List<Pair<String,Object>> updateList = new ArrayList<>();
        updateList.add(new Pair<>("isadmin", "true"));
        List<Pair<String,Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("id", idClient));
        ((MethodesPostgreSQL)this.methodesBD).update(updateList, whereList, this.table);
        return (Admin) this.getClientById(idClient);
    }

    @Override
    public Client banClient(Client client) throws Exception {
        client.setBanni();
        boolean isBan = client.isBanni();
        List<Pair<String,Object>> updateList = new ArrayList<>();
        updateList.add(new Pair<>("isbanned", isBan));
        List<Pair<String,Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("id", client.getId()));
        ((MethodesPostgreSQL)this.methodesBD).update(updateList, whereList, this.table);
        return this.getClientById(client.getId());
    }

    @Override
    public List<Client> getAllClientForACoach(int coachId) throws Exception {
        List<Client> clients = new ArrayList<>();
        List<Triple<String,String,String>> joinList = new ArrayList<>();
        joinList.add(new Triple<>("commande","idclient", "client.id"));
        List<Pair<String,Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("commande.idcoach", coachId));
        try {
            DaoMapper resultSet = ((MethodesPostgreSQL)this.methodesBD).selectJoin(joinList,whereList,this.table);
            List<Map<String,Object>> listData = resultSet.getListeData();
            int idCurrentClient = -1;
            int i = 0;
            while (i < listData.size()){
                Map<String,Object> data = listData.get(i);
                if (idCurrentClient != ((Long)data.get("id")).intValue()){
                    idCurrentClient = ((Long)data.get("id")).intValue();
                    Client client = new Client((String)data.get("mail"), (String)data.get("pseudo"), ((Number)data.get("poids")).doubleValue(), (String)data.get("photo"), ((Long)data.get("taille")).intValue(), Sexe.getSexe((String)data.get("sexe")), (String)data.get("password"), idCurrentClient, (boolean)data.get("isbanned"));
                    List<Materiel> materiels = new DAOMaterielPostgreSQL().getMaterielByIdClient(client.getId());
                    List<Sport> sports = new DAOSportPostgreSQL().getSportByIdClient(client.getId());
                    client.setListeMateriel(materiels);
                    client.setListeSport(sports);
                    clients.add(client);
                }
                i++;
            }
            return clients;
        }
        catch (Exception e){
            throw new DBProblemException("Impossible de récupérer tous les clients du coach");
        }
    }


}
