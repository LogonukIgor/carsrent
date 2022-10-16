package by.logonuk.controller.exceptionhandle;

import by.logonuk.exception.NoSuchEntityException;
import by.logonuk.util.UUIDGenerator;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;

@ControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler({NoSuchEntityException.class, EmptyResultDataAccessException.class})
    public ResponseEntity<Object> handleEntityNotFountException(Exception e) {

        ErrorContainer error = ErrorContainer
                .builder()
                .exceptionId(UUIDGenerator.generateUUID())
                .errorCode(2)
                .errorMessage(e.getMessage())
                .errorClass(e.getClass().toString())
                .message("DAO")
                .build();

        return new ResponseEntity<>(Collections.singletonMap("error", error), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(Exception e) {

        ErrorContainer error = ErrorContainer
                .builder()
                .exceptionId(UUIDGenerator.generateUUID())
                .errorCode(3)
                .errorMessage(e.getMessage())
                .errorClass(e.getClass().toString())
                .message("Enum!!!!")
                .build();

        return new ResponseEntity<>(Collections.singletonMap("error", error), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e) {

        ErrorContainer error = ErrorContainer
                .builder()
                .exceptionId(UUIDGenerator.generateUUID())
                .errorCode(1)
                .errorMessage("General error")
                .errorClass(e.getClass().toString())
                .message("")
                .build();

        return new ResponseEntity<>(Collections.singletonMap("error", error), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
