package pl.put.poznan.json_tools.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller responsible for catching and managing all <code>JSONException</code>
 *
 * @author Przemysław Marcinkowski (ReynaX)
 * @version 1.0, 21/01/2023
 */
@ControllerAdvice
public class JSONExceptionHandler{
    private static final Logger logger = LoggerFactory.getLogger(JSONExceptionHandler.class);

    /**
     *  Converts exception to <code>ResponseEntity</code> for correct connection with API
     * @param e         exception that has occured
     * @return          given exception as <code>e</code> converted to <code>ResponseEntity</code>
     */
    @ExceptionHandler(value = {JSONException.class})
    public ResponseEntity<String> handle(JSONException e){
        logger.warn("Exception occurred: " + e.getMessage());
        return new ResponseEntity<>(e.getMessage(), e.getStatus());
    }
}
