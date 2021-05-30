package by.epam.esm.validator;

import by.epam.esm.exception.ErrorCode;
import by.epam.esm.exception.ErrorMessage;
import by.epam.esm.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
/**
 * class BaseValidator
 * class contains method for validate dto
 * @author Aliaksei Tkachuk
 * @version 1.0
 */
@Component
public class BaseValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseValidator.class);
    private final Validator validator;

    @Autowired
    public BaseValidator(Validator validator) {
        this.validator = validator;
    }

    /**
     * method dtoValidator
     * method for check all fields dto
     * @param dto - dto for validate
     */
    public void dtoValidator(Object dto) {
        boolean isUnCorrectDto = false;
        Set<ConstraintViolation<Object>> validate = validator.validate(dto);
        Set<ErrorMessage> errorMessages = new HashSet<>();
        for (ConstraintViolation constraintViolation : validate) {
            String property = constraintViolation.getPropertyPath().toString();
            Object value = Optional.ofNullable(constraintViolation.getInvalidValue())
                    .orElse("null");
            String message = constraintViolation.getMessage();
            errorMessages.add(createNewErrorMessage(property, value.toString(), message));
            LOGGER.error(dto.getClass().getSimpleName(), property, message);
            isUnCorrectDto = true;
        }
        if (isUnCorrectDto) {
            throw new ServiceException(ErrorCode.NOT_VALID_DATA,
                    ErrorCode.NOT_VALID_DATA.getMessage(),
                    errorMessages);
        }
    }

    /**
     * method dtoValidatorForPartUpdate
     * method got validate notNull field in dto
     * @param dto dto for validate
     */
    public void dtoValidatorForPartUpdate(Object dto) {
        boolean inCorrect = false;
        Set<ConstraintViolation<Object>> validate = validator.validate(dto);
        Set<ErrorMessage> errorMessages = new HashSet<>();
        for (ConstraintViolation constraintViolation : validate) {
            String property = constraintViolation.getPropertyPath().toString();
            Object value = Optional.ofNullable(constraintViolation.getInvalidValue())
                    .orElse("null");
            String message = constraintViolation.getMessage();
            if (value != "null") {
                errorMessages.add(createNewErrorMessage(property, value.toString(), message));
                inCorrect = true;
            }
        }
        if (inCorrect) {
            throw new ServiceException(ErrorCode.NOT_VALID_DATA,
                    ErrorCode.NOT_VALID_DATA.getMessage(),
                    errorMessages);
        }
    }

    /**
     * method createNewErrorMessage
     * method for create new error message
     * @param path - problem field
     * @param unCorrectValue - in correct value
     * @param message - error message
     * @return new ErrorMessage
     */
    private ErrorMessage createNewErrorMessage(String path, String unCorrectValue, String message) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setField(path);
        errorMessage.setMessage(message);
        errorMessage.setUnCorrectValue(unCorrectValue);
        return errorMessage;
    }
}
