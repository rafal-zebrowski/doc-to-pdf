package pl.rafalzebrowski.doctopdf.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlerController {

    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxFileSize;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {SizeLimitExceededException.class, MaxUploadSizeExceededException.class})
    protected Error handleSizeLimit(RuntimeException ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        return new Error("Plik ma zbyt duży rozmiar. Plik może mieć maksymalnie " + maxFileSize);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    protected Error handleBusinessException(BusinessException ex) {
        log.error(ex.getMessage(), ex);
        return new Error(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = RuntimeException.class)
    protected Error handleRuntimeException(RuntimeException ex) {
        log.error(ex.getMessage(), ex);
        return new Error("Wystąpił błąd. Skontaktuj się z administratorem");
    }
}
