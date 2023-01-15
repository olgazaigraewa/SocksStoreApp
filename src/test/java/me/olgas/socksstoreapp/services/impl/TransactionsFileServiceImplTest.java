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

import static org.junit.jupiter.api.Assertions.*;

public class TransactionsFileServiceImplTest {

    private final TransactionsFileServiceImpl transactionsFileService = new TransactionsFileServiceImpl();
    @TempDir
    File tempDirForTesting;

    private String nameJsonFileForTesting;
    private String pathJsonFileForTesting;

    private String nameTXTFileForTesting;
    private String pathTXTFileForTesting;

    @BeforeEach
    void setUp(){
        ReflectionTestUtils.setField(transactionsFileService, "transactionsJsonFilePath",tempDirForTesting.getPath());
        ReflectionTestUtils.setField(transactionsFileService,"transactionsJsonFileName", "transactions.json");
        ReflectionTestUtils.setField(transactionsFileService, "transactionsTXTFilePath",tempDirForTesting.getPath());
        ReflectionTestUtils.setField(transactionsFileService,"transactionsTXTFileName", "transactions.txt");
        pathJsonFileForTesting = transactionsFileService.getTransactionsJsonFilePath();
        nameJsonFileForTesting = transactionsFileService.getTransactionsJsonFileName();
        pathTXTFileForTesting = transactionsFileService.getTransactionsTXTFilePath();
        nameTXTFileForTesting = transactionsFileService.getTransactionsTXTFileName();
    }
    @Test
    @DisplayName("Тест метода по очистке  файла со списком транзакций в формате Json ")
    void testCleanTransactionsJsonFile()throws IOException{
        assertTrue(transactionsFileService.cleanTransactionsJsonFile());
        assertTrue(Files.exists(Path.of(pathJsonFileForTesting,nameJsonFileForTesting)));
        assertTrue(Files.deleteIfExists(Path.of(pathJsonFileForTesting, nameJsonFileForTesting)));
        assertFalse(Files.exists(Path.of(pathJsonFileForTesting, nameJsonFileForTesting)));
    }
    @Test
    @DisplayName("Тест метода по очистке  файла со списком транзакций в формате Txt ")
    void testCleanTransactionsTXTFile()throws IOException{
        assertTrue(transactionsFileService.cleanTransactionsTXTFile());
        assertTrue(Files.exists(Path.of(pathTXTFileForTesting,nameTXTFileForTesting)));
        assertTrue(Files.deleteIfExists(Path.of(pathTXTFileForTesting, nameTXTFileForTesting)));
        assertFalse(Files.exists(Path.of(pathTXTFileForTesting, nameTXTFileForTesting)));
    }
    @Test
    @DisplayName("Тест метода получения   файла со списком транзакций в формате Txt ")
    void testGetTxtFile() {
        File testFile = new File(pathTXTFileForTesting + "/" + nameTXTFileForTesting);
        assertEquals(testFile, transactionsFileService.getTxtFile());
    }
    @Test
    @DisplayName("Тест метода получения   файла со списком транзакций в формате Json ")
    void testGetJsonFile() {
        File testFile = new File(pathJsonFileForTesting + "/" + nameJsonFileForTesting);
        assertEquals(testFile, transactionsFileService.getJsonFile());
    }


    @Test
    @DisplayName("Тест метода по сохранению  файла со списком транзакций в формате Json ")
    void testSaveToJsonFile(){
        String stringForSave = "string for save";
        assertTrue (transactionsFileService.saveToJsonFile(stringForSave));
    }

    @Test
    @DisplayName("Тест метода по сохранению  файла со списком транзакций в формате Txt ")
    void testSaveToTXTFile(){
        String stringForSave = "string for save";
        assertTrue (transactionsFileService.saveToTXTFile(stringForSave));
    }
    @Test
    @DisplayName("Тест метода по чтению  сохраненного файла со списком транзакций в формате Json ")
    void testReadFromTXTFile()throws IOException {
        String stringForSave = "string for save";
        transactionsFileService.saveToTXTFile(stringForSave);
        String saveString = Files.readString(Path.of(pathTXTFileForTesting, nameTXTFileForTesting));
        assertEquals(stringForSave, saveString);
    }
    @Test
    @DisplayName("Тест метода по чтению  сохраненного файла со списком транзакций в формате Json ")
    void testReadFromJsonFile()throws IOException {
        String stringForSave = "string for save";
        transactionsFileService.saveToJsonFile(stringForSave);
        String saveString = Files.readString(Path.of(pathJsonFileForTesting, nameJsonFileForTesting));
        assertEquals(stringForSave, saveString);
    }












}