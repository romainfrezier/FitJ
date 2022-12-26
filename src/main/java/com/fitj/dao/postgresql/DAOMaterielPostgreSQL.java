package com.fitj.dao.postgresql;

import com.fitj.classes.Materiel;
import com.fitj.dao.DAOMateriel;
import com.fitj.dao.methodesBD.MethodesPostgreSQL;
import com.fitj.exceptions.DBProblemException;
import kotlin.Pair;
import kotlin.Triple;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe qui permet d'interagir avec la base de données PostgreSQL pour ce qui fait référence aux matériels
 *
 * @author Etienne Tillier, Romain Frezier
 */
public class DAOMaterielPostgreSQL extends DAOMateriel {


    public DAOMaterielPostgreSQL(){
        super();
        this.methodesBD = new MethodesPostgreSQL();
    }

    /**
     * Ajoute un matériel dans la base de donnée
     * @param nom String, le nom du matériel
     * @throws DBProblemException si une erreur SQL est détectée
     */
    @Override
    public Materiel createMateriel(String nom) throws DBProblemException {
        List<Pair<String,Object>> listeInsert = new ArrayList<>();
        listeInsert.add(new Pair<>("nom",nom));
        try {
            int id = ((MethodesPostgreSQL)this.methodesBD).insert(listeInsert, this.table);
            return this.getMaterielById(id);
        }
        catch (Exception e){
            throw new DBProblemException("La création du matériel a échoué");
        }
    }

    /**
     * Permet de récupérer le matériel possédant l'id rentré en paramètre
     * @param id int, l'id du matériel
     * @return le matériel dans la base de donnée contenant l'id rentré en paramètre
     * @throws DBProblemException si une erreur SQL est détectée
     */
    @Override
    public Materiel getMaterielById(int id) throws DBProblemException {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("id", id));
        try{
            ResultSet materielData = ((MethodesPostgreSQL)this.methodesBD).selectWhere(whereList, this.table);
            if (materielData.next()){
                return new Materiel(id, materielData.getString("nom"));
            }
            else{
                throw new DBProblemException("Le matériel n'existe pas");
            }
        }
        catch (Exception e){
            throw new DBProblemException("La récupération du matériel a échoué");
        }
    }

    /**
     * Permet de récupérer le matériel qui a le nom rentré en paramètre
     * @param nom String, le nom du matériel
     * @return le matériel dans la base de donnée contenant le nom rentré en paramètre
     * @throws DBProblemException si une erreur SQL est détectée
     */
    @Override
    public Materiel getMaterielByNom(String nom) throws DBProblemException {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("nom", nom));
        try{
            ResultSet materielData = ((MethodesPostgreSQL)this.methodesBD).selectWhere(whereList, this.table);
            if (materielData.next()){
                return new Materiel(materielData.getInt("id"), nom);
            }
            else{
                throw new DBProblemException("Le matériel n'existe pas");
            }
        }
        catch (Exception e){
            throw new DBProblemException("La récupération du matériel a échoué");
        }
    }

    /**
     * Permet de récupérer tous les matériels de la base de donnée
     * @return la liste de tous les matériels dans la base de donnée
     * @throws DBProblemException si une erreur SQL est détectée
     */
    @Override
    public List<Materiel> getAllMateriel() throws DBProblemException {
        return this.getAllMaterielWhere(new ArrayList<>());
    }

    /**
     * Permet de récupérer tous les matériels dans la base de donnée qui vérifient les conditions rentrées en paramètre
     * @param whereList List<Pair<String, Object>>, la liste des conditions pour la requête SQL
     * @return la liste de tous les matériels dans la base de donnée qui respectent les conditions de la liste
     * @throws DBProblemException si une erreur SQL est détectée
     */
    @Override
    public List<Materiel> getAllMaterielWhere(List<Pair<String, Object>> whereList) throws DBProblemException {
        List<Materiel> listeMateriel = new ArrayList<>();
        List<Triple<String, String, String>> joinList = new ArrayList<>();
        joinList.add(new Triple<>("clientmateriel", "idmateriel", "materiel.id"));
        joinList.add(new Triple<>("exercicemateriel", "idmateriel", "materiel.id"));
        try {
            ResultSet materielData = ((MethodesPostgreSQL)this.methodesBD).selectJoin(joinList, whereList, this.table);
            int idCurrentMateriel = -1;
            while (materielData.next()){
                if (materielData.getInt("id") != idCurrentMateriel){
                    idCurrentMateriel = materielData.getInt("id");
                    listeMateriel.add(new Materiel(idCurrentMateriel, materielData.getString("nom")));
                }
            }
            return listeMateriel;
        }
        catch (Exception e){
            throw new DBProblemException("La récupération des matériels a échoué");
        }
    }

    @Override
    public List<Materiel> getMaterielByIdClient(int idClient) throws Exception {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("idclient", idClient));
        return this.getAllMaterielWhere(whereList);
    }


    /**
     * Permet de supprimer un matériel de la base de donnée
     * @param id int, l'id du materiel à supprimer
     * @throws DBProblemException si une erreur SQL est détectée
     */
    @Override
    public void supprimerMateriel(int id) throws DBProblemException {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("id", id));
        List<Pair<String, Object>> whereListClientMateriel = new ArrayList<>();
        whereListClientMateriel.add(new Pair<>("idmateriel", id));
        try{
            ((MethodesPostgreSQL)this.methodesBD).delete(whereListClientMateriel, "clientmateriel");
            ((MethodesPostgreSQL)this.methodesBD).delete(whereListClientMateriel, "exercicemateriel");
            ((MethodesPostgreSQL)this.methodesBD).delete(whereList, this.table);
        }
        catch (Exception e){
            throw new DBProblemException("La suppression du matériel a échoué");
        }
    }


    /**
     * Permet de modifier un matériel dans la base de donnée
     * @param updateList List<Pair<String,Object>>, la liste du setter
     * @param id int, l'id du materiel
     * @return Materiel, le matériel modifié
     * @throws DBProblemException si une erreur SQL est détectée
     */
    @Override
    public Materiel updateMateriel(List<Pair<String, Object>> updateList, int id) throws DBProblemException {
        List<Pair<String, Object>> whereList = new ArrayList<>();
        whereList.add(new Pair<>("id", id));
        try{
            ((MethodesPostgreSQL)this.methodesBD).update(updateList, whereList, this.table);
            return this.getMaterielById(id);
        }
        catch (Exception e){
            throw new DBProblemException("La modification du matériel a échoué");
        }
    }
}
