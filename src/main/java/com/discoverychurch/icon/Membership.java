package com.discoverychurch.icon;

import org.apache.commons.csv.CSVFormat;
import org.boon.json.JsonFactory;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Membership {
    String[] columns = {
            "cp",   //A
            "member", //B
            "lastName", //C
            "firstName",//D
            "wifeName",//E
            "children",//F
            "address",//G
            "city",//H
            "zip",//I
            "phone",//J
            "mobile",//K
            "email",//L
            "wifeMobile",//M
            "birthday",//N
            "wifeemail",//O
            "wifebirthday",//P
            "childrenbirthday",//Q
            "anniversary"};//R




    public void parse(String filename) throws IOException{
        List<Map<String,String>> data = new ArrayList<>();
        Reader in = new FileReader(filename);
        CSVFormat.DEFAULT.parse(in).getRecords().stream()
                .skip(1)
                .forEach(r -> {
                    Map<String,String> a = new LinkedHashMap<>();
                    for (int i=0; i < r.size();i++) {
                        a.put(columns[i],r.get(i));
                    }
                    data.add(a);
                });
        System.out.println(JsonFactory.toJson(data));
    }



}
