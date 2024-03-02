package ars.otus.sonet.model.enums;

import lombok.Getter;
import org.apache.http.HttpStatus;

/**
 * Коды ошибок.
 */
@Getter
public enum ErrorCodes {
    BAD_CREDENTIALS(401, HttpStatus.SC_UNAUTHORIZED),
    NOT_FOUND(404, HttpStatus.SC_NOT_FOUND),
    DEFAULT_ERROR_CODE(500, HttpStatus.SC_INTERNAL_SERVER_ERROR);

    ErrorCodes(Integer code, int httpCode) {
        this.internalErrorCode = code;
        this.httpStatusCode = httpCode;
    }

    private final Integer internalErrorCode;
    private final int httpStatusCode;
}
