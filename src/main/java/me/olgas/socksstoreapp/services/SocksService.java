package me.olgas.socksstoreapp.services;

import me.olgas.socksstoreapp.model.SocksColor;
import me.olgas.socksstoreapp.model.SocksSize;

public interface SocksService {

 boolean addSocks(SocksColor socksColor, SocksSize socksSize,int cottonPart, int quantity);
 boolean removeSocks(SocksColor socksColor, SocksSize socksSize,int cottonPart, int quantity);
 boolean disposalSocks(SocksColor socksColor, SocksSize socksSize,int cottonPart, int quantity);



 int findTheMinimumCottonPart(SocksColor socksColor, SocksSize socksSize, int cottonMin);

 int findTheMaximumCottonPart(SocksColor socksColor, SocksSize socksSize, int cottonMax);




}
