package by.epam.esm.exception;

import org.springframework.http.HttpStatus;

public enum  ErrorCode {
    DATA_ACCESS(HttpStatus.INTERNAL_SERVER_ERROR,"40001","Data Access Exception" ),
    CERTIFICATE_NAME_IS_ALREADY_EXISTS(HttpStatus.BAD_REQUEST,"40002","This name certificate is already exist"),
    NAME_TAG_IS_ALREADY_EXIST(HttpStatus.BAD_REQUEST,"40003","This name tag is already exist"),
    HTTP_MESSAGE_NOT_READABLE(HttpStatus.BAD_REQUEST,"40004", "Message not readeble exception"),
    NOT_VALID_DATA(HttpStatus.UNPROCESSABLE_ENTITY,"40201","Invalid input data"),
    ILLEGAL_ARGUMENT(HttpStatus.UNPROCESSABLE_ENTITY,"42202","Illegal arg exception"),
    NOT_FIND_TAG_BY_ID(HttpStatus.NOT_FOUND,"40401","Not find tag by this id"),
    NOT_FIND_CERTIFICATE_BY_ID(HttpStatus.NOT_FOUND,"40402","Not find certificate by this id"),
    NOT_FIND_TAG_BY_ID_WITH_THIS_NAME(HttpStatus.NOT_FOUND,"40403","Not find tag by this id with this name"),
    ALL_FIELD_IS_NULL(HttpStatus.BAD_REQUEST,"42205","All field is Null"),
    METHOD_NOT_SUPPORTED( HttpStatus.METHOD_NOT_ALLOWED,"40501","Method not support"),
    UNSUPPORTED_OPERATION(HttpStatus.METHOD_NOT_ALLOWED,"40502","UnSupport operation" ),
    MEDIA_TYPE_NOT_SUPPORTED(HttpStatus.UNSUPPORTED_MEDIA_TYPE,"41503", "Media Type Not Support" ),
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"50001","Was Find Server Error");


    ErrorCode(HttpStatus httpStatus, String errorCode,String message) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.message = message;
    }

    private HttpStatus httpStatus;
    private String errorCode;
    private String message;

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }
}
