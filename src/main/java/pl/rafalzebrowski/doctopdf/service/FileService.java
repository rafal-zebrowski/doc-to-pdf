package pl.rafalzebrowski.doctopdf.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface FileService {
    File saveToTemp(MultipartFile file);
    File saveArrayByteToFile(byte[] from, String fileName);
    byte[] downloadFileBytes(String fileName) throws IOException;
}
