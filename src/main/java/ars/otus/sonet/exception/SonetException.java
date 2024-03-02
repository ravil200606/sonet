package ars.otus.sonet.exception;

import ars.otus.sonet.model.enums.ErrorCodes;
import lombok.Getter;

import static ars.otus.sonet.model.enums.ErrorCodes.DEFAULT_ERROR_CODE;

@Getter
public class SonetException extends RuntimeException {

    private final ErrorCodes errorCode;

    public SonetException(String exceptionMessage) {
        super(exceptionMessage);
        errorCode = DEFAULT_ERROR_CODE;
    }

    public SonetException(String exceptionMessage, ErrorCodes code) {
        super(exceptionMessage);
        this.errorCode = code;
    }
}
