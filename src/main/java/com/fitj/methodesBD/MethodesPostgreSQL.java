package com.fitj.methodesBD;

import com.fitj.classes.Tool;
import com.fitj.connexions.ConnexionPostgreSQL;
import kotlin.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MethodesPostgreSQL extends MethodesBD{

    public MethodesPostgreSQL(){
        super(new ConnexionPostgreSQL());
    }

    @Override
    public void close() throws SQLException {
        this.getConnexion().close();
    }

    //continuer ici
    @Override
    public Connection getConnexion(){
        return ((ConnexionPostgreSQL) this.connexionBD).getConnection();
    }

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

    public boolean exist(Object param, String table) {
        try {
            List<Pair<String,Object>> data = new ArrayList<>();
            String paramName = Tool.getParameterNames(this.getClass().getMethod("verifier")).get(0);
            data.add(new Pair<>(paramName, param));
            ResultSet result = this.selectWhere(data, table);
            if (result.next() == false){
                return false;
            }
            else return true;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }
}
