package pl.rafalzebrowski.doctopdf.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.rafalzebrowski.doctopdf.exception.BusinessException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.Objects;

import static pl.rafalzebrowski.doctopdf.common.FileUtils.DEFAULT_OUTPUT_EXTENSION;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public File saveToTemp(MultipartFile file) {
        File tempFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        saveToFile(file, tempFile);
        return tempFile;
    }

    @Override
    public File saveArrayByteToFile(byte[] bytes, String fileName) {
        File outputFile = new File(fileName + DEFAULT_OUTPUT_EXTENSION);
        saveToFile(bytes, outputFile);
        return outputFile;
    }

    public byte[] downloadFileBytes(String fileName) throws IOException {
        File file = new File(fileName);
        try {
            return Files.readAllBytes(file.toPath());
        } catch (NoSuchFileException ex) {
            throw new BusinessException("Brak pliku");
        }
    }

    private void saveToFile(MultipartFile input, File output) {
        try (OutputStream outputStream = new FileOutputStream(output)) {
            outputStream.write(input.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveToFile(byte[] bytes, File fileOutput) {
        try (OutputStream outputStream = new FileOutputStream(fileOutput)) {
            outputStream.write(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
