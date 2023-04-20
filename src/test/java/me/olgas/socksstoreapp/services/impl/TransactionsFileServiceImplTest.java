package me.olgas.socksstoreapp.services.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class TransactionsFileServiceImplTest {

    private final TransactionsFileServiceImpl transactionsFileService = new TransactionsFileServiceImpl();
    @TempDir
    File tempDirForTesting;

    private String nameJsonFileForTesting;
    private String pathJsonFileForTesting;

    private String nameTxtFileForTesting;
    private String pathTxtFileForTesting;

    @BeforeEach
    void setUp(){
        ReflectionTestUtils.setField(transactionsFileService, "transactionsJsonFilePath",tempDirForTesting.getPath());
        ReflectionTestUtils.setField(transactionsFileService,"transactionsJsonFileName", "transactions.json");
        ReflectionTestUtils.setField(transactionsFileService, "transactionsTxtFilePath",tempDirForTesting.getPath());
        ReflectionTestUtils.setField(transactionsFileService,"transactionsTxtFileName", "transactions.txt");
        pathJsonFileForTesting = transactionsFileService.getTransactionsJsonFilePath();
        nameJsonFileForTesting = transactionsFileService.getTransactionsJsonFileName();
        pathTxtFileForTesting = transactionsFileService.getTransactionsTxtFilePath();
        nameTxtFileForTesting = transactionsFileService.getTransactionsTxtFileName();
    }
    @Test
    @DisplayName("Тест метода по очистке  файла со списком транзакций в формате Json ")
    void testCleanTransactionsJsonFile()throws IOException{
        assertTrue(transactionsFileService.cleanTransactionsJsonFile());
        assertTrue(Files.exists(Path.of(pathJsonFileForTesting,nameJsonFileForTesting)));
        List<String> testJson = (Files.readAllLines(Path.of(pathJsonFileForTesting, nameJsonFileForTesting)));
        assertTrue(testJson.isEmpty());
    }
    @Test
    @DisplayName("Тест метода по очистке  файла со списком транзакций в формате Txt ")
    void testCleanTransactionsTxtFile()throws IOException{
        assertTrue(transactionsFileService.cleanTransactionsTxtFile());
        assertTrue(Files.exists(Path.of(pathTxtFileForTesting,nameTxtFileForTesting)));
        List<String> testTXT = (Files.readAllLines(Path.of(pathTxtFileForTesting, nameTxtFileForTesting)));
        assertTrue(testTXT.isEmpty());
    }
    @Test
    @DisplayName("Тест метода получения   файла со списком транзакций в формате Txt ")
    void testGetTxtFile() {
        File testFile = new File(pathTxtFileForTesting + "/" + nameTxtFileForTesting);
        assertEquals(testFile, transactionsFileService.getTxtFile());
    }
    @Test
    @DisplayName("Тест метода получения   файла со списком транзакций в формате Json ")
    void testGetJsonFile() {
        File testFile = new File(pathJsonFileForTesting + "/" + nameJsonFileForTesting);
        assertEquals(testFile, transactionsFileService.getJsonFile());
    }

    @Test
    @DisplayName("Тест метода по чтению сохраненного TXT файла в списке транзакций")
    void testSaveAndReadTransactionsToTxtFile() throws IOException {
        String stringForSave = "string for save";
        transactionsFileService.saveToTxtFile(stringForSave);
        String savedString = Files.readString(Path.of(pathTxtFileForTesting, nameTxtFileForTesting));
        assertEquals(stringForSave, savedString);
    }
    @Test
    @DisplayName("Тест метода по чтению сохраненного Json файла в списке транзакций")
    void testSaveAndReadTransactionsListFromJsonFile() throws IOException {
        String stringForSave = "string for save";
        transactionsFileService.saveToJsonFile(stringForSave);
        String savedString = Files.readString(Path.of(pathJsonFileForTesting, nameJsonFileForTesting));
        assertEquals(stringForSave, savedString);
    }














}