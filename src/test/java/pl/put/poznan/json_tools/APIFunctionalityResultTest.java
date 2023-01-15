package pl.put.poznan.json_tools;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.put.poznan.json_tools.logic.decorator.JSONToolDecorator;
import pl.put.poznan.json_tools.logic.decorator.*;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class APIFunctionalityResultTest{
    private JSONToolMinify mockMinify;
    private JSONToolPrettify mockPrettify;
    private JSONToolExtract mockExtract;
    private JSONToolFilter mockFilter;
    private JSONToolCompare mockCompare;

    @BeforeEach
    private void setup() throws JsonProcessingException{
        setupOKMinify();
        setupOKPrettify();
        setupOKExtract();
        setupOKFilter();
    }

    @Test
    public void testMinifyOK() throws JsonProcessingException{
        JsonNode node = JSONToolDecorator.getJsonNode("\"{\"json\" : {\"option\": \"option\", \"option2\": 123}}\"");
        assertEquals(mockMinify.generateOutput(node), "\"{\"json\":{\"option\":\"option\",\"option2\":123}}\"");

        node = JSONToolDecorator.getJsonNode("{\n" +
                                             "    \"fruit\": \t\"Apple\",\"size\": \"Large\",\t\"color\": \"Red\"\n" +
                                             "}");
        when(mockMinify.generateOutput(node)).thenReturn("{\"fruit\":\"Apple\",\"size\":\"Large\",\"color\":\"Red\"}");
    }

    @Test
    public void testPrettifyOK() throws JsonProcessingException{
        JsonNode node = JSONToolDecorator.getJsonNode("\"{\"json\" : {\"option\": \"option\", \"option2\": 123}}\"");
        when(mockPrettify.generateOutput(node)).thenReturn("{\n  \"json\" : {\n    \"option\" :" +
                                                           " \"option\",\n    \"option2\" : 123\n  }\n}");

        node = JSONToolDecorator.getJsonNode("{\n    \"fruit\": \t\"Apple\",\"size\": \"Large\",\t\"color\": \"Red\"\n}");
        when(mockPrettify.generateOutput(node)).thenReturn("{\n  \"fruit\" : \"Apple\",\n  \"size\" :" +
                                                           " \"Large\",\n  \"color\" : \"Red\"\n}");
    }

    @Test
    public void testExtractOK() throws JsonProcessingException{
        JsonNode node = JSONToolDecorator.getJsonNode("{\"json\" : {\"option\": \"option\", \"option2\": 123}, \"keys\": [\"option\"]}");
        when(mockExtract.generateOutput(node)).thenReturn("{\n  \"option2\" : 123\n}");
    }

    @Test
    public void testFilterOK() throws JsonProcessingException{
        JsonNode node = JSONToolDecorator.getJsonNode("{\"json\" : {\"option\": \"option\", \"option2\": 123}, \"keys\": [\"option\"]}");
        when(mockFilter.generateOutput(node)).thenReturn("{\n  \"option\" : \"option\"\n}");
    }

    private void setupOKMinify() throws JsonProcessingException{
        // Minify ok
        mockMinify = mock(JSONToolMinify.class);
        JsonNode node = JSONToolDecorator.getJsonNode("\"{\"json\" : {\"option\": \"option\", \"option2\": 123}}\"");
        when(mockMinify.generateOutput(node)).
                thenReturn("\"{\"json\":{\"option\":\"option\",\"option2\":123}}\"");

        node = JSONToolDecorator.getJsonNode("{\n" +
                                             "    \"fruit\": \t\"Apple\",\"size\": \"Large\",\t\"color\": \"Red\"\n" +
                                             "}");
        when(mockMinify.generateOutput(node)).thenReturn("{\"fruit\":\"Apple\",\"size\":\"Large\",\"color\":\"Red\"}");
    }

    private void setupOKPrettify() throws JsonProcessingException{
        mockPrettify = mock(JSONToolPrettify.class);
        JsonNode node = JSONToolDecorator.getJsonNode("\"{\"json\" : {\"option\": \"option\", \"option2\": 123}}\"");
        when(mockPrettify.generateOutput(node)).thenReturn("{\n  \"json\" : {\n    \"option\" :" +
                                                           " \"option\",\n    \"option2\" : 123\n  }\n}");

        node = JSONToolDecorator.getJsonNode("{\n    \"fruit\": \t\"Apple\",\"size\": \"Large\",\t\"color\": \"Red\"\n}");
        when(mockPrettify.generateOutput(node)).thenReturn("{\n  \"fruit\" : \"Apple\",\n  \"size\" :" +
                                                           " \"Large\",\n  \"color\" : \"Red\"\n}");
    }

    private void setupOKExtract() throws JsonProcessingException{
        mockExtract = mock(JSONToolExtract.class);
        JsonNode node = JSONToolDecorator.getJsonNode("{\"json\" : {\"option\": \"option\", \"option2\": 123}, \"keys\": [\"option\"]}");
        when(mockExtract.generateOutput(node)).thenReturn("{\n  \"option2\" : 123\n}");
    }

    private void setupOKFilter() throws JsonProcessingException{
        mockFilter = mock(JSONToolFilter.class);
        JsonNode node = JSONToolDecorator.getJsonNode("{\"json\" : {\"option\": \"option\", \"option2\": 123}, \"keys\": [\"option\"]}");
        when(mockFilter.generateOutput(node)).thenReturn("{\n  \"option\" : \"option\"\n}");
    }
}
