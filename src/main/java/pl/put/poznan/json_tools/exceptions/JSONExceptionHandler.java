package pl.put.poznan.json_tools.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ControllerAdvice
public class JSONExceptionHandler{
    private static final Logger logger = LoggerFactory.getLogger(JSONExceptionHandler.class);
    @ExceptionHandler(value = {JSONException.class})
    public ResponseEntity<String> handle(JSONException e){
        logger.warn("Exception occurred: " + e.getMessage());
        return new ResponseEntity<>(e.getMessage(), e.getStatus());
    }
}
