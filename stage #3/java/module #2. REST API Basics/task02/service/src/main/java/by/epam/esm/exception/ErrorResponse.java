package by.epam.esm.exception;

import java.util.Set;

public class ErrorResponse {
    private String errorCode;
    private String errorMessage;
    private Set<ErrorMessage> problems;

    public Set<ErrorMessage> getProblems() {
        return problems;
    }

    public void setProblems(Set<ErrorMessage> problems) {
        this.problems = problems;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
