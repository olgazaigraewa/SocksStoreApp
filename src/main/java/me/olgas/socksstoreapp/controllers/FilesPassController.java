package me.olgas.socksstoreapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.olgas.socksstoreapp.services.SocksFileService;
import me.olgas.socksstoreapp.services.TransactionsFileService;
import org.apache.commons.io.IOUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/files")
@Tag(name = "Отправка  и загрузка файлов",
        description = " Операции по работе с файлами: отправка, загрузка, сохранение.")
public class FilesPassController {

    private final SocksFileService socksFileService;

    private final TransactionsFileService transactionsFileService;

    public FilesPassController(SocksFileService socksFileService, TransactionsFileService transactionsFileService) {
        this.socksFileService = socksFileService;
        this.transactionsFileService = transactionsFileService;
    }

    @GetMapping(value = "/socksExport")
    @Operation(summary = "Сохранение файла с остатками товара на складе на компьютер пользователя в формате json")
    public ResponseEntity<InputStreamResource> dowloadSocksJsonFile() throws FileNotFoundException {
        File file = socksFileService.getSocksJson();
        if (file.exists()) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .contentLength(file.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename = \"socks.json\"")
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/socksImport", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Загрузка файла с остатками товара на складе с компьютера пользоаателя")
    public ResponseEntity<Void> uploadSocksFile(@RequestParam MultipartFile file) {
        socksFileService.cleanSocksFile();
        File dataFile = socksFileService.getSocksJson();
        if (file != null) {
            try (FileOutputStream fos = new FileOutputStream(dataFile)) {
                IOUtils.copy(file.getInputStream(), fos);
                return ResponseEntity.ok().build();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ResponseEntity.internalServerError().build();
    }

    @GetMapping(value = "/transactionsExport")
    @Operation(summary = "Сохранение файла со списком транзакций на компьютер пользователя в формате json")
    public ResponseEntity<InputStreamResource> dowloadTransactionsJsonFile() throws FileNotFoundException {
        File file = transactionsFileService.getTransactions();
        if (file.exists()) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok().
                    contentLength(file.length()).
                    contentType(MediaType.APPLICATION_JSON).
                    header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename = \"transactions.json\"").
                    body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/transactionsImport", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Загрузка сохраненного файла со списком транзакций с компьютера пользоаателя")
    public ResponseEntity<Void> uploadTransactionsFile(@RequestParam MultipartFile file) {
        File dataFile = transactionsFileService.getTransactions();
        if (!(file == null)) {
            try (FileOutputStream fos = new FileOutputStream(dataFile)) {
                IOUtils.copy(file.getInputStream(), fos);
                return ResponseEntity.ok().build();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ResponseEntity.internalServerError().build();
    }

    @GetMapping(value = "saveTransactionsTxt")
    @Operation(summary = "Сохранение файла с данными товара на компьютер пользователя в формате txt")
    public ResponseEntity<InputStreamResource> dowloadTransactionsFileTxt() throws FileNotFoundException {
        File file = transactionsFileService.getTxtFile();
        if (file.exists()) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok().
                    contentLength(file.length()).
                    contentType(MediaType.TEXT_PLAIN).
                    header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename = \"transactions.txt\"" + LocalDateTime.now()).
                    body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}









