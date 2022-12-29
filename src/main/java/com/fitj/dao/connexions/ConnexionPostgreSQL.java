package com.fitj.dao.connexions;

import com.fitj.exceptions.DBProblemException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
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

    private static HikariDataSource dataSource;


    static Dotenv dotenv = Dotenv.load();

    /**
     * Nom d'utilisateur pour se connecter à la base de donnée
     */
    private static final String user = dotenv.get("DATABASE_USER");

    /**
     * Mot de passe pour se connecter à la base de donnée
     */
    private static final String password = dotenv.get("DATABASE_PASSWORD");

    /**
     * Port pour se connecter à la base de donnée
     */
    private static final String port = dotenv.get("DATABASE_PORT");

    /**
     * Hote pour se connecter à la base de donnée
     */
    private static final String hostname = (!dotenv.get("DATABASE_HOSTNAME").equals("localhost") ? dotenv.get("DATABASE_HOSTNAME") + ":" + port : dotenv.get("DATABASE_HOSTNAME"));

    /**
     * Url final pour se connecter à la base de donnée
     */
    private static final String url = "jdbc:postgresql://" + hostname + "/" + dotenv.get("DATABASE_NAME");

    static HikariConfig config = new HikariConfig();


    static {
        config.setJdbcUrl(url);
        config.setUsername(user);
        config.setPassword(password);
        config.setMaximumPoolSize(50);
        config.setMinimumIdle(5);
        config.setConnectionTimeout(30000);
        config.setMaxLifetime(600000);
        config.setIdleTimeout(300000);
        dataSource = new HikariDataSource(config);
    }


    /**
     * Se connecte à la base de donnée postgreSQL
     * @return un objet connection pour se connecter à la base de donnée PostgreSQL
     */
    @Override
    public Connection connection() {
        try {
            return this.dataSource.getConnection();
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
    public Connection getConnection() throws DBProblemException {
        try {
            return this.dataSource.getConnection();
        }
        catch(Exception e){
            e.printStackTrace();
            throw new DBProblemException("Problème de connexion à la base de donnée");
        }

    }

    /**
     * Ferme la connexion à la base de donnée PostgreSQL
     * @throws SQLException
     */
    public void close() throws SQLException {
        this.connection.close();
    }
}