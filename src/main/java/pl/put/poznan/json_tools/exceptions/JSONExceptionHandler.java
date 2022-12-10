package pl.put.poznan.json_tools.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class JSONExceptionHandler{

    @ExceptionHandler(value = {JSONException.class})
    public ResponseEntity<String> handle(JSONException e){
        return new ResponseEntity<>(e.getMessage(), e.getStatus());
    }
}
