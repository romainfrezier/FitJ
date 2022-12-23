package com.fitj.dao.tool;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public final class ResultSetPrinter {
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
