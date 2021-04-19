package by.epam.esm.exception;

public class ErrorMessage {
    private String field;
    private String unCorrectValue;
    private String message;

    public ErrorMessage() {
    }

    public ErrorMessage(String field, String unCorrectValue, String message) {
        this.field = field;
        this.unCorrectValue = unCorrectValue;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getUnCorrectValue() {
        return unCorrectValue;
    }

    public void setUnCorrectValue(String unCorrectValue) {
        this.unCorrectValue = unCorrectValue;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
