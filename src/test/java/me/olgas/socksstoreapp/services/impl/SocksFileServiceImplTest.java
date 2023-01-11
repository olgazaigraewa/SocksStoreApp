package me.olgas.socksstoreapp.services.impl;

import me.olgas.socksstoreapp.services.SocksFileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class SocksFileServiceImplTest {

    private final SocksFileService socksFileService = new SocksFileServiceImpl();

    @TempDir
    File tempDirForTesting;

    private String pathFileForTesting;

    private String nameFileForTesting;

    @BeforeEach
    void setUp(){
        ReflectionTestUtils.setField(socksFileService, "socksFilePath",tempDirForTesting.getPath());
        ReflectionTestUtils.setField(socksFileService,"socksFileName", "socks.json");
        pathFileForTesting = socksFileService.getSocksFilePath();
        nameFileForTesting = socksFileService.getSocksFileName();
    }
    @Test
    @DisplayName("Тест метода по очистке файла Json")
    void testCleanFromFile() throws IOException{
        assertTrue(socksFileService.cleanSocksFile());
        assertTrue(Files.exists(Path.of(pathFileForTesting, nameFileForTesting)));
        assertTrue(Files.deleteIfExists(Path.of(pathFileForTesting, nameFileForTesting)));
        assertFalse(Files.exists(Path.of(pathFileForTesting, nameFileForTesting)));

    }
    @Test
    @DisplayName("Тест метода по получению ранее сохраненного файла Json")
    void testGetSocks(){
        File testFile = new File(pathFileForTesting + "/" + nameFileForTesting);
        assertEquals(testFile, socksFileService.getSocks());
    }


    @Test
    @DisplayName("Тест метода по сохранению в файл списка товара в формате Json")
    void testSaveToFile(){
        String stringForSave = "string for save";
        assertTrue(socksFileService.saveToFile(stringForSave));
    }

    @Test
    @DisplayName("Тест метода по получению списка товара из файла Json")
    void testReadFromFile() throws IOException{
        String stringForSave = "string for save";
        socksFileService.saveToFile(stringForSave);
        String saveString = Files.readString(Path.of(pathFileForTesting, nameFileForTesting));
        assertEquals(stringForSave, saveString);

    }







}