package com.discoverychurch.icon;

import com.discoverychurch.icon.access.DatabaseReader;

import java.io.IOException;

public class Importer {

    public static void main(String[] argv) {
        String dbFile = argv[0];

        try {
            DatabaseReader reader = new DatabaseReader(dbFile);
            reader.reportDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
