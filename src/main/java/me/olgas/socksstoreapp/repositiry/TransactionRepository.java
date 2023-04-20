package me.olgas.socksstoreapp.repositiry;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import me.olgas.socksstoreapp.model.*;
import me.olgas.socksstoreapp.services.TransactionsFileService;

import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TreeMap;
import java.util.TreeSet;

@Data
@Repository
public class TransactionRepository {

    private TreeMap<Long, Transaction> transactionList;
    private final TransactionsFileService transactionsFileService;
    static Long idCounter = 1L;

    public TransactionRepository(TransactionsFileService transactionsFileService) {
        this.transactionsFileService = transactionsFileService;
        this.transactionList = new TreeMap<>();
    }



    public boolean addTransaction(SocksColor socksColor,
                               SocksSize socksSize,
                               int cottonPart,
                               int quantity,
                               TransactionsType transactionsType) {
        idCounter = 1L;
        while (transactionList.containsKey(idCounter)) {
            idCounter++;
        }
        String fullDateCreateTransaction = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy"));
        String onlyDate = fullDateCreateTransaction.substring(6, 16);
        String onlyTime = fullDateCreateTransaction.substring(0, 5);
        transactionList.put(idCounter, new Transaction(socksColor,
                socksSize,
                cottonPart,
                quantity,
                onlyDate,
                onlyTime,
                transactionsType));
        transactionsFileService.saveToJsonFile(jsonFromList());
        transactionsFileService.saveToTxtFile(viewAllTransactions());
        return true;
    }

    private String jsonFromList() {
        String json;
        try {
            json = new ObjectMapper().writeValueAsString(transactionList);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return json;
    }

    public String viewAllTransactions() {
        StringBuilder result = new StringBuilder();
        int counter = 0;
        result.append(" Список транзакций: \n");
        result.append(" Поступление на склад: \n");
        TreeSet<String> sortedDates = new TreeSet<>();
        for (Transaction externalStep : getTransactionList().values()) {
            if (externalStep.getTransactionsType().equals(TransactionsType.INCOME)) {
                sortedDates.add(externalStep.getDateCreateTransaction());
            }
        }
        counter = getCounter(result, counter, sortedDates);
        if (counter == 0) {
            result.append(" Поступлений на склад не было. ");
        }
        sortedDates.clear();
        counter = 0;
        result.append(" Выбытие со склада: \n");
        for (Transaction externalStep : getTransactionList().values()) {
            if (externalStep.getTransactionsType().equals(TransactionsType.OUTCOME)) {
                sortedDates.add(externalStep.getDateCreateTransaction());
            }
        }
        counter = getCounter(result, counter, sortedDates);
        if (counter == 0) {
            result.append(" Выбытия товара не было. \n");
        }
        sortedDates.clear();
        counter = 0;
        result.append(" Списание со склада: \n");
        for (Transaction externalStep : getTransactionList().values()) {
            if (externalStep.getTransactionsType().equals(TransactionsType.CANCELLATION)) {
                sortedDates.add(externalStep.getDateCreateTransaction());
            }
        }
        counter = getCounter(result, counter, sortedDates);
        if (counter == 0) {
            result.append(" Списания товара не было. \n");
        }
        return result.toString();
    }

    private int getCounter(StringBuilder result, int counter, TreeSet<String> sortedDates) {
        for (String date : sortedDates) {
            result.append(" дата - ").append(date).append(" \n");
            for (Transaction internalStep : getTransactionList().values()) {
                if (internalStep.getDateCreateTransaction().equals(date)) {
                    result.append(internalStep.getTimeCreateTransaction()).append(" ").append(" цвет ").
                            append(internalStep.getTranslation()).append(" , ").append(" размер ").
                            append(internalStep.getSocksSize()).append(" , ").append(" содержание хлопка ").
                            append(internalStep.getCottonPart()).append(" %,  ").append(" количество ").
                            append(internalStep.getQuantity()).append(" пар.\n");
                    counter++;
                }
            }
        }
        return counter;
    }

}




