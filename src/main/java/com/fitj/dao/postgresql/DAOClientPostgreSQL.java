package com.fitj.dao.postgresql;

import com.fitj.classes.*;
import com.fitj.dao.factory.FactoryDAO;
import com.fitj.dao.methodesBD.MethodesPostgreSQL;
import com.fitj.dao.DAOClient;
import com.fitj.dao.tools.DaoMapper;
import com.fitj.enums.Sexe;
import com.fitj.exceptions.BannedAccountException;
import com.fitj.exceptions.DBProblemException;
import kotlin.Pair;
import kotlin.Triple;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Classe qui permet d'intéragir avec la base de données PostgreSQL pour ce qui fait référence aux clients
 *
 * @author Etienne Tillier, Romain Frezier
 */
public class DAOClientPostgreSQL extends DAOClient {

    /**
     * Constructeur
     */
    public DAOClientPostgreSQL(){
        super();
        this.methodesBD = new MethodesPostgreSQL();
    }

    /**
     * Méthode qui crée un nouveau client dans la base de données avec les informations passées en paramètre.
     * @param mail String, l'email du client
     * @param pseudo String, le pseudo du client
     * @param password String,  le mot de passe rentré par le client
     * @param poids float,  le poids du client
     * @param taille int, la taille du client
     * @param photo String, le lien de la photo du client
     * @param sexe Sexe, le sexe du client
     * @return Client, le client créé
     * @throws Exception
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
    private Client chooseRole(Map<String,Object> compte) throws Exception {
        if ((Boolean)compte.get("isadmin")){
            Admin connectedClient = new Admin((String)compte.get("mail"), (String)compte.get("pseudo"),((Number)compte.get("poids")).doubleValue(), (String)compte.get("photo"), ((Long)compte.get("taille")).intValue(), Sexe.getSexe((String)compte.get("sexe")), (String)compte.get("password"), ((Long)compte.get("id")).intValue(), (boolean) compte.get("isbanned"));
            connectedClient.setSolde(this.getSoldeCoach(connectedClient.getId()));
            return connectedClient;
        } else if ((Boolean)compte.get("iscoach")){
            Coach connectedClient = new Coach((String)compte.get("mail"), (String)compte.get("pseudo"), ((Number)compte.get("poids")).doubleValue(), (String)compte.get("photo"), ((Long)compte.get("taille")).intValue(), Sexe.getSexe((String)compte.get("sexe")), (String)compte.get("password"), ((Long)compte.get("id")).intValue(), (boolean) compte.get("isbanned"));
            connectedClient.setSolde(this.getSoldeCoach(connectedClient.getId()));
            return connectedClient;
        } else {
            return new Client((String)compte.get("mail"), (String)compte.get("pseudo"), ((Number)compte.get("poids")).doubleValue(), (String)compte.get("photo"), ((Long)compte.get("taille")).intValue(), Sexe.getSexe((String)compte.get("sexe")), (String)compte.get("password"), ((Long)compte.get("id")).intValue(), (boolean) compte.get("isbanned"));
        }
    }

    /**
     * Méthode qui permet de recuperer un client dans la base de données avec son mail
     * @param mail String, l'email du client
     * @return Client, le client correspondant à l'email
     * @throws Exception
     */

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
                return chooseRole(result.get(0));
            }
            else {
                throw new DBProblemException("Aucun client avec cet email n'existe");
            }
        }
        catch (Exception e){
            e.printStackTrace();
            throw new DBProblemException("La sélection du client a échoué");
        }
    }

    /**
     * Méthode qui permet de recuperer un client dans la base de données avec son id
     * @param id int, l'id du client
     * @return Client, le client correspondant à l'id
     * @throws Exception
     */
    @Override
    public Client getClientById(int id) throws Exception {
        List<Pair<String,Object>> data = new ArrayList<>();
        data.add(new Pair<>("id", id));
        try {
            DaoMapper compte = ((MethodesPostgreSQL)this.methodesBD).selectWhere(data, this.table);
            List<Map<String,Object>> result = compte.getListeData();
            if (!result.isEmpty()){
                return chooseRole(result.get(0));
            }
            else {
                throw new DBProblemException("Aucun client avec cet id n'existe");
            }
        }
        catch (Exception e){
            e.printStackTrace();
            throw new DBProblemException("La sélection du client a échoué");
        }
    }


    /**
     * Methode qui permet de supprimer un client de la base de données à partir de son id
     * @param mail String, l'email du client
     * @throws Exception
     */
    @Override
    public void supprimerClientByMail(String mail) throws Exception{
        try {
            Client client = getClientByEmail(mail);
            if (client instanceof Coach || client instanceof Admin) {
                List<Pair<String,Object>> wherelistSolde = new ArrayList<>();
                wherelistSolde.add(new Pair<>("idcoach", client.getId()));
                ((MethodesPostgreSQL)this.methodesBD).delete(wherelistSolde, "solde");
            }
            List<Pair<String,Object>> wherelist = new ArrayList<>();
            wherelist.add(new Pair<>("mail", mail));
            ((MethodesPostgreSQL)this.methodesBD).delete(wherelist, this.table);
        }
        catch(Exception e){
            throw new DBProblemException("La suppresion du client a échoué");
        }
    }

    /**
     * Methode qui permet de supprimer un client de la base de données à partir de son id
     * @param id int, l'id du client
     * @throws Exception
     */
    @Override
    public void supprimerClientById(int id) throws Exception{
        try {
            Client client = getClientById(id);
            if (client instanceof Coach || client instanceof Admin) {
                List<Pair<String,Object>> wherelistSolde = new ArrayList<>();
                wherelistSolde.add(new Pair<>("idcoach", id));
                ((MethodesPostgreSQL)this.methodesBD).delete(wherelistSolde, "solde");
            }
            List<Pair<String,Object>> wherelist = new ArrayList<>();
            wherelist.add(new Pair<>("id", id));
            ((MethodesPostgreSQL)this.methodesBD).delete(wherelist, this.table);
        }
        catch(Exception e){
            e.printStackTrace();
            throw new DBProblemException("La suppresion du client a échoué");
        }
    }

    /**
     * Methode qui permet de mettre à jour un client dans la base de données
     * @param data, la liste des champs à mettre à jour
     * @param id, l'id du client à mettre à jour
     * @return Client, le client mis à jour
     * @throws Exception
     */
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

    /**
     * Methode qui permet de mettre à jour la photo d'un client dans la base de données
     * @param photo String, la photo du client
     * @param id int, l'id du client
     * @return Client, le client mis à jour
     * @throws Exception
     */
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

    /**
     * Methode qui permet de mettre à jour le pseudo d'un client dans la base de données
     * @param pseudo String, pseudo du client
     * @param id int, l'id du client
     * @return Client, le client mis à jour
     * @throws Exception
     */
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

    /**
     * Methode qui permet de mettre à jour le poids d'un client dans la base de données
     * @param poids double, poids du client
     * @param id int, l'id du client
     * @return Client, le client mis à jour
     * @throws Exception
     */
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

    /**
     * Methode qui permet de mettre à jour la taille d'un client dans la base de données
     * @param taille double, taille du client
     * @param id int, l'id du client
     * @return Client, le client mis à jour
     * @throws Exception
     */

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

    /**
     * Methode qui permet de mettre à jour le mot de passe d'un client dans la base de données
     * @param password String, le mot de passe du client
     * @param id int, l'id du client
     * @return Client, le client mis à jour
     * @throws Exception
     */
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

    /**
     * Methode qui permet de mettre à jour le sexe d'un client dans la base de données
     * @param sexe Sexe, le sexe du client
     * @param id int, l'id du client
     * @return Client, le client mis à jour
     * @throws Exception
     */
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

    /**
     * Methode qui permet de mettre à jour le mail d'un client dans la base de données
     * @param mail String, le mail du client
     * @param id int, l'id du client
     * @return Client, le client mis à jour
     * @throws Exception
     */
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

    /**
     * Methode qui retourne la liste des clients
     * @return List<Client>, la liste des clients
     * @throws Exception
     */
    @Override
    public List<Client> getAllClient() throws Exception {
        return this.getAllClientWhere(new ArrayList<>());
    }

    /**
     * Methode qui retourne la liste des coachs
     * @return List<Coach>, la liste des coachs
     * @throws Exception
     */
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

    /**
     * Methode qui retourne la liste des admins
     * @return List<Admin>, la liste des admins
     * @throws Exception
     */
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

    /**
     * Methode qui retourne la liste des clients qui correspondent à la liste de conditions
     * @param whereList List<Pair<String,Object>>, la liste des critères
     * @return List<Client>, la liste des clients
     * @throws Exception
     */
    @Override
    public List<Client> getAllClientWhere(List<Pair<String, Object>> whereList) throws Exception {
        List<Client> listClients = new ArrayList<>();
        List<Triple<String,String,String>> joinList = new ArrayList<>();
        joinList.add(new Triple<>("clientsport","idclient", "client.id"));
        joinList.add(new Triple<>("clientmateriel","idclient", "client.id"));
        joinList.add(new Triple<>("clientavis","idclient", "client.id"));
        joinList.add(new Triple<>("solde","idcoach", "client.id"));
        try {
            DaoMapper resultSet = ((MethodesPostgreSQL)this.methodesBD).selectJoin(joinList,whereList,this.table);
            List<Map<String,Object>> listData = resultSet.getListeData();
            int idCurrentClient = -1;
            int i = 0;
            while (i < listData.size()){
                Map<String,Object> data = listData.get(i);
                if (idCurrentClient != ((Long)data.get("id")).intValue()){
                    idCurrentClient = ((Long)data.get("id")).intValue();
                    listClients.add(new Client((String)data.get("mail"), (String)data.get("pseudo"), ((Float)data.get("poids")).doubleValue(), (String)data.get("photo"), ((Long)data.get("taille")).intValue(), Sexe.getSexe((String)data.get("sexe")), (String)data.get("password"), idCurrentClient, (boolean)data.get("isbanned")));
                }
                i++;
            }
            return listClients;
        }
        catch (Exception e){
            e.printStackTrace();
            throw new DBProblemException("Impossible de récupérer tous les clients");
        }
    }

    /**
     * Methode qui ajoute un materiel à un client
     * @param idClient int, l'id du client
     * @param idMateriel int, l'id du matériel
     * @return Client, le client mis à jour
     * @throws Exception
     */
    @Override
    public Client addMaterielToClient(int idClient, int idMateriel) throws Exception {
        List<Pair<String,Object>> insertList = new ArrayList<>();
        insertList.add(new Pair<>("idclient",idClient));
        insertList.add(new Pair<>("idmateriel",idMateriel));
        ((MethodesPostgreSQL)this.methodesBD).insert(insertList,"clientmateriel");

        return this.getClientById(idClient);
    }

    /**
     * Methode qui supprime un materiel à un client
     * @param idClient int, l'id du client
     * @param idMateriel int, l'id du matériel
     * @return Client, le client mis à jour
     * @throws Exception
     */
    @Override
    public Client deleteMaterielToClient(int idClient, int idMateriel) throws Exception {
        List<Pair<String,Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("idclient",idClient));
        whereList.add(new Pair<>("idmateriel",idMateriel));
        ((MethodesPostgreSQL)this.methodesBD).delete(whereList, "clientmateriel");

        return this.getClientById(idClient);
    }

    /**
     * Methode qui ajoute un sport à un client
     * @param idClient int, l'id du client
     * @param idSport int, l'id du sport
     * @return Client, le client mis à jour
     * @throws Exception
     */
    @Override
    public Client addSportToClient(int idClient, int idSport) throws Exception {
        List<Pair<String,Object>> insertList = new ArrayList<>();
        insertList.add(new Pair<>("idclient",idClient));
        insertList.add(new Pair<>("idsport",idSport));
        ((MethodesPostgreSQL)this.methodesBD).insert(insertList,"clientsport");

        return this.getClientById(idClient);
    }

    /**
     * Methode qui supprime un sport à un client
     * @param idClient int, l'id du client
     * @param idSport int, l'id du sport
     * @return Client, le client mis à jour
     * @throws Exception
     */
    @Override
    public Client deleteSportToClient(int idClient, int idSport) throws Exception {
        List<Pair<String,Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("idclient",idClient));
        whereList.add(new Pair<>("idsport",idSport));
        ((MethodesPostgreSQL)this.methodesBD).delete(whereList,"clientsport");

        return this.getClientById(idClient);
    }

    /**
     * Methode qui permet a un client de devenir un coach
     * @param idClient int, l'id du client
     * @return Coach, le coach créé
     * @throws Exception
     */
    @Override
    public Coach clientBecomeCoach(int idClient) throws Exception {
        try {
            List<Pair<String,Object>> updateList = new ArrayList<>();
            updateList.add(new Pair<>("iscoach", "true"));
            List<Pair<String,Object>> whereList = new ArrayList<>();
            whereList.add(new Pair<>("id", idClient));
            ((MethodesPostgreSQL)this.methodesBD).update(updateList, whereList, this.table);
            List<Pair<String,Object>> data = new ArrayList<>();
            data.add(new Pair<>("idcoach", idClient));
            ((MethodesPostgreSQL)this.methodesBD).insert(data, "solde");
            return (Coach) this.getClientById(idClient);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DBProblemException("Impossible de faire devenir le client un coach");
        }

    }

    /**
     * Methode qui permet a un coach de devenir un admin
     * @param idClient int, l'id du coach
     * @return Admin, l'admin créé
     * @throws Exception
     */
    @Override
    public Admin coachBecomeAdmin(int idClient) throws Exception {
        try {
            if (this.getClientById(idClient) instanceof Coach){
                List<Pair<String,Object>> updateList = new ArrayList<>();
                updateList.add(new Pair<>("isadmin", "true"));
                List<Pair<String,Object>> whereList = new ArrayList<>();
                whereList.add(new Pair<>("id", idClient));
                ((MethodesPostgreSQL)this.methodesBD).update(updateList, whereList, this.table);
                return (Admin) this.getClientById(idClient);
            } else {
                throw new DBProblemException("Le client n'est pas un coach");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DBProblemException("Impossible de faire devenir le coach admin");
        }
    }

    /**
     * Methode qui permet de banne un client
     * @param client Client, client à bannir
     * @return Client, le client banni
     * @throws Exception
     */
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

    /**
     * Methode qui retourne tous les clients d'un coach
     * @param coachId int, l'id du coach
     * @return List<Client>, la liste des clients du coach
     * @throws Exception
     */
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
                if (idCurrentClient != ((Long)data.get("idclient")).intValue()){
                    idCurrentClient = ((Long)data.get("idclient")).intValue();
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
            e.printStackTrace();
            throw new DBProblemException("Impossible de récupérer tous les clients du coach");
        }
    }

    /**
     * Methode qui reser le solde d'un coach
     * @param coachId int, l'id du coach
     * @return Coach, le coach mis à jour
     * @throws Exception
     */
    @Override
    public Coach resetSoldeCoach(int coachId) throws Exception {
        List<Pair<String,Object>> updateList = new ArrayList<>();
        updateList.add(new Pair<>("montant", (double)0));
        List<Pair<String,Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("idcoach", coachId));
        ((MethodesPostgreSQL)this.methodesBD).update(updateList, whereList, "solde");
        return (Coach) this.getClientById(coachId);
    }

    /**
     * Methode qui modifie le solde d'un coach
     * @param coachId int, l'id du coach
     * @param solde int, le solde à ajouter
     * @return Coach, le coach mis à jour
     * @throws Exception
     */
    @Override
    public Coach incrementeSoldeCoach(int coachId, double solde) throws Exception {
        List<Pair<String,Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("idcoach", coachId));
        DaoMapper soldeData = ((MethodesPostgreSQL)this.methodesBD).selectWhere(whereList, "solde");
        List<Map<String,Object>> result = soldeData.getListeData();
        if (!result.isEmpty()){
            double soldeActuel = (double)result.get(0).get("montant");
            List<Pair<String,Object>> updateList = new ArrayList<>();
            updateList.add(new Pair<>("montant", soldeActuel + solde));
            ((MethodesPostgreSQL)this.methodesBD).update(updateList, whereList, "solde");
            return (Coach) this.getClientById(coachId);
        } else {
            throw new DBProblemException("Un problème est survenu lors de l'incrémentation du solde du coach");
        }
    }

    /**
     * Methode qui retourne le solde d'un coach
     * @param coachId int, l'id du coach
     * @return double, le solde du coach
     * @throws Exception
     */
    @Override
    public double getSoldeCoach(int coachId) throws Exception {
        try {
            List<Pair<String,Object>> data = new ArrayList<>();
            data.add(new Pair<>("idcoach", coachId));
            DaoMapper soldeData =((MethodesPostgreSQL)this.methodesBD).selectWhere(data, "solde");
            List<Map<String,Object>> result = soldeData.getListeData();
            if (!result.isEmpty()) {
                return (double) result.get(0).get("montant");
            } else {
                throw new DBProblemException("Aucun solde pour ce coach");
            }
        } catch (Exception e){
            e.printStackTrace();
            throw new DBProblemException("Impossible de récupérer le solde du coach");
        }
    }


}
