package com.fitj.models.connexions;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionPostgreSQL extends ConnexionBD {

    private Connection connection;

    Dotenv dotenv = Dotenv.load();
    private String user = dotenv.get("DATABASE_USER");
    private String password = dotenv.get("DATABASE_PASSWORD");

    private String port = dotenv.get("DATABASE_PORT");

    private String hostname = (!dotenv.get("DATABASE_HOSTNAME").equals("localhost") ? dotenv.get("DATABASE_HOSTNAME") + ":" + port : dotenv.get("DATABASE_HOSTNAME"));
    private String url = "jdbc:postgresql://" + hostname + "/" + dotenv.get("DATABASE_NAME");


    @Override
    public Connection connection() {
        System.out.println("\n\n\n\n\n\n\n"+dotenv+"\n\n\n\n\n\n\n");
        try {
            this.connection = DriverManager.getConnection(url,user,password);
            return connection;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    @Override
    public Connection getConnection() {
        return connection;
    }

    public void close() throws SQLException {
        this.connection.close();
    }
}
