package com.fitj.dao.tools;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

/**
 * Classe permettant d'afficher les résultats d'un ResultSet sous forme de tableau CSV.
 */
public final class ResultSetPrinter {

    /**
     * Affiche les résultats du ResultSet fourni sous forme de tableau CSV.
     * @param rs ResultSet, le ResultSet à afficher
     * @throws Exception en cas d'erreur lors de la récupération des données du ResultSet
     */
    public static void displayResultSetAsCSVTable(ResultSet rs) throws Exception {
        ResultSetMetaData rsmd = rs.getMetaData();

        int columnCount = rsmd.getColumnCount();

        for (int i = 1; i <= columnCount; i++) {
            if (i > 1) {
                System.out.print(",");
            }
            System.out.print(rsmd.getColumnName(i));
        }
        System.out.println();

        while (rs.next()) {
            for (int i = 1; i <= columnCount; i++) {
                if (i > 1) {
                    System.out.print(",");
                }
                System.out.print(rs.getString(i));
            }
            System.out.println();
        }
    }
}
