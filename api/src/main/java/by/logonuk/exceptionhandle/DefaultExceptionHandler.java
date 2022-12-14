package by.logonuk.exceptionhandle;

import by.logonuk.exception.CreateLicenceForUserException;
import by.logonuk.exception.CustomIllegalArgumentException;
import by.logonuk.exception.DateValidationException;
import by.logonuk.exception.NoSuchEntityException;
import by.logonuk.exception.UniqueConstraintException;
import by.logonuk.util.UUIDGenerator;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.UnexpectedTypeException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler({EmptyResultDataAccessException.class, DataIntegrityViolationException.class})
    public ResponseEntity<Object> handleEntityNotFountException(Exception e) {

        ErrorContainer error = ErrorContainer
                .builder()
                .exceptionId(UUIDGenerator.generateUUID())
                .errorCode(2)
                .errorMessage(e.getMessage())
                .errorClass(e.getClass().toString())
                .build();

        return new ResponseEntity<>(Collections.singletonMap("error", error), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoSuchEntityException.class)
    public ResponseEntity<Object> handleNoSuchEntityExceptionException(NoSuchEntityException e) {

        ErrorContainer error = ErrorContainer
                .builder()
                .exceptionId(UUIDGenerator.generateUUID())
                .errorCode(5)
                .errorMessage(e.toString())
                .errorClass(e.getClass().toString())
                .build();

        return new ResponseEntity<>(Collections.singletonMap("error", error), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UniqueConstraintException.class)
    public ResponseEntity<Object> handleUniqueConstraintException(UniqueConstraintException e) {

        ErrorContainer error = ErrorContainer
                .builder()
                .exceptionId(UUIDGenerator.generateUUID())
                .errorCode(11)
                .errorMessage(e.toString())
                .errorClass(e.getClass().toString())
                .build();

        return new ResponseEntity<>(Collections.singletonMap("error", error), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(Exception e) {

        ErrorContainer error = ErrorContainer
                .builder()
                .exceptionId(UUIDGenerator.generateUUID())
                .errorCode(3)
                .errorMessage(e.getMessage())
                .errorClass(e.getClass().toString())
                .build();

        return new ResponseEntity<>(Collections.singletonMap("error", error), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Object> handleNullPointerException(Exception e) {

        ErrorContainer error = ErrorContainer
                .builder()
                .exceptionId(UUIDGenerator.generateUUID())
                .errorCode(4)
                .errorMessage(e.getMessage())
                .errorClass(e.getClass().toString())
                .build();

        return new ResponseEntity<>(Collections.singletonMap("error", error), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e) {

        ErrorContainer error = ErrorContainer
                .builder()
                .exceptionId(UUIDGenerator.generateUUID())
                .errorCode(1)
                .errorMessage("General error: " + e.getMessage())
                .errorClass(e.getClass().toString())
                .build();

        return new ResponseEntity<>(Collections.singletonMap("error", error), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UnexpectedTypeException.class)
    public ResponseEntity<Object> handleException(UnexpectedTypeException e) {

        ErrorContainer error = ErrorContainer
                .builder()
                .exceptionId(UUIDGenerator.generateUUID())
                .errorCode(6)
                .errorMessage(e.getMessage())
                .errorClass(e.getClass().toString())
                .build();

        return new ResponseEntity<>(Collections.singletonMap("error", error), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleException(MethodArgumentNotValidException e) {

        List<FieldError> errors = e.getBindingResult().getFieldErrors();

        Map<String, String> allValidExceptions = new HashMap<>();

        for (FieldError a : errors) {
            allValidExceptions.put(a.getField(), a.getDefaultMessage());
        }

        ErrorValidationContainer error = ErrorValidationContainer
                .builder()
                .exceptionId(UUIDGenerator.generateUUID())
                .errorCode(7)
                .errorMessage("Count of validation exception: " + e.getBindingResult().getErrorCount())
                .allValidMessages(allValidExceptions)
                .errorClass(e.getClass().toString())
                .build();

        return new ResponseEntity<>(Collections.singletonMap("error", error), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomIllegalArgumentException.class)
    public ResponseEntity<Object> handleCustomIllegalArgumentException(CustomIllegalArgumentException e) {

        ErrorContainer error = ErrorContainer
                .builder()
                .exceptionId(UUIDGenerator.generateUUID())
                .errorCode(8)
                .errorMessage(e.toString())
                .errorClass(e.getClass().toString())
                .build();

        return new ResponseEntity<>(Collections.singletonMap("error", error), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DateValidationException.class)
    public ResponseEntity<Object> handleCustomIllegalArgumentException(DateValidationException e) {

        ErrorContainer error = ErrorContainer
                .builder()
                .exceptionId(UUIDGenerator.generateUUID())
                .errorCode(9)
                .errorMessage(e.toString())
                .errorClass(e.getClass().toString())
                .build();

        return new ResponseEntity<>(Collections.singletonMap("error", error), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CreateLicenceForUserException.class)
    public ResponseEntity<Object> handleCreateLicenceForUserException(CreateLicenceForUserException e) {

        ErrorContainer error = ErrorContainer
                .builder()
                .exceptionId(UUIDGenerator.generateUUID())
                .errorCode(10)
                .errorMessage(e.toString())
                .errorClass(e.getClass().toString())
                .build();

        return new ResponseEntity<>(Collections.singletonMap("error", error), HttpStatus.NOT_FOUND);
    }
}
