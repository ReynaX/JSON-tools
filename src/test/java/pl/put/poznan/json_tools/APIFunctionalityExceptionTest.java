package pl.put.poznan.json_tools;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import pl.put.poznan.json_tools.exceptions.JSONException;
import pl.put.poznan.json_tools.logic.decorator.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class APIFunctionalityExceptionTest{

    private static JSONToolMinify mockMinify;
    private static JSONToolPrettify mockPrettify;
    private static JSONToolExtract mockExtract;
    private static JSONToolFilter mockFilter;
    private static JSONToolCompare mockCompare;

    @BeforeEach
    public void setup() throws JsonProcessingException{
        mockMinify = mock(JSONToolMinify.class);
        mockPrettify = mock(JSONToolPrettify.class);
        mockExtract = mock(JSONToolExtract.class);
        mockFilter = mock(JSONToolFilter.class);
        mockCompare = mock(JSONToolCompare.class);

        setupFilter();
        setupExtract();
        setupCompare();
    }

    @Test
    public void testMinifyException(){
        JSONException exception = assertThrows(
                JSONException.class,
                () -> mockMinify.generateOutput(JSONToolDecorator.getJsonNode("{\"json\" : {\"option\": \"option\", \"option2\": 123}haha}")),
                "aha"
        );
        assertTrue(exception.getMessage().contentEquals("Payload is not a valid JSON!"));

        exception = assertThrows(
                JSONException.class,
                () -> mockMinify.generateOutput(JSONToolDecorator.getJsonNode("{\"json\" : {\"option\": \"option\", option2:}}")),
                "aha"
        );
        assertTrue(exception.getMessage().contentEquals("Payload is not a valid JSON!"));
    }

    @Test
    public void testPrettifyException(){
        JSONException exception = assertThrows(
                JSONException.class,
                () -> mockPrettify.generateOutput(JSONToolDecorator.getJsonNode("{\"json\" : {\"option\": \"option\", \"option2\": 123}haha}")),
                "aha"
        );
        assertTrue(exception.getMessage().contentEquals("Payload is not a valid JSON!"));

        exception = assertThrows(
                JSONException.class,
                () -> mockPrettify.generateOutput(JSONToolDecorator.getJsonNode("{\"json\" : {\"option\": \"option\", \"option2\": aha}}")),
                "aha"
        );
        assertTrue(exception.getMessage().contentEquals("Payload is not a valid JSON!"));
    }

    @Test
    public void testFilterPropertyNumberException(){
        // No "keys" property
        JSONException exception = assertThrows(
                JSONException.class,
                () -> mockFilter.generateOutput(JSONToolDecorator.getJsonNode("{\"json\" : {\"option\": \"option\", \"option2\": 123}}")),
                "aha"
        );

        assertTrue(exception.getMessage().contentEquals("Missing JSON property: \"keys\"!"));
        // No "json" property
        exception = assertThrows(
                JSONException.class,
                () -> mockFilter.generateOutput(JSONToolDecorator.getJsonNode("{\"keys\" : [ \"option\"]}")),
                "aha"
        );

        assertTrue(exception.getMessage().contentEquals("Missing JSON property: \"json\"!"));
    }

    @Test
    public void testExtractPropertyNumberException(){
        // No "keys" property
        JSONException exception = assertThrows(
                JSONException.class,
                () -> mockExtract.generateOutput(JSONToolDecorator.getJsonNode("{\"json\" : {\"option\": \"option\", \"option2\": 123}}")),
                "aha"
        );

        assertTrue(exception.getMessage().contentEquals("Missing JSON property: \"keys\"!"));
        // No "json" property
        exception = assertThrows(
                JSONException.class,
                () -> mockExtract.generateOutput(JSONToolDecorator.getJsonNode("{\"keys\" : [ \"option\"]}")),
                "aha"
        );

        assertTrue(exception.getMessage().contentEquals("Missing JSON property: \"json\"!"));
    }

    @Test
    public void testCompareException(){
        JSONException exception = assertThrows(
                JSONException.class,
                () -> mockCompare.generateOutput(JSONToolDecorator.getJsonNode("{\"json\" : {\"option\": \"option\", \"option2\": 123}, \"json2\": {\"option\": \"option_changed\"}}")),
                "aha"
        );
        assertTrue(exception.getMessage().contentEquals("Missing JSON property: \"json1\"!"));
    }


    private void setupFilter() throws JsonProcessingException{
        when(mockFilter.generateOutput(JSONToolDecorator.getJsonNode("{\"json\" : {\"option\": \"option\", \"option2\": 123}}"))).thenThrow(
                new JSONException("Missing JSON property: \"keys\"!", HttpStatus.BAD_REQUEST));

        when(mockFilter.generateOutput(JSONToolDecorator.getJsonNode("{\"keys\" : [ \"option\"]}"))).thenThrow(
                new JSONException("Missing JSON property: \"json\"!", HttpStatus.BAD_REQUEST));
    }

    private void setupExtract() throws JsonProcessingException{
        when(mockExtract.generateOutput(JSONToolDecorator.getJsonNode("{\"json\" : {\"option\": \"option\", \"option2\": 123}}"))).thenThrow(
                new JSONException("Missing JSON property: \"keys\"!", HttpStatus.BAD_REQUEST));

        when(mockExtract.generateOutput(JSONToolDecorator.getJsonNode("{\"keys\" : [ \"option\"]}"))).thenThrow(
                new JSONException("Missing JSON property: \"json\"!", HttpStatus.BAD_REQUEST));
    }

    private void setupCompare() throws JsonProcessingException{
        when(mockCompare.generateOutput(JSONToolDecorator.getJsonNode("{\"json\" : {\"option\": \"option\", \"option2\": 123}, \"json2\": {\"option\": \"option_changed\"}}"))).
                thenThrow(new JSONException("Missing JSON property: \"json1\"!", HttpStatus.BAD_REQUEST));
    }
}
