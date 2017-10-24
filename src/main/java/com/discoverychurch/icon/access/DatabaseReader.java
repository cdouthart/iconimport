package com.discoverychurch.icon.access;


import com.google.common.base.Throwables;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;
import com.healthmarketscience.jackcess.Table;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

public class DatabaseReader {
    private String dbFileName;
    private Database database;


    public DatabaseReader(String dbFileName) throws IOException {
        this.dbFileName = dbFileName;
        database = DatabaseBuilder.open(new File(dbFileName));
    }

    public void reportDatabase() throws IOException {
        Set<String> tables = database.getTableNames();

        tables.forEach(tableName -> {
            try {
                reportTable(tableName);
            } catch (IOException e) {
                Throwables.propagate(e);
            }
        });
    }


    public void reportTable(String tableName) throws IOException{
        System.out.println("Table " + tableName);
        System.out.println("====================");

        Table table = database.getTable(tableName);
        table.getColumns().forEach( column -> {
            System.out.println(column.getName() + ": " + column.getType().toString());
        });
    }
}
