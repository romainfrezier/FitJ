package com.fitj.dao.tools;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.*;

public class DaoMapper {

    private List<Map<String,Object>> listeData;

    private List<Map<Integer,Object>> listeDataIndex;


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

    public List<Map<String,Object>> getListeData() {
        return listeData;
    }

    public List<Map<Integer,Object>> getListeDataIndex() {
        return listeDataIndex;
    }

}
