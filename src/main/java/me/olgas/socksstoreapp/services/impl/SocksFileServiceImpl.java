package me.olgas.socksstoreapp.services.impl;

import me.olgas.socksstoreapp.services.SocksFileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class SocksFileServiceImpl implements SocksFileService {
    @Value("${path.to.socks.file}")
    private String socksFilePath;

    @Value("${name.of.socks.file}")
    private String socksFileName;

    @Override
    public boolean saveToFile(String json) {
        try {
            cleanSocksFile();
            Files.writeString(Path.of(socksFilePath, socksFileName), json);
            return true;
        } catch (IOException e) {
            return false;
        }

    }

    @Override
    public String readFromFile() {
        if (Files.exists(Path.of(socksFilePath, socksFileName))) {
            try {
                Files.readString(Path.of(socksFilePath, socksFileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public boolean cleanSocksFile() {
        try {
            Path path = Path.of(socksFilePath, socksFileName);
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public File getSocks() {
        return new File(socksFilePath + "/" + socksFileName);
    }


}
