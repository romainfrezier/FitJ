package com.fitj.dao.postgresql;

import com.fitj.classes.*;
import com.fitj.dao.DAOSport;
import com.fitj.dao.factory.FactoryDAOPostgreSQL;
import com.fitj.dao.methodesBD.MethodesPostgreSQL;
import com.fitj.dao.tools.DaoMapper;
import com.fitj.exceptions.DBProblemException;
import kotlin.Pair;
import kotlin.Triple;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Classe qui permet d'intéragir avec la base de données PostgreSQL pour ce qui fait référence aux sports
 *
 * @author Etienne Tillier, Romain Frezier
 */
public class DAOSportPostgreSQL extends DAOSport {

    /**
     * Constructeur
     */
    public DAOSportPostgreSQL() {
        super();
        this.methodesBD = new MethodesPostgreSQL();
    }

    /**
     * Ajoute un sport dans la base de donnée
     * @param nom String, le nom du sport
     * @throws Exception si une erreur SQL est détectée
     */
    @Override
    public Sport createSport(String nom) throws Exception {
        List<Pair<String,Object>> listeInsert = new ArrayList<>();
        listeInsert.add(new Pair<>("nom",nom));
        try {
            int id = ((MethodesPostgreSQL)this.methodesBD).insert(listeInsert, this.table);
            return this.getSportById(id);
        }
        catch (Exception e){
            throw new DBProblemException("La création du sport a échoué");
        }
    }

    /**
     * @param id int, l'id du sport
     * @return le sport dans la base de donnée contenant l'id rentré en paramètre
     * @throws Exception si une erreur SQL est détectée
     */
    @Override
    public Sport getSportById(int id) throws Exception {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("id", id));
        try {
            DaoMapper sportBD = ((MethodesPostgreSQL)this.methodesBD).selectWhere(whereList, this.table);
            List<Map<String,Object>> result = sportBD.getListeData();
            if (!result.isEmpty()) {
                Map<String, Object> data = result.get(0);
                return new Sport(id, (String)data.get("nom"));
            }
            else {
                throw new DBProblemException("Aucun sport avec cet id n'existe");
            }
        }
        catch(Exception e){
            throw new DBProblemException("La selection du sport a échoué");
        }

    }

    /**
     * Retourne un sport en fonction de son nom
     * @param nom String, le nom du sport
     * @return
     * @throws Exception
     */
    @Override
    public Sport getSportByNom(String nom) throws Exception {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("nom", nom));
        try {
            DaoMapper sportBD = ((MethodesPostgreSQL)this.methodesBD).selectWhere(whereList, this.table);
            List<Map<String,Object>> result = sportBD.getListeData();
            if (!result.isEmpty()) {
                Map<String, Object> data = result.get(0);
                return new Sport(((Long)data.get("id")).intValue(), (String) data.get("nom"));
            }
            else {
                throw new DBProblemException("Aucun sport avec ce nom n'existe");
            }
        }
        catch(Exception e){
            throw new DBProblemException("La selection du sport a échoué");
        }
    }

    /**
     * Retourne la liste de tous les sports
     * @return List<Sport>, la liste de tous les sports
     * @throws Exception
     */
    @Override
    public List<Sport> getAllSport() throws Exception {
        return this.getAllSportWhere(new ArrayList<>());
    }

    /**
     * Retourne la liste de tous les sports qui vérifient les conditions rentrées en paramètre
     * @param whereList List<Pair<String, Object>>, la liste des conditions
     * @return List<Sport>, la liste de tous les sports qui vérifient les conditions rentrées en paramètre
     * @throws Exception
     */
    public List<Sport> getAllSportWhere(List<Pair<String, Object>> whereList) throws Exception {
        List<Sport> listeSport = new ArrayList<>();
        List<Triple<String,String,String>> joinList = new ArrayList<>();
        joinList.add(new Triple<>("clientsport","idsport", "sport.id"));
        joinList.add(new Triple<>("seance","idsport", "sport.id"));
        try {
            DaoMapper resultSet = ((MethodesPostgreSQL) this.methodesBD).selectJoin(joinList, whereList, this.table);
            List<Map<String, Object>> listData = resultSet.getListeData();
            List<Map<Integer, Object>> listDataIndex = resultSet.getListeDataIndex();
            int idCurrentSport = -1;
            int i = 0;
            while (i < listData.size()) {
                Map<Integer, Object> dataIndex = listDataIndex.get(i);
                if (((Long)dataIndex.get(1)).intValue() != idCurrentSport) {
                    idCurrentSport = ((Long)dataIndex.get(1)).intValue();
                    listeSport.add(new Sport(idCurrentSport,(String) dataIndex.get(2)));
                }
                i++;
            }
            return listeSport;
        }
        catch (Exception e){
            e.printStackTrace();
            throw new DBProblemException("Impossible de récupérer tous les sports");
        }
    }


    /**
     * Supprime le sport de la base de donnée
     * @param id int, l'id du sport
     * @throws Exception si une erreur SQL est détectée
     */
    @Override
    public void supprimerSport(int id) throws Exception {
        List<Pair<String,Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("id",id));
        List<Pair<String,Object>> whereListClientSport = new ArrayList<>();
        whereListClientSport.add(new Pair<>("idsport",id));
        try {
            ((MethodesPostgreSQL)this.methodesBD).delete(whereListClientSport,"clientsport");
            this.supprimerSeanceSport(id);
            ((MethodesPostgreSQL)this.methodesBD).delete(whereList,this.table);
        }
        catch (Exception e){
            throw new DBProblemException("La suppression du sport a échoué");
        }
    }

    /**
     * Supprimer les séances qui font référence à ce sport
     * @param id int, l'id du sport
     * @throws Exception si une erreur SQL est détectée
     */
    public void supprimerSeanceSport(int id) throws Exception {
        List<Seance> listeSeance = FactoryDAOPostgreSQL.getInstance().getDAOSeance().getSeanceFromSport(id);
        for (Seance seance : listeSeance){
            FactoryDAOPostgreSQL.getInstance().getDAOSeance().supprimerSeance(seance.getId());
        }
    }


    /**
     * Met à jour dans la base de donnée le sport
     * @param updateList List<Pair<String,Object>>, la liste du setter
     * @param id int, l'id du sport
     */
    @Override
    public Sport updateSport(List<Pair<String, Object>> updateList, int id) throws Exception {
        List<Pair<String,Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("id",id));
        try {
            ((MethodesPostgreSQL)this.methodesBD).update(updateList,whereList,this.table);
            return this.getSportById(id);
        }
        catch (Exception e){
            throw new DBProblemException("La mise à jour du sport a échoué");
        }
    }

    /**
     * Retourne la liste des sports d'un client
     * @param clientId int, l'id du client pratiquant le sport
     * @return
     * @throws Exception
     */
    @Override
    public List<Sport> getSportByIdClient(int clientId) throws Exception {
        List<Pair<String,Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("idclient",clientId));
        return this.getAllSportWhere(whereList);
    }


}
