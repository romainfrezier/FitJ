package com.fitj.models;

import io.github.cdimascio.dotenv.Dotenv;
import kotlin.Pair;

import java.sql.*;
import java.util.List;

public class Model {

    private Connection connection;
    private String table;

    Dotenv dotenv = Dotenv.load();
    private String user = dotenv.get("DATABASE_USER");
    private String password = dotenv.get("DATABASE_PASSWORD");
    private String url = dotenv.get("DATABASE_HOSTNAME") + dotenv.get("DATABASE_PORT")+ "/" + dotenv.get("DATABASE_NAME") + "?user=" + user + "&password=" + password;

    public Model(String table){
        this.table = table;
    }


    public Connection connection() throws SQLException {
        this.connection = DriverManager.getConnection(url);

        return connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public ResultSet selectAll() throws SQLException {
        this.connection();
        String sql = "SELECT * FROM " + this.table+ ";";
        PreparedStatement query = this.getConnection().prepareStatement(sql);
        ResultSet result = query.executeQuery();
        this.getConnection().close();
        return result;
    }

    public ResultSet selectWhere(List<Pair<String,Object>> data) throws SQLException {
        this.connection();
        String sql = "SELECT * FROM " + this.table + " WHERE ";
        for (Pair<String, Object> pair : data) {
            sql += pair.getFirst() + " = " + (pair.getSecond() instanceof String ? "'" + pair.getSecond() + "'" : pair.getSecond() + " ") + "AND";
        }
        sql = sql.substring(0, sql.length() - 3);
        sql += ";";
        PreparedStatement query = this.getConnection().prepareStatement(sql);
        ResultSet result = query.executeQuery();
        this.getConnection().close();
        return result;
    }

    public void delete(List<Pair<String,Object>> data) throws SQLException {
        this.connection();
        String sql = "DELETE FROM " + this.table + " WHERE ";
        for (Pair<String, Object> pair : data) {
            sql += pair.getFirst() + " = " + (pair.getSecond() instanceof String ? "'" + pair.getSecond() + "'" : pair.getSecond() + " ") + "AND";
        }
        sql = sql.substring(0, sql.length() - 3);
        sql += ";";
        PreparedStatement query = this.getConnection().prepareStatement(sql);
        query.executeUpdate();
        this.getConnection().close();
    }

    public void update(List<Pair<String,Object>> data, List<Pair<String,Object>> dataWhere) throws SQLException{
        this.connection();
        String sql = "UPDATE " + this.table + " SET ";
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
        PreparedStatement query = this.getConnection().prepareStatement(sql);
        query.executeUpdate();
        this.getConnection().close();
    }

    public void insert(List<Pair<String,Object>> data) throws SQLException {
        this.connection();
        String sql = "INSERT INTO " + this.table + "(";
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
        PreparedStatement query = this.getConnection().prepareStatement(sql);
        query.executeUpdate();
        this.getConnection().close();
    }





}
