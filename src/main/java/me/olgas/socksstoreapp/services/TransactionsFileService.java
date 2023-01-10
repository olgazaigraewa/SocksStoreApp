package me.olgas.socksstoreapp.services;

import java.io.File;

public interface TransactionsFileService {

    boolean saveToTXTFile(String txt);

    String readFromJsonFile();

    boolean cleanTransactionsJsonFile();

    boolean cleanTransactionsTXTFile();

    File getTransactions();

    File getTxtFile();

    boolean saveToJsonFile(String json);

   File getJsonFile();
}
