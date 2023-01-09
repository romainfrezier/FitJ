package com.fitj.dao.tools;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.*;

/**
 * Classe permettant de mapper les résultats d'une requête SQL
 *
 * @author Etienne Tillier, Paul Merceur
 */
public final class DaoMapper {

    /**
     * La liste des données mappées, avec pour chaque ligne une Map associant le nom de chaque colonne à sa valeur.
     */
    private final List<Map<String,Object>> listeData;

    /**
     * La liste des données mappées, avec pour chaque ligne une Map associant l'index de chaque colonne (à partir de 1) à sa valeur.
     */
    private final List<Map<Integer,Object>> listeDataIndex;

    /**
     * Construit un nouveau DaoMapper en mappant les résultats du ResultSet fourni.
     * @param resultSet ResultSet, le ResultSet à mapper
     * @throws Exception en cas d'erreur lors de la récupération des données du ResultSet
     */
    public DaoMapper(ResultSet resultSet) throws Exception {
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int columnCount = rsmd.getColumnCount();
        this.listeData = new ArrayList<>();
        this.listeDataIndex = new ArrayList<>();
        int i = 1;
        while (resultSet.next()){
            Map<String,Object> listeDataLigne = new LinkedHashMap<>();
            Map<Integer,Object> listeDataLigneIndex = new LinkedHashMap<>();
            while(i <= columnCount){
                listeDataLigne.put(rsmd.getColumnName(i),resultSet.getObject(i));
                listeDataLigneIndex.put(i,resultSet.getObject(i));
                i++;
            }
            listeDataIndex.add(listeDataLigneIndex);
            listeData.add(listeDataLigne);
            i = 1;
        }
        resultSet.close();
    }

    /**
     * Retourne la liste des données mappées, avec pour chaque ligne une Map associant le nom de chaque colonne à sa valeur.
     * @return la liste des données mappées par nom de colonne
     */
    public List<Map<String,Object>> getListeData() {
        return listeData;
    }

    /**
     * Retourne la liste des données mappées, avec pour chaque ligne une Map associant l'index de chaque colonne (à partir de 1) à sa valeur.
     * @return la liste des données mappées par index de colonne
     */
    public List<Map<Integer,Object>> getListeDataIndex() {
        return listeDataIndex;
    }
}
