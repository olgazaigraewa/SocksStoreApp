package me.olgas.socksstoreapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.olgas.socksstoreapp.model.SocksColor;
import me.olgas.socksstoreapp.model.SocksSize;
import me.olgas.socksstoreapp.services.SocksService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/socks")
@Tag(name = "Наименование товара - Носки", description = " CRUD-операции для работы с товаром")
public class SocksController {
    private final SocksService socksService;

    public SocksController(SocksService socksService) {
        this.socksService = socksService;
    }

    @PostMapping("/income/{socksColor}/ {socksSize}/ {cottonPart}/ {quantity}")
    @Operation(summary = "Приход товара на склад")
    public ResponseEntity<String> income (@PathVariable ("socksColor")SocksColor socksColor,
                                          @PathVariable ("socksSize")SocksSize socksSize,
                                          @PathVariable ("cottonPart")int cottonPart,
                                          @PathVariable ("quantity")int quantity) {
        {
            if (socksService.addSocks(socksColor, socksSize, cottonPart, quantity)) {
                return ResponseEntity.status(HttpStatus.OK).body("Товар добавлен на склад");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ошибка 400. Товар не добавлен на склад.");
        }
    }

    @GetMapping(value = "/allSocksWithCottonMin/{socksColor}/ {socksSize}/ {cottonMin}")
    @Operation(summary = "Общее количество носков с минимальным содержанием хлопка в товаре")
    public ResponseEntity<String> findTheMinimumCottonPart(@PathVariable ("socksColor")SocksColor socksColor,
                                                           @PathVariable ("socksSize")SocksSize socksSize,
                                                           @PathVariable ("cottonMin")int cottonMin) {
        int quantity = socksService.findTheMinimumCottonPart(socksColor, socksSize, cottonMin);
        if (quantity > 0) {
            return ResponseEntity.status(HttpStatus.OK).body("По запросу найдено " + quantity + " пар носков");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ошибка 400. По запросу ничего не найдено.");
    }

    @GetMapping("/allSocksWithCottonMax/{socksColor}/ {socksSize}/ {cottonMax}")
    @Operation(summary = "Общее количество носков с минимальным содержанием хлопка в товаре")
    public ResponseEntity<String> findTheMaximumCottonPart(@PathVariable ("socksColor")SocksColor socksColor,
                                                           @PathVariable ("socksSize")SocksSize socksSize,
                                                           @PathVariable ("cottonMax")int cottonMax) {
        int quantity = socksService.findTheMaximumCottonPart(socksColor, socksSize, cottonMax);
        if (quantity > 0) {
            return ResponseEntity.status(HttpStatus.OK).body("По запросу найдено " + quantity + " пар носков");
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ошибка 400. По запросу ничего не найдено.");
        }
    }

    @PutMapping("/outcome/{socksColor}/ {socksSize}/ {cottonPart}/ {quantity}")
    @Operation(summary = "Отпуск товара со склада")
    public ResponseEntity<String> outcome(@PathVariable ("socksColor")SocksColor socksColor,
                                          @PathVariable ("socksSize")SocksSize socksSize,
                                          @PathVariable ("cottonPart")int cottonPart,
                                          @PathVariable ("quantity")int quantity)  {
        {
            if (socksService.removeSocks(socksColor, socksSize, cottonPart, quantity)) {
                return ResponseEntity.status(HttpStatus.OK).body("Товар отпущен со склада");
            }else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ошибка 400. " +
                        "Параметры запроса отсутствуют или имеют некорректный формат ");
            }
        }
    }

    @DeleteMapping("/cancellation/{socksColor}/ {socksSize}/ {cottonPart}/ {quantity}")
    @Operation(summary = "Списание бракованного товара")
    public ResponseEntity<String> cancellation(@PathVariable ("socksColor")SocksColor socksColor,
                                               @PathVariable ("socksSize")SocksSize socksSize,
                                               @PathVariable ("cottonPart")int cottonPart,
                                               @PathVariable ("quantity")int quantity) {
        {
            if (socksService.disposalSocks(socksColor, socksSize, cottonPart, quantity)) {
                return ResponseEntity.status(HttpStatus.OK).body("Бракованный товар списан со склада ");
            }else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ошибка 400. " +
                        "Параметры запроса отсутствуют или имеют некорректный формат ");
            }
        }
    }


}










