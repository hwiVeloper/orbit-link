package dev.hwiveloper.orbitlink.common.exception;

import dev.hwiveloper.orbitlink.common.constants.APIConst;
import dev.hwiveloper.orbitlink.dto.common.ResDTO;
import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<?> commonException(Exception e) {
        ResDTO resDTO = new ResDTO(APIConst.SYSTEM_ERROR);

        log.error("시스템 오류 : ", e);

//        if (e.getMessage() != null) {
//            resDTO.setResMsg(e.getMessage());
//        }

        return ResponseEntity.ok(resDTO);
    }

    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<?> handleUserNotFoundException(UserNotFoundException e) {
        ResDTO resDTO = new ResDTO(APIConst.USER_NOT_FOUND);

        return ResponseEntity.ok(resDTO);
    }

    @ExceptionHandler(ApplicationException.class)
    protected ResponseEntity<?> handleApplicationException(ApplicationException e) {
        ResDTO resDTO = new ResDTO(e.getCode(), e.getMessage());

        log.error("ApplicationException: ", e);

        return ResponseEntity.ok(resDTO);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e) {
        ResDTO resDTO = new ResDTO(APIConst.INVALID_PARAMS);

        log.error("IllegalArgumentException: ", e);

        return ResponseEntity.ok(resDTO);
    }

    @ExceptionHandler(MalformedJwtException.class)
    protected ResponseEntity<?> handleMalformedJwtException(MalformedJwtException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
