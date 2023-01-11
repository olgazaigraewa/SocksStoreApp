package me.olgas.socksstoreapp.services;

import java.io.File;

public interface TransactionsFileService {

    boolean cleanTransactionsJsonFile();

    boolean cleanTransactionsTXTFile();

  //  File getTransactions();

    File getTxtFile();
    File getJsonFile();

    boolean saveToJsonFile(String json);

    boolean saveToTXTFile(String txt);

    String readFromJsonFile();

    File getTransactions();
}
