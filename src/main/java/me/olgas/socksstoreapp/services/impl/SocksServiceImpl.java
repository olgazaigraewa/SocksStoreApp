package me.olgas.socksstoreapp.services.impl;

import me.olgas.socksstoreapp.model.SocksColor;
import me.olgas.socksstoreapp.model.SocksSize;
import me.olgas.socksstoreapp.model.TransactionsType;
import me.olgas.socksstoreapp.repositiry.SocksRepository;
import me.olgas.socksstoreapp.repositiry.TransactionRepository;
import me.olgas.socksstoreapp.services.SocksService;
import org.springframework.stereotype.Service;

@Service
public class SocksServiceImpl implements SocksService {

    private final TransactionRepository transactionsRepository;
    private final SocksRepository socksRepository;

    public SocksServiceImpl(TransactionRepository transactionsRepository, SocksRepository socksRepository) {
        this.transactionsRepository = transactionsRepository;
        this.socksRepository = socksRepository;
    }


    @Override
    public boolean addSocks(SocksColor socksColor,
                            SocksSize socksSize,
                            int cottonPart,
                            int quantity) {
        transactionsRepository.addTransaction(socksColor, socksSize, cottonPart, quantity, TransactionsType.INCOME);
        return socksRepository.addInSocksRepository(socksColor, socksSize, cottonPart, quantity);
    }


    @Override
    public boolean removeSocks(SocksColor socksColor, SocksSize socksSize, int cottonPart, int quantity) {
        transactionsRepository.addTransaction(socksColor, socksSize, cottonPart, quantity, TransactionsType.OUTCOME);
        return socksRepository.out(socksColor, socksSize, cottonPart, quantity);
    }

    @Override
    public boolean disposalSocks(SocksColor socksColor, SocksSize socksSize, int cottonPart, int quantity) {
        transactionsRepository.addTransaction(socksColor, socksSize, cottonPart, quantity, TransactionsType.CANCELLATION);
        return socksRepository.delete(socksColor, socksSize, cottonPart, quantity);
    }

    @Override
    public int findTheMinimumCottonPart(SocksColor socksColor, SocksSize socksSize, int cottonMin) {
        return socksRepository.findTheMinimumCottonPart(socksColor,socksSize,cottonMin);
    }

    @Override
    public int findTheMaximumCottonPart(SocksColor socksColor, SocksSize socksSize, int cottonMax) {
        return socksRepository.findTheMaximumCottonPart(socksColor,socksSize,cottonMax);
    }


}


