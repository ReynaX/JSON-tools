package pl.put.poznan.json_tools;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import pl.put.poznan.json_tools.exceptions.JSONException;
import pl.put.poznan.json_tools.logic.decorator.JSONToolDecorator;
import pl.put.poznan.json_tools.logic.decorator.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class APIFunctionalityMockTest{

    private JSONTool mockTool;
    private JSONToolMinify mockMinify;
    private JSONToolPrettify mockPrettify;
    private JSONToolExtract mockExtract;
    private JSONToolFilter mockFilter;
    private JSONToolCompare mockCompare;

    @Test
    public void testMinifyOK() throws JsonProcessingException{
        JsonNode node = JSONToolDecorator.getJsonNode("\"{\"json\" : {\"option\": \"option\", \"option2\": 123}}\"");
        assertEquals(mockMinify.generateOutput(node), "\"{\"json\":{\"option\":\"option\",\"option2\":123}}\"");
    }

    @Test
    public void testMinifyException(){
        JSONException exception = assertThrows(
                JSONException.class,
                () -> mockMinify.generateOutput(JSONToolDecorator.getJsonNode("{\"json\" : {\"option\": \"option\", \"option2\": 123}haha}")),
                "aha"
        );
        assertTrue(exception.getMessage().contentEquals("Payload is not a valid JSON!"));
    }

    @BeforeEach
    private void setupOKTest() throws JsonProcessingException{
        // Minify ok
        mockMinify = mock(JSONToolMinify.class);
        JsonNode node = JSONToolDecorator.getJsonNode("\"{\"json\" : {\"option\": \"option\", \"option2\": 123}}\"");
        when(mockMinify.generateOutput(node)).
                thenReturn("\"{\"json\":{\"option\":\"option\",\"option2\":123}}\"");

        node = JSONToolDecorator.getJsonNode("{\n" +
                                             "    \"fruit\": \t\"Apple\",\"size\": \"Large\",\t\"color\": \"Red\"\n" +
                                             "}");
        when(mockMinify.generateOutput(node)).thenReturn("{\"fruit\":\"Apple\",\"size\":\"Large\",\"color\":\"Red\"}");

        // Minify exception
        mockPrettify = mock(JSONToolPrettify.class);
        node = JSONToolDecorator.getJsonNode("\"{\"json\" : {\"option\": \"option\", \"option2\": 123}}\"");
        when(mockPrettify.generateOutput(node)).thenReturn("{\n" +
                                                           "  \"json\" : {\n" +
                                                           "    \"option\" : \"option\",\n" +
                                                           "    \"option2\" : 123\n" +
                                                           "  }\n" +
                                                           "}");

        node = JSONToolDecorator.getJsonNode("{\n" +
                                             "    \"fruit\": \t\"Apple\",\"size\": \"Large\",\t\"color\": \"Red\"\n" +
                                             "}");
        when(mockPrettify.generateOutput(node)).thenReturn("{\n" +
                                                         "  \"fruit\" : \"Apple\",\n" +
                                                         "  \"size\" : \"Large\",\n" +
                                                         "  \"color\" : \"Red\"\n" +
                                                         "}");
        // TODO: Add other class tests
        // TODO: Split setup functions into small private functions to avoid readability problems
        /*
            example:
            setupOK(){
                setupMinifyOK();
                setupPrettifyOK();
                ...
             }
            setupException(){
                ...
            }

         */
    }

//    @BeforeEach
//    private void setupException()
}
