package me.olgas.socksstoreapp.model;

import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Класс-сущность, описывает движение товара
 */
@Data
@NoArgsConstructor
public class Transaction {
    private  SocksColor socksColor;
    private   SocksSize socksSize;
    private int cottonPart;
    private int quantity;
    private String timeCreateTransaction;
    private String dateCreateTransaction;
    private TransactionsType transactionsType;


    public Transaction(SocksColor socksColor, SocksSize socksSize, int cottonPart, int quantity,
                       String timeCreateTransaction, String dateCreateTransaction, TransactionsType transactionsType) {
        this.socksColor = socksColor;
        this.socksSize = socksSize;
        this.cottonPart = cottonPart;
        if (quantity>0){
            this.quantity= quantity;
        }else{
            throw new IllegalArgumentException("Количество товара равно нулю");
        }
        this.timeCreateTransaction = timeCreateTransaction;
        this.dateCreateTransaction = dateCreateTransaction;
        this.transactionsType = transactionsType;
    }

    public String getTranslation() {
        return socksColor.translation;
    }
}
