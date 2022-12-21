package com.fitj.dao.postgresql;

import com.fitj.classes.Aliment;
import com.fitj.classes.Sport;
import com.fitj.dao.DAOAliment;
import com.fitj.dao.methodesBD.MethodesPostgreSQL;
import kotlin.Pair;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe qui permet d'intéragir avec la base de données PostgreSQL pour ce qui fait référence aux aliments
 *
 * @author Etienne Tillier
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
            throw new SQLException("La création de l'aliment a échoué");
        }
    }


    @Override
    public Aliment getAlimentById(int id) throws Exception {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("id", id));
        ResultSet alimentData = ((MethodesPostgreSQL)this.methodesBD).selectWhere(whereList, this.table);
        try{
            if (alimentData.next()){
                Aliment aliment = new Aliment(id, alimentData.getString("nom"));
                return aliment;
            }
            else {
                throw new SQLException("Aucun aliment avec cet id n'existe");
            }
        }
        catch(Exception e){
            throw new SQLException("La selection de l'aliment a échoué");
        }

    }

    @Override
    public List<Aliment> getAllAliments() throws Exception {
        List<Aliment> listeAliment = new ArrayList<>();
        ResultSet alimentsBD = ((MethodesPostgreSQL)this.methodesBD).selectAll(this.table);
        try {
            while(alimentsBD.next()){
                listeAliment.add(new Aliment(alimentsBD.getInt("id"), alimentsBD.getString("nom")));
            }
            return listeAliment;
        }
        catch (Exception e){
            throw new SQLException("Impossible de récupérer tous les aliments");
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
            throw new SQLException("La suppression de l'aliment a échoué");
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
            throw new SQLException("La mise à jour de l'aliment a échoué");
        }
    }


}
