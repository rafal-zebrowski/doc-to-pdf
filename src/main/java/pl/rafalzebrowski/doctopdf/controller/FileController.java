package pl.rafalzebrowski.doctopdf.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.rafalzebrowski.doctopdf.common.FileUtils;
import pl.rafalzebrowski.doctopdf.service.CloudmersiveApiServiceImpl;
import pl.rafalzebrowski.doctopdf.service.FileService;
import pl.rafalzebrowski.doctopdf.validator.FileValidator;

import java.io.File;
import java.io.IOException;

import static pl.rafalzebrowski.doctopdf.common.FileUtils.DEFAULT_OUTPUT_EXTENSION;


@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {

    private final FileService fileService;
    private final CloudmersiveApiServiceImpl officeService;
    private final FileValidator fileValidator;


    public FileController(FileValidator fileValidator, FileService fileService, CloudmersiveApiServiceImpl officeService) {
        this.fileService = fileService;
        this.officeService = officeService;
        this.fileValidator = fileValidator;
    }

    @PostMapping
    public String upload(@RequestParam MultipartFile inputFile) {
        log.info("Uploading and converting file: " + inputFile.getOriginalFilename());
        fileValidator.validateExtension(inputFile);
        File tempFile = fileService.saveToTemp(inputFile);

        byte[] result = officeService.convertDocumentDocxToPdf(tempFile);
        File outputFile = fileService.saveArrayByteToFile(result, FileUtils.getFilenameWithoutExtension(inputFile));

        tempFile.delete();
        log.info("Converted filename: " + outputFile.getName());

        return outputFile.getName();
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<byte[]> download(@PathVariable("fileName") String fileName) throws IOException {
        log.info("Downloading file: " + fileName);
        byte[] result = fileService.downloadFileBytes(fileName);
        return getResponse(result, fileName);
    }

    @PostMapping("/convert")
    public ResponseEntity<byte[]> convert(@RequestParam("inputFile") final MultipartFile inputFile) {
        log.info("Converting file: " + inputFile.getOriginalFilename());
        fileValidator.validateExtension(inputFile);
        File tempFile = fileService.saveToTemp(inputFile);

        byte[] result = officeService.convertDocumentDocxToPdf(tempFile);

        tempFile.delete();

        return getResponse(result, FileUtils.getFilenameWithoutExtension(inputFile) + DEFAULT_OUTPUT_EXTENSION);
    }

    private ResponseEntity<byte[]> getResponse(byte[] resultFile, String fileName) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_ENCODING, "UTF-8");
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(resultFile.length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resultFile);
    }
}
