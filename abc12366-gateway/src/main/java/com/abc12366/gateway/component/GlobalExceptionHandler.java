package com.abc12366.gateway.component;

import com.abc12366.common.model.BodyStatus;
import com.abc12366.common.util.Utils;
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
        BodyStatus bodyStatus;
        if (e instanceof HttpRequestMethodNotSupportedException) {
            bodyStatus = Utils.bodyStatus(4005);
            LOGGER.error(bodyStatus.getMessage() + e);
            return new ResponseEntity<>(bodyStatus, HttpStatus.METHOD_NOT_ALLOWED);
        } else if (e instanceof HttpMessageNotReadableException) {
            bodyStatus = Utils.bodyStatus(4004);
            LOGGER.error(bodyStatus.getMessage() + e);
            return new ResponseEntity<>(bodyStatus, HttpStatus.BAD_REQUEST);
        } else if (e instanceof MethodArgumentNotValidException) {
            bodyStatus = Utils.bodyStatus(4006);
            LOGGER.error(bodyStatus.getMessage() + e);
            return new ResponseEntity<>(bodyStatus, HttpStatus.BAD_REQUEST);
        } else {
            bodyStatus = Utils.bodyStatus(5000);
            LOGGER.error(bodyStatus.getMessage() + e);
            return new ResponseEntity<>(bodyStatus, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
