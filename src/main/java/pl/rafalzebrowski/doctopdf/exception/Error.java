package pl.rafalzebrowski.doctopdf.exception;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Error {
    private String message;

    public Error(String message) {
        this.message = message;
    }
}
