package com.itmo.dragon.client;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileReader {

    public String read(String fileName) {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new java.io.FileReader(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        String line;
        String allData = "";
        try {
            while ((line = reader.readLine()) != null) {
                allData = allData + line + "\n";
            }
            return allData;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
