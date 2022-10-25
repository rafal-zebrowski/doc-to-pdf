package pl.rafalzebrowski.doctopdf.validator;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import pl.rafalzebrowski.doctopdf.common.FileUtils;
import pl.rafalzebrowski.doctopdf.exception.BusinessException;

import static pl.rafalzebrowski.doctopdf.common.FileConstants.FILE_EXTENSION_ALLOWED;

@Component
public class FileValidator {

    public void validateExtension(MultipartFile file) {
        String extension = FileUtils.getExtension(file);
        String extensionByContent = FileUtils.detectExtensionByContent(file);

        if(FILE_EXTENSION_ALLOWED.stream().noneMatch(extension::equals) ||
            FILE_EXTENSION_ALLOWED.stream().noneMatch(extensionByContent::equals)) {
            throw new BusinessException("Nieprawidłowe rozszerzenie pliku");
        }
    }
}
