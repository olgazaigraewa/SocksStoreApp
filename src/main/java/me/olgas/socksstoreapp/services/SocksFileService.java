package me.olgas.socksstoreapp.services;

import java.io.File;

public interface SocksFileService {


    boolean saveToFile(String json);

    String readFromFile();

    boolean cleanSocksFile();

     File getSocks();

    String getSocksFilePath();

    String getSocksFileName();


}
