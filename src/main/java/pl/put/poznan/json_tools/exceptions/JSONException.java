package pl.put.poznan.json_tools.exceptions;

import org.springframework.http.HttpStatus;

public class JSONException extends RuntimeException{
    private final HttpStatus status;
    public JSONException(String message, HttpStatus status){
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus(){
        return status;
    }
}
