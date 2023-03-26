package me.olgas.socksstoreapp.services.impl;


import me.olgas.socksstoreapp.services.TransactionsFileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class TransactionsFileServiceImpl implements TransactionsFileService {

    @Value("${path.to.transactionsJson.file}")
    private String transactionsJsonFilePath;

    @Value("${name.of.transactionsJson.file}")
    private String transactionsJsonFileName;

    @Value("${path.to.transactionsTXT.file}")
    private String transactionsTxtFilePath;

    @Value("${name.of.transactionsTXT.file}")
    private String transactionsTxtFileName;

    @Override
    public boolean cleanTransactionsJsonFile(){
        try {
            Path path = Path.of(transactionsJsonFilePath, transactionsJsonFileName);
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
           e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean cleanTransactionsTxtFile(){
        try {
            Path path = Path.of(transactionsTxtFilePath, transactionsTxtFileName);
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
           e.printStackTrace();
        }
        return false;
    }


    @Override
    public File getTxtFile(){
        return new File(transactionsTxtFilePath + "/" + transactionsTxtFileName);
    }

    @Override
    public File getJsonFile(){
        return new File(transactionsJsonFilePath + "/" + transactionsJsonFileName);
    }

    @Override
    public boolean saveToJsonFile(String json) {
        try {
            cleanTransactionsJsonFile();
            Files.writeString(Path.of(transactionsJsonFilePath, transactionsJsonFileName), json);
            return true;
        } catch (IOException e) {
          e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean saveToTxtFile(String txt) {
        try {
            cleanTransactionsTxtFile();
            Files.writeString(Path.of(transactionsTxtFilePath, transactionsTxtFileName), txt);
            return true;
        } catch (IOException e) {
           e.printStackTrace();
        }
        return false;
    }

    @Override
    public String readFromJsonFile() {
        if (Files.exists(Path.of(transactionsJsonFilePath, transactionsJsonFileName))){
           try {
               return Files.readString(Path.of(transactionsJsonFilePath, transactionsJsonFileName));
            } catch (IOException e) {
               e.printStackTrace();
           }
        }
        return null;
    }

    @Override
    public File getTransactions() {
        return new File(transactionsJsonFilePath + "/" + transactionsJsonFileName);
    }


    public String getTransactionsJsonFilePath() {
        return transactionsJsonFilePath;
    }

    public String getTransactionsJsonFileName() {
        return transactionsJsonFileName;
    }

    public String getTransactionsTxtFilePath() {
        return transactionsTxtFilePath;
    }

    public String getTransactionsTxtFileName() {
        return transactionsTxtFileName;
    }
}





