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
    private String transactionsTXTFilePath;

    @Value("${name.of.transactionsTXT.file}")
    private String transactionsTXTFileName;

    @Override
    public boolean saveToJsonFile(String json) {
        try {
            cleanTransactionsJsonFile();
            Files.writeString(Path.of(transactionsJsonFilePath, transactionsJsonFileName), json);
            return true;
        } catch (IOException e) {
            return false;
        }

    }

    @Override
    public boolean saveToTXTFile(String txt) {
        try {
            cleanTransactionsJsonFile();
            Files.writeString(Path.of(transactionsTXTFilePath, transactionsTXTFileName), txt);
            return true;
        } catch (IOException e) {
            return false;
        }

    }

    @Override
    public String readFromJsonFile() {
        if (Files.exists(Path.of(transactionsJsonFilePath, transactionsJsonFileName))){
           try {
               Files.readString(Path.of(transactionsJsonFilePath, transactionsJsonFileName));
            } catch (IOException e) {
               e.printStackTrace();
           }
        }
        return null;
    }

    @Override
    public boolean cleanTransactionsJsonFile(){
        try {
            Path path = Path.of(transactionsJsonFilePath, transactionsJsonFileName);
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean cleanTransactionsTXTFile(){
        try {
            Path path = Path.of(transactionsTXTFilePath, transactionsTXTFileName);
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public File getTransactions() {
        if (Files.exists(Path.of(transactionsJsonFilePath,transactionsJsonFileName))){
            return new File(transactionsJsonFilePath + "/" + transactionsJsonFileName);
        }
        return null;

    }
    @Override
    public File getTxtFile(){
        if (Files.exists(Path.of(transactionsTXTFilePath, transactionsTXTFileName))){
            return new File(transactionsTXTFilePath + "/" + transactionsTXTFileName);
        }
        return null;
    }

    @Override
    public File getJsonFile(){
        if (Files.exists(Path.of(transactionsJsonFilePath, transactionsJsonFileName))){
            return new File(transactionsJsonFilePath + "/" + transactionsJsonFileName);
        }
        return null;
    }


    public String getTransactionsJsonFilePath() {
        return transactionsJsonFilePath;
    }

    public void setTransactionsJsonFilePath(String transactionsJsonFilePath) {
        this.transactionsJsonFilePath = transactionsJsonFilePath;
    }

    public String getTransactionsJsonFileName() {
        return transactionsJsonFileName;
    }

    public void setTransactionsJsonFileName(String transactionsJsonFileName) {
        this.transactionsJsonFileName = transactionsJsonFileName;
    }

    public String getTransactionsTXTFilePath() {
        return transactionsTXTFilePath;
    }

    public void setTransactionsTXTFilePath(String transactionsTXTFilePath) {
        this.transactionsTXTFilePath = transactionsTXTFilePath;
    }

    public String getTransactionsTXTFileName() {
        return transactionsTXTFileName;
    }

    public void setTransactionsTXTFileName(String transactionsTXTFileName) {
        this.transactionsTXTFileName = transactionsTXTFileName;
    }
}





