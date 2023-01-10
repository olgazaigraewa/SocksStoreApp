package me.olgas.socksstoreapp.services.impl;

import me.olgas.socksstoreapp.model.SocksColor;
import me.olgas.socksstoreapp.model.SocksSize;
import me.olgas.socksstoreapp.model.TransactionsType;
import me.olgas.socksstoreapp.repositiry.SocksRepository;
import me.olgas.socksstoreapp.repositiry.TransactionRepository;
import me.olgas.socksstoreapp.services.SocksService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SocksServiceImplTest {

    private final TransactionRepository transactionRepository = Mockito.mock(TransactionRepository.class);
    private final SocksRepository socksRepository = Mockito.mock(SocksRepository.class);
    private final SocksService socksService = new SocksServiceImpl(transactionRepository, socksRepository);

    SocksColor socksColor = SocksColor.BLACK;
    SocksSize socksSize = SocksSize.L;
    int cottonPart = 40;
    int quantity = 100;
    int cottonMin;
    int cottonMax;
    int expectedValueSocks;


    @Test
    @DisplayName("Тест метода добавления товара на склад")
    public void testAddSocks() {
        when(socksRepository.addInSocksRepository(socksColor, socksSize, cottonPart, quantity)).thenReturn(true);
        transactionRepository.addTransaction(socksColor, socksSize, cottonPart, quantity, TransactionsType.INCOME);
        boolean correctAddSocks = socksService.addSocks(socksColor, socksSize, cottonPart, quantity);
        assertTrue(correctAddSocks);
    }

    @Test
    @DisplayName("Тест метода отпуска товара со склада")
    public void testRemoveSocks() {
        when(socksRepository.out(socksColor, socksSize, cottonPart, quantity)).thenReturn(true);
        transactionRepository.addTransaction(socksColor, socksSize, cottonPart, quantity, TransactionsType.OUTCOME);
        boolean correctRemoveSocks = socksService.removeSocks(socksColor, socksSize, cottonPart, quantity);
        assertTrue(correctRemoveSocks);
    }

    @Test
    @DisplayName("Тест метода списания бракованного товара со склада")
    public void testDisposalSocks() {
        when(socksRepository.delete(socksColor, socksSize, cottonPart, quantity)).thenReturn(true);
        transactionRepository.addTransaction(socksColor, socksSize, cottonPart, quantity, TransactionsType.CANCELLATION);
        boolean correctDisposalSocks = socksService.disposalSocks(socksColor, socksSize, cottonPart, quantity);
        assertTrue(correctDisposalSocks);
    }

    @Test
    @DisplayName("Тест метода определения общего количества носков на складе с минимальным содержанием хлопка")
    public void testFindTheMinimumCottonPart() {
        when(socksRepository.findTheMinimumCottonPart(socksColor, socksSize, cottonMin)).thenReturn(expectedValueSocks);
        int socksValue = socksService.findTheMinimumCottonPart(socksColor, socksSize, cottonMin);
        assertEquals(expectedValueSocks, socksValue);
    }

    @Test
    @DisplayName("Тест метода определения общего количества носков на складе с максимальным содержанием хлопка")
    public void testFindTheMaximumCottonPart() {
        when(socksRepository.findTheMaximumCottonPart(socksColor, socksSize, cottonMin)).thenReturn(expectedValueSocks);
        int socksValue = socksService.findTheMaximumCottonPart(socksColor, socksSize, cottonMax);
        assertEquals(expectedValueSocks, socksValue);
    }


}
