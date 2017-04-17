package com.abc12366.core.component;

import com.abc12366.core.model.bo.ResultBodyBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

/**
 * 全局Exception处理
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-03-29 2:59 PM
 * @since 1.0.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity handle(Exception e, HttpServletResponse response) {
        e.printStackTrace();
        LOGGER.error(e.getMessage() + e);

        if (e instanceof HttpRequestMethodNotSupportedException) {
            return new ResponseEntity(HttpStatus.METHOD_NOT_ALLOWED);
        } else if (e instanceof HttpMessageNotReadableException) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } else if (e instanceof MethodArgumentNotValidException) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } else {
            ResultBodyBO dto = new ResultBodyBO.Builder()
                    .message(e.getMessage())
                    .localizedMessage(e.getLocalizedMessage())
                    .clazz(e.getClass())
                    .build();
            return ResponseEntity.ok(dto);
        }
    }
}
