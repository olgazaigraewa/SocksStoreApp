package me.olgas.socksstoreapp.services.impl;

import me.olgas.socksstoreapp.model.SocksColor;
import me.olgas.socksstoreapp.model.SocksSize;
import me.olgas.socksstoreapp.model.TransactionsType;
import me.olgas.socksstoreapp.repositiry.SocksRepository;
import me.olgas.socksstoreapp.repositiry.TransactionRepository;
import me.olgas.socksstoreapp.services.SocksService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SocksServiceImplTest {

    private final TransactionRepository transactionRepository = Mockito.mock(TransactionRepository.class);
    private final SocksRepository socksRepository = Mockito.mock(SocksRepository.class);
    private SocksService socksService = new SocksServiceImpl(transactionRepository, socksRepository);

    SocksColor socksColor = SocksColor.BLACK;
    SocksSize socksSize = SocksSize.L;
    int cottonPart = 40;
    int quantity = 100;
    int cottonMin = 70;
    int cottonMax = 30;
    int expectedValueSocks = 10;

    @BeforeEach
    public void setUp(){
      socksService = new SocksServiceImpl(transactionRepository, socksRepository);
    }


    @Test
    @DisplayName("Проверка корректной работы метода  добавления товара на склад")
    public void testAddSocks() {
        when(socksRepository.addInSocksRepository(socksColor, socksSize, cottonPart, quantity)).thenReturn(true);
        when(transactionRepository.addTransaction(socksColor, socksSize, cottonPart, quantity, TransactionsType.INCOME)).thenReturn(true);
        boolean correctAddSocks = socksService.addSocks(socksColor, socksSize, cottonPart, quantity);
        assertTrue(correctAddSocks);
    }
    @Test
    @DisplayName("Тест метода - на неуспешное добавления носков в список")
    void testAddToWarehouseNotSuccess() {
        when(socksRepository.addInSocksRepository(socksColor, socksSize, cottonPart, quantity)).thenReturn(false);
        when(transactionRepository.addTransaction(socksColor, socksSize, cottonPart, quantity, TransactionsType.INCOMING)).thenReturn(false);
        boolean notCorrectAddInStorage = socksService.addSocks(socksColor,socksSize, cottonPart,quantity);
        assertFalse(notCorrectAddInStorage);
    }

    @Test
    @DisplayName("Проверка корректной работы метода  отпуска товара со склада")
    public void testRemoveSocks() {
        when(socksRepository.out(socksColor, socksSize, cottonPart, quantity)).thenReturn(true);
        when(transactionRepository.addTransaction(socksColor, socksSize, cottonPart, quantity, TransactionsType.OUTCOME)).thenReturn(true);
        boolean correctRemoveSocks = socksService.removeSocks(socksColor, socksSize, cottonPart, quantity);
        assertTrue(correctRemoveSocks);
    }

    @Test
    @DisplayName("Проверка корректной работы метода  списания бракованного товара со склада")
    public void testDisposalSocks() {
        when(socksRepository.delete(socksColor, socksSize, cottonPart, quantity)).thenReturn(true);
        when(transactionRepository.addTransaction(socksColor, socksSize, cottonPart, quantity, TransactionsType.CANCELLATION)).thenReturn(true);
        boolean correctDisposalSocks = socksService.disposalSocks(socksColor, socksSize, cottonPart, quantity);
        assertTrue(correctDisposalSocks);
    }

    @Test
    @DisplayName("Проверка корректной работы метода определения общего количества носков на складе с минимальным содержанием хлопка")
    public void testFindTheMinimumCottonPart() {
        when(socksRepository.findTheMinimumCottonPart(socksColor, socksSize, cottonMin)).thenReturn(expectedValueSocks);
        int socksValue = socksService.findTheMinimumCottonPart(socksColor, socksSize, cottonMin);
        assertEquals(expectedValueSocks, socksValue);
    }

    @Test
    @DisplayName("Проверка корректной работы метода определения общего количества носков на складе с максимальным содержанием хлопка")
    public void testFindTheMaximumCottonPart() {
        when(socksRepository.findTheMaximumCottonPart(socksColor, socksSize, cottonMax)).thenReturn(expectedValueSocks);
        int socksValue = socksService.findTheMaximumCottonPart(socksColor, socksSize, cottonMax);
        assertEquals(expectedValueSocks, socksValue);
    }


}
