package by.epam.esm.exception;

import java.util.Set;

public class ServiceException extends RuntimeException {
    private ErrorCode errorCode;
    private String errorMessage;
    private Set<ErrorMessage> problems;

    public ServiceException(Throwable cause, ErrorCode errorCode, String errorMessage) {
        super(cause);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public ServiceException(ErrorCode errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;

    }

    public ServiceException(ErrorCode errorCode, String errorMessage, Set<ErrorMessage> problems) {
        this.problems=problems;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;

    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public Set<ErrorMessage> getProblems() {
        return problems;
    }
}
