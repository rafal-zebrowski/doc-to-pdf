package pl.rafalzebrowski.doctopdf.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlerController {

    @ExceptionHandler(value = {SizeLimitExceededException.class, MaxUploadSizeExceededException.class})
    protected Error handleSizeLimit(RuntimeException ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        return new Error("Plik ma zbyt duży rozmiar");
    }

    @ExceptionHandler
    protected Error handleBusinessException(BusinessException ex) {
        log.error(ex.getMessage(), ex);
        return new Error(ex.getMessage());
    }

    @ExceptionHandler(value = RuntimeException.class)
    protected Error handleRuntimeException(RuntimeException ex) {
        log.error(ex.getMessage(), ex);
        return new Error("Wystąpił błąd. Skontaktuj się z administratorem");
    }
}
