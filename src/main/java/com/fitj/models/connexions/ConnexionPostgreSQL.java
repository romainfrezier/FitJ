package com.fitj.models.connexions;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe qui permet de se connecter à une base de données de type PostgreSQL et d'y exécuter des requêtes
 *
 * @author Etienne Tillier
 */
public class ConnexionPostgreSQL extends ConnexionBD {

    /**
     * Object connection qui permet d'exécuter des requêtes sur la base de donnée
     */
    private Connection connection;

    Dotenv dotenv = Dotenv.load();

    /**
     * Nom d'utilisateur pour se connecter à la base de donnée
     */
    private String user = dotenv.get("DATABASE_USER");

    /**
     * Mot de passe pour se connecter à la base de donnée
     */
    private String password = dotenv.get("DATABASE_PASSWORD");

    /**
     * Port pour se connecter à la base de donnée
     */
    private String port = dotenv.get("DATABASE_PORT");

    /**
     * Hote pour se connecter à la base de donnée
     */
    private String hostname = (!dotenv.get("DATABASE_HOSTNAME").equals("localhost") ? dotenv.get("DATABASE_HOSTNAME") + ":" + port : dotenv.get("DATABASE_HOSTNAME"));

    /**
     * Url final pour se connecter à la base de donnée
     */
    private String url = "jdbc:postgresql://" + hostname + "/" + dotenv.get("DATABASE_NAME");


    /**
     * Se connecte à la base de donnée postgreSQL
     * @return un objet connection pour se connecter à la base de donnée PostgreSQL
     */
    @Override
    public Connection connection() {
        try {
            this.connection = DriverManager.getConnection(url,user,password);
            return connection;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    /**
     * @return un objet connection pour se connecter à la base de donnée PostgreSQL
     */
    @Override
    public Connection getConnection() {
        return connection;
    }

    /**
     * Ferme la connexion à la base de donnée PostgreSQL
     * @throws SQLException
     */
    public void close() throws SQLException {
        this.connection.close();
    }
}
