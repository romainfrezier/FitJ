package com.fitj.models.postgresql;

import com.fitj.models.methodesBD.MethodesPostgreSQL;
import com.fitj.models.ModelClient;
import kotlin.Pair;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe qui permet d'intéragir avec la base de données PostgreSQL pour ce qui fait référence aux clients
 *
 * @author Etienne Tillier
 */
public class ModelClientPostgreSQL extends ModelClient {

    public ModelClientPostgreSQL(){
        super();
        this.methodesBD = new MethodesPostgreSQL();
    }

    /**
     * Créer un client dans la base de donnée avec les données rentrées en paramètre
     * @param mail     String, l'émail du client
     * @param pseudo   String, le pseudo du client
     * @param password String,  le mot de passe rentré par le client
     * @param poids    float,  le poids du client
     * @param taille   int, la taille du client
     * @param photo    String, le lien de la photo du client
     * @throws SQLException
     */
    public void createClient(String mail, String pseudo, String password, float poids, int taille, String photo) throws SQLException {
        List<Pair<String,Object>> data = new ArrayList<>();
        data.add(new Pair<>("mail", mail));
        data.add(new Pair<>("pseudo", pseudo));
        data.add(new Pair<>("password", passwordAuthentication.hash(password.toCharArray())));
        data.add(new Pair<>("poids", poids));
        data.add(new Pair<>("taille", taille));
        data.add(new Pair<>("photo", photo));
        ((MethodesPostgreSQL)this.methodesBD).insert(data, this.table);
    }

    /**
     * @param mail String, l'email du client
     * @return un objet de type ResultSet contenant toutes les informations du client qui contient l'email rentré en paramètre
     * @throws SQLException
     */
    public ResultSet getClientAccount(String mail) throws SQLException {
        ResultSet compte = null;
        List<Pair<String,Object>> data = new ArrayList<>();
        data.add(new Pair<>("mail", mail));
        compte = ((MethodesPostgreSQL)this.methodesBD).selectWhere(data, this.table);
        try {
            if (compte.next() == true){
                return compte;
            }
        }
        catch (SQLException e){
            System.out.println("Email non existant");
            throw new SQLException();
        }
        return compte;
    }

    /**
     * @param data Object, le contenu de l'attribut à vérifier
     * @param name String, le nom de l'attribut à vérifier
     * @return true si l'attribut est présent dans la table client sinon return false
     * @throws Exception
     */
    public boolean verifier(Object data,String name) throws Exception {
        return (((MethodesPostgreSQL)this.methodesBD).exist(data, name, this.table));
    }



}
