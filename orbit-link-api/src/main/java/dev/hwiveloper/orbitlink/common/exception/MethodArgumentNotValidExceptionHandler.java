package dev.hwiveloper.orbitlink.common.exception;

import dev.hwiveloper.orbitlink.common.constants.APIConst;
import dev.hwiveloper.orbitlink.dto.common.ResDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Spring boot validation 관련 에러.
 */
@Slf4j
@RestControllerAdvice
public class MethodArgumentNotValidExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ResDTO resDTO = new ResDTO(APIConst.INVALID_PARAMS);
        resDTO.setResData(errors);

        return ResponseEntity.status(HttpStatus.OK).body(resDTO);
    }
}
