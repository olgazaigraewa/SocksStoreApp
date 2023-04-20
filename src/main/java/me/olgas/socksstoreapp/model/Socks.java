package me.olgas.socksstoreapp.model;

import lombok.Data;


/**
 * Класс-сущность, описывает товар
 */
@Data
public class Socks {
    private SocksColor socksColor;
    private SocksSize socksSize;
    private int cottonPart;
    private int quantity;


    public Socks(SocksColor socksColor, SocksSize socksSize, int cottonPart, int quantity) {
        this.socksColor = socksColor;
        this.socksSize = socksSize;
        if (cottonPart > 0 && cottonPart <= 100) {
            this.cottonPart = cottonPart;
        } else {
            throw new IllegalArgumentException("содержание хлопка " + cottonPart + " % ");
        }
        this.quantity = quantity;
    }
}
