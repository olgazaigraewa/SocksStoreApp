package me.olgas.socksstoreapp.repositiry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import me.olgas.socksstoreapp.model.Socks;
import me.olgas.socksstoreapp.model.SocksColor;
import me.olgas.socksstoreapp.model.SocksSize;
import me.olgas.socksstoreapp.services.SocksFileService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

@Data
@Repository
public class SocksRepository {

    private HashMap<Long, Socks> repository;
    private final SocksFileService socksFileService;
    static Long idCounter = 1L;

    public SocksRepository(SocksFileService socksFileService) {
        this.socksFileService = socksFileService;
        this.repository = new HashMap<>();
    }

    public int cottonMin;
    public int cottonMax;

    @PostConstruct
    private void init() {
        repository = listFromFile();
    }

    public int findTheMinimumCottonPart(SocksColor socksColor, SocksSize socksSize, int cottonMin) {
        int quantity = 0;
        Collection<Socks> socks = repository.values();
        for (Socks socks1 : socks) {
            if (socks1.getCottonPart() < cottonMin
                    & socks1.getSocksColor().equals(socksColor)
                    & socks1.getSocksSize().equals(socksSize)) {
                quantity += socks1.getQuantity();
            }
        }
        return quantity;
    }

    public int findTheMaximumCottonPart(SocksColor socksColor, SocksSize socksSize, int cottonMax) {
        int quantity = 0;
        Collection<Socks> socks = repository.values();
        for (Socks socks1 : socks) {
            if (socks1.getCottonPart() > cottonMax
                    & socks1.getSocksColor().equals(socksColor)
                    & socks1.getSocksSize().equals(socksSize)) {
                quantity += socks1.getQuantity();
            }
        }
        return quantity;
    }

    public boolean addInSocksRepository(SocksColor socksColor,
                                        SocksSize socksSize,
                                        int cottonPart,
                                        int quantity) {
        if (checkInputValues(cottonPart, quantity)) {
            Socks bufferSocks = new Socks(socksColor, socksSize, cottonPart, quantity);
            if (repository.isEmpty()) {
                repository.put(idCounter, bufferSocks);
                idCounter++;
                socksFileService.cleanSocksFile();
                socksFileService.saveToFile(jsonFromList());
                return true;
            }
            if (checkContainsInputValues(socksColor, socksSize, cottonPart)) {
                Long currentKey = findKey(socksColor, socksSize, cottonPart);
                int newQuantity = repository.get(currentKey).getQuantity() + bufferSocks.getQuantity();
                bufferSocks.setQuantity(newQuantity);
                repository.remove(currentKey);
                repository.put(currentKey, bufferSocks);
                socksFileService.cleanSocksFile();
                socksFileService.saveToFile(jsonFromList());
                return true;
            }
            if (!checkContainsInputValues(socksColor, socksSize, cottonPart)) {
                idCounter = 1L;
                while (repository.containsKey(idCounter)) {
                    idCounter++;
                }
                repository.put(idCounter, bufferSocks);
                idCounter++;
                socksFileService.cleanSocksFile();
                socksFileService.saveToFile(jsonFromList());
                return true;
            }
        }
        return false;
    }

    public boolean delete(SocksColor socksColor,
                          SocksSize socksSize,
                          int cottonPart,
                          int quantity) {
        Long bufferKey = findKey(socksColor, socksSize, cottonPart);
        if (repository.containsKey(bufferKey)) {
            repository.remove(bufferKey);
            socksFileService.cleanSocksFile();
            socksFileService.saveToFile(jsonFromList());
            return true;
        }
        return false;
    }

    public boolean out(SocksColor socksColor,
                       SocksSize socksSize,
                       int cottonPart,
                       int quantity) {
        Long bufferKey = findKey(socksColor, socksSize, cottonPart);
        if (repository.containsKey(bufferKey)) {
            Socks bufferSocks = repository.get(bufferKey);
            int bufferQuantity = bufferSocks.getQuantity() - quantity;
            if (bufferQuantity > 0) {
                bufferSocks.setQuantity(bufferQuantity);
                repository.remove(bufferKey);
                repository.put(bufferKey, bufferSocks);
                socksFileService.cleanSocksFile();
                socksFileService.saveToFile(jsonFromList());
                return true;
            }
            if (bufferQuantity == 0) {
                repository.remove(bufferKey);
                socksFileService.cleanSocksFile();
                socksFileService.saveToFile(jsonFromList());
                return true;
            }
        }
        return false;
    }

    private boolean checkInputValues(int cottonPart,
                                     int quantity) {
        return cottonPart > 0 & cottonPart <= 100 & quantity > 0;
    }

    private boolean checkContainsInputValues(SocksColor socksColor,
                                             SocksSize socksSize,
                                             int cottonPart) {
        for (Socks socksEntry : repository.values()) {
            if (socksEntry.getSocksColor().equals(socksColor) &
                    socksEntry.getSocksSize().equals(socksSize) &
                    socksEntry.getCottonPart() == cottonPart) {
                return true;
            }
        }
        return false;
    }

    private HashMap<Long, Socks> listFromFile() {
        try {
            String json = socksFileService.readFromFile();
            if (StringUtils.isNotBlank(json) || StringUtils.isNotEmpty(json)) {
                repository = new ObjectMapper().readValue(json, new TypeReference<>() {
                });
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return repository;
    }

    private String jsonFromList() {
        String json;
        try {
            json = new ObjectMapper().writeValueAsString(repository);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return json;
    }

    private Long findKey(SocksColor socksColor,
                         SocksSize socksSize,
                         int cottonPart) {
        Long bufferKey = 1L;
        Set<Long> keys = repository.keySet();
        for (Long key : keys) {
            if (checkContainsInputValues(socksColor, socksSize, cottonPart)) {
                bufferKey = key;
            }
        }
        return bufferKey;
    }

}
