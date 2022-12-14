package com.fitj.dao.methodesBD;

import com.fitj.dao.connexions.ConnexionPostgreSQL;
import kotlin.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * Classe qui donne accès à toutes les méthodes de base pour une base de donnée de type PostgreSQL
 *
 * @author Etienne Tillier
 */
public class MethodesPostgreSQL extends MethodesBD{

    public MethodesPostgreSQL(){
        super(new ConnexionPostgreSQL());
    }

    /**
     * Ferme la connexion avec la base de donnée
     * @throws SQLException
     */
    @Override
    public void close() throws SQLException {
        this.getConnexion().close();
    }

    /**
     * @return un objet qui permet d'exécuter des requêtes sur la base de donnée
     */
    public Connection getConnexion(){
        return ((ConnexionPostgreSQL) this.connexionBD).getConnection();
    }

    /**
     * @param table le nom de la table
     * @return un objet de type ResultSet qui donne à accès à chaque tuple présent dans la table
     */
    public ResultSet selectAll(String table) {
        try {
            this.connect();
            String sql = "SELECT * FROM " + table+ ";";
            PreparedStatement query = this.getConnexion().prepareStatement(sql);
            ResultSet result = query.executeQuery();
            this.close();
            return result;
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * @param data List<Pair<String,Object>>, une liste de pair String (pour le nom de l'attribut) et un objet pour le contenu. Cette liste est la liste de condition pour le where
     * @param table String, le nom de la table
     * @return un objet de type ResultSet pour chaque tuple correspondant à la clause where
     */
    public ResultSet selectWhere(List<Pair<String,Object>> data, String table) {
        try {
            this.connect();
            String sql = "SELECT * FROM " + table + " WHERE ";
            for (Pair<String, Object> pair : data) {
                sql += pair.getFirst() + " = " + (pair.getSecond() instanceof String ? "'" + pair.getSecond() + "'" : pair.getSecond() + " ") + "AND";
            }
            sql = sql.substring(0, sql.length() - 3);
            sql += ";";
            PreparedStatement query = this.getConnexion().prepareStatement(sql);
            ResultSet result = query.executeQuery();
            this.close();
            return result;
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Supprime les tuples correspondant à la clause where
     * @param data List<Pair<String,Object>>, une liste de pair String (pour le nom de l'attribut) et un objet pour le contenu. Cette liste est la liste de condition pour le where
     * @param table String, le nom de la table
     * @throws SQLException
     */
    public void delete(List<Pair<String,Object>> data, String table) throws SQLException {
        this.connect();
        String sql = "DELETE FROM " + table + " WHERE ";
        for (Pair<String, Object> pair : data) {
            sql += pair.getFirst() + " = " + (pair.getSecond() instanceof String ? "'" + pair.getSecond() + "'" : pair.getSecond() + " ") + "AND";
        }
        sql = sql.substring(0, sql.length() - 3);
        sql += ";";
        PreparedStatement query = this.getConnexion().prepareStatement(sql);
        query.executeUpdate();
        this.close();
    }

    /**
     * Met à jour les tuples en fonction de la liste de setter et en fonction de la clause where
     * @param data List<Pair<String,Object>>, une liste de pair String (pour le nom de l'attribut) et un objet pour le contenu. Cette liste est la liste de setter pour le update
     * @param dataWhere List<Pair<String,Object>>, une liste de pair String (pour le nom de l'attribut) et un objet pour le contenu. Cette liste est la liste de condition pour le where
     * @param table String, le nom de la table
     * @throws SQLException
     */
    public void update(List<Pair<String,Object>> data, List<Pair<String,Object>> dataWhere, String table) throws SQLException{
        this.connect();
        String sql = "UPDATE " + table + " SET ";
        for (Pair<String, Object> pair : data) {
            sql += pair.getFirst() + " = " + (pair.getSecond() instanceof String ? "'" + pair.getSecond() + "'" : pair.getSecond() + " ") + ", ";
        }
        sql = sql.substring(0, sql.length() - 2);
        sql += "WHERE ";
        for (Pair<String, Object> pair : dataWhere) {
            sql += pair.getFirst() + " = " + (pair.getSecond() instanceof String ? "'" + pair.getSecond() + "'" : pair.getSecond() + " ") + "AND";
        }
        sql = sql.substring(0, sql.length() - 3);
        sql += ";";
        PreparedStatement query = this.getConnexion().prepareStatement(sql);
        query.executeUpdate();
        this.close();
    }

    /**
     * Ajoute un tuple dans la table avec les attributs présents dans la liste
     * @param data List<Pair<String,Object>>, une liste de pair String (pour le nom de l'attribut) et un objet pour le contenu. Cette liste est la liste des attribut à insérer dans le nouveau tuple
     * @param table String, le nom de la table
     * @throws SQLException
     */
    public void insert(List<Pair<String,Object>> data, String table) throws SQLException {
        this.connect();
        String sql = "INSERT INTO " + table + "(";
        for (Pair<String, Object> pair : data) {
            sql += pair.getFirst() + ",";
        }
        sql = sql.substring(0, sql.length() - 1);
        sql += ") VALUES(";
        for (Pair<String, Object> pair : data) {
            sql += (pair.getSecond() instanceof String ? "'" + pair.getSecond() + "'" : pair.getSecond()) + ",";
        }
        sql = sql.substring(0, sql.length() - 1);
        sql += ");";
        PreparedStatement query = this.getConnexion().prepareStatement(sql);
        query.executeUpdate();
        this.close();
    }

    /**
     * @param data Object, Le contenu de l'attribut
     * @param name String, le nom de l'attribut dans la table
     * @param table String, le nom de la table
     * @return true si un tuple existe avec cet attribut sinon return false
     * @throws SQLException
     */
    public boolean exist(Object data, String name, String table) throws SQLException {
        List<Pair<String,Object>> list = new ArrayList<>();
        list.add(new Pair<String,Object>(name,data));
        ResultSet result = this.selectWhere(list, table);
        if (result.next() == true){
            return true;
        }
        else {
            return false;
        }
    }
}
