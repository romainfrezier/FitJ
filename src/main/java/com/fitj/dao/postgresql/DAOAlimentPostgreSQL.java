package com.fitj.dao.postgresql;

import com.fitj.classes.Aliment;
import com.fitj.dao.DAOAliment;
import com.fitj.dao.methodesBD.MethodesPostgreSQL;
import com.fitj.dao.tools.DaoMapper;
import com.fitj.exceptions.DBProblemException;
import kotlin.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Classe qui permet d'intéragir avec la base de données PostgreSQL pour ce qui fait référence aux aliments
 *
 * @author Etienne Tillier, Romain Frezier
 */
public class DAOAlimentPostgreSQL extends DAOAliment {

    public DAOAlimentPostgreSQL(){
        super();
        this.methodesBD = new MethodesPostgreSQL();
    }


    @Override
    public Aliment createAliment(String nom) throws Exception {
        List<Pair<String,Object>> listeInsert = new ArrayList<>();
        listeInsert.add(new Pair<>("nom",nom));
        try {
            int id = ((MethodesPostgreSQL)this.methodesBD).insert(listeInsert, this.table);
            return this.getAlimentById(id);
        }
        catch (Exception e){
            throw new DBProblemException("La création de l'aliment a échoué");
        }
    }


    @Override
    public Aliment getAlimentById(int id) throws Exception {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("id", id));
        try {
            DaoMapper alimentData = ((MethodesPostgreSQL)this.methodesBD).selectWhere(whereList, this.table);
            List<Map<String,Object>> result = alimentData.getListeData();
            if (!result.isEmpty()){
                return new Aliment(id, (String)result.get(0).get("nom"));
            }
            else {
                throw new DBProblemException("Aucun aliment avec cet id n'existe");
            }
        }
        catch(Exception e){
            throw new DBProblemException("La selection de l'aliment a échoué");
        }

    }

    @Override
    public List<Aliment> getAllAliments() throws Exception {
        List<Aliment> listeAliment = new ArrayList<>();
        try {
            DaoMapper alimentData = ((MethodesPostgreSQL)this.methodesBD).selectAll(this.table);
            List<Map<String,Object>> result = alimentData.getListeData();
            for (Map<String,Object> row : result){
                listeAliment.add(new Aliment(((Long)row.get("id")).intValue(), (String)row.get("nom")));
            }
            return listeAliment;
        }
        catch (Exception e){
            e.printStackTrace();
            throw new DBProblemException("Impossible de récupérer tous les aliments");
        }
    }


    @Override
    public void supprimerAliment(int id) throws Exception {
        List<Pair<String,Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("id",id));
        List<Pair<String,Object>> whereListClientSport = new ArrayList<>();
        whereListClientSport.add(new Pair<>("idaliment",id));
        try {
            ((MethodesPostgreSQL)this.methodesBD).delete(whereListClientSport,"recettealiment");
            ((MethodesPostgreSQL)this.methodesBD).delete(whereList,this.table);
        }
        catch (Exception e){
            throw new DBProblemException("La suppression de l'aliment a échoué");
        }
    }

    @Override
    public Aliment updateAliment(List<Pair<String, Object>> updateList, int id) throws Exception {
        List<Pair<String,Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("id",id));
        try {
            ((MethodesPostgreSQL)this.methodesBD).update(updateList,whereList,this.table);
            return this.getAlimentById(id);
        }
        catch (Exception e){
            throw new DBProblemException("La mise à jour de l'aliment a échoué");
        }
    }


}
