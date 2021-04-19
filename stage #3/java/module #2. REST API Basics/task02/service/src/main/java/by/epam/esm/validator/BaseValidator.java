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

@Component
public class BaseValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseValidator.class);

    private Validator validator;

    @Autowired
    public BaseValidator(Validator validator) {
        this.validator = validator;
    }

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

    private ErrorMessage createNewErrorMessage(String path, String unCorrectValue, String message) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setField(path);
        errorMessage.setMessage(message);
        errorMessage.setUnCorrectValue(unCorrectValue);
        return errorMessage;
    }
}
