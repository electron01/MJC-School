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
import java.lang.reflect.Field;
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

    private Validator validator;

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
        boolean isUnCorrectDto = false;
        Set<ConstraintViolation<Object>> validate = validator.validate(dto);
        Set<ErrorMessage> errorMessages = new HashSet<>();
        for (ConstraintViolation constraintViolation : validate) {
            String property = constraintViolation.getPropertyPath().toString();
            Object value = Optional.ofNullable(constraintViolation.getInvalidValue())
                    .orElse("null");
            String message = constraintViolation.getMessage();
            if (value != "null") {
                errorMessages.add(createNewErrorMessage(property, value.toString(), message));
                isUnCorrectDto = true;
            }
        }
        if (isUnCorrectDto) {
            throw new ServiceException(ErrorCode.NOT_VALID_DATA,
                    ErrorCode.NOT_VALID_DATA.getMessage(),
                    errorMessages);
        }
        if (!(atLeastOneNonNullParameter(dto))) {
            throw new ServiceException(ErrorCode.ALL_FIELD_IS_NULL,
                    ErrorCode.ALL_FIELD_IS_NULL.getMessage());
        }
    }

    /**
     * method atLeastOneNonNullParameter
     * method for check that not all dto fields is NULL
     * @param dto - dto for check fields
     * @return true if at least one field not null or throw ServiceException
     */
    private boolean atLeastOneNonNullParameter(Object dto) {
        Field[] fields = dto.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                if (field.get(dto) != null) {
                    return true;
                }
            } catch (IllegalAccessException exception) {
                throw new ServiceException(exception,
                        ErrorCode.SERVER_ERROR,
                        ErrorCode.SERVER_ERROR.getMessage());

            } finally {
                field.setAccessible(false);
            }
        }
        return false;
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
