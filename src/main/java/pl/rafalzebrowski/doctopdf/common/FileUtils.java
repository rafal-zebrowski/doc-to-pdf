package pl.rafalzebrowski.doctopdf.common;

import org.apache.commons.io.FilenameUtils;
import org.apache.tika.detect.DefaultDetector;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class FileUtils {

    public final static String DEFAULT_OUTPUT_EXTENSION = ".pdf";

    public static String getExtension(MultipartFile file) {
        return FilenameUtils.getExtension(file.getOriginalFilename());
    }

    public static String getFilenameWithoutExtension(MultipartFile file) {
        return FilenameUtils.removeExtension(file.getOriginalFilename());
    }

    public static String detectExtensionByContent(MultipartFile file) {
        try {
            return new DefaultDetector().detect(TikaInputStream.get(file.getInputStream()), new Metadata()).toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
