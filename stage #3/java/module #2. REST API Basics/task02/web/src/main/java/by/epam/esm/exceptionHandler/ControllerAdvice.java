package by.epam.esm.exceptionHandler;

import by.epam.esm.exception.ErrorCode;
import by.epam.esm.exception.ErrorMessage;
import by.epam.esm.exception.ErrorResponse;
import by.epam.esm.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Set;

@RestControllerAdvice

public class ControllerAdvice {
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerAdvice.class);

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleServiceException(ServiceException serviceException) {
        return getResponseEntity(serviceException,
                serviceException.getErrorCode(),
                serviceException.getErrorMessage(),
                serviceException.getProblems());
    }
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleUnsupportedOperationException(
            UnsupportedOperationException exception) {
        return getResponseEntityWithOutSet(exception, ErrorCode.UNSUPPORTED_OPERATION, exception.getLocalizedMessage());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException exception) {
        return getResponseEntityWithOutSet(exception, ErrorCode.ILLEGAL_ARGUMENT, exception.getLocalizedMessage());
    }


    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleDaoException(DataAccessException exception) {
        return getResponseEntityWithOutSet(exception, ErrorCode.DATA_ACCESS, exception.getLocalizedMessage());
    }


    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleMethodNotSupportedException(
            HttpRequestMethodNotSupportedException exception) {
        return getResponseEntityWithOutSet(exception, ErrorCode.METHOD_NOT_SUPPORTED, exception.getLocalizedMessage());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleNotSupportedMediaTypeException(
            HttpMediaTypeNotSupportedException exception) {
        return getResponseEntityWithOutSet(exception, ErrorCode.MEDIA_TYPE_NOT_SUPPORTED,
                exception.getLocalizedMessage());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleMessageNotReadableException(
            HttpMessageNotReadableException exception) {
        return getResponseEntityWithOutSet(exception, ErrorCode.HTTP_MESSAGE_NOT_READABLE, exception.getLocalizedMessage());
    }




    private ResponseEntity<ErrorResponse> getResponseEntity(Throwable exception, ErrorCode errorCode, String message, Set<ErrorMessage> errorMessages) {
        ErrorResponse errorResponse = prepareErrorResponse(errorCode.getErrorCode(), message);
        errorResponse.setProblems(errorMessages);
        logException(errorCode,exception);
        return new ResponseEntity<>(errorResponse, errorCode.getHttpStatus());
    }
    private ResponseEntity<ErrorResponse> getResponseEntityWithOutSet(Throwable exception, ErrorCode errorCode, String message) {
        ErrorResponse errorResponse = prepareErrorResponse(errorCode.getErrorCode(), message);
        logException(errorCode,exception);
        return new ResponseEntity<>(errorResponse, errorCode.getHttpStatus());
    }

    private ErrorResponse prepareErrorResponse(String errorCode, String errorDetails) {
        ErrorResponse newErrorResponse = new ErrorResponse();
        newErrorResponse.setErrorMessage(errorDetails);
        newErrorResponse.setErrorCode(errorCode);
        return newErrorResponse;
    }

    private void logException(ErrorCode errorCode, Throwable exception) {
        LOGGER.error(errorCode.getErrorCode(), errorCode.getMessage(), exception);
    }

}
