package pl.put.poznan.json_tools;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import pl.put.poznan.json_tools.exceptions.JSONException;
import pl.put.poznan.json_tools.logic.decorator.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class APIFunctionalityExceptionTest{

    @Mock
    ObjectMapper mapper;
    @Mock
    JsonNode mockNode;

    @BeforeEach
    private void setup() throws JsonProcessingException{
        mapper = mock(ObjectMapper.class);
        mockNode = mock(JsonNode.class);
        when(mapper.readTree(Mockito.anyString())).thenReturn(mockNode);
    }

    @Test
    public void testMinifyException(){
        IJSONTool tool = new JSONToolMinify(new JSONTool());
        when(mockNode.toPrettyString()).thenThrow(new JSONException("Payload is not a valid JSON!", HttpStatus.BAD_REQUEST));
        JSONException exception = assertThrows(
                JSONException.class,
                () -> tool.generateOutput(mockNode),
                "aha"
        );
        assertTrue(exception.getMessage().contentEquals("Payload is not a valid JSON!"));

        exception = assertThrows(
                JSONException.class,
                () -> tool.generateOutput(mockNode),
                "aha"
        );
        assertTrue(exception.getMessage().contentEquals("Payload is not a valid JSON!"));
    }

    @Test
    public void testPrettifyException(){
        IJSONTool tool = new JSONToolPrettify(new JSONTool());
        when(mockNode.toPrettyString()).thenThrow(new JSONException("Payload is not a valid JSON!", HttpStatus.BAD_REQUEST));
        JSONException exception = assertThrows(
                JSONException.class,
                () -> tool.generateOutput(mockNode),
                "aha"
        );
        assertTrue(exception.getMessage().contentEquals("Payload is not a valid JSON!"));

        exception = assertThrows(
                JSONException.class,
                () -> tool.generateOutput(mockNode),
                "aha"
        );
        assertTrue(exception.getMessage().contentEquals("Payload is not a valid JSON!"));
    }

    @Test
    public void testFilterPropertyNumberException(){
        IJSONTool tool = new JSONToolFilter(new JSONTool());
        // No "keys" property
        when(mockNode.toPrettyString()).thenReturn("{\"json\" : {\"option\": \"option\", \"option2\": 123}}");
        JSONException exception = assertThrows(
                JSONException.class,
                () -> tool.generateOutput(mockNode),
                "aha"
        );

        assertTrue(exception.getMessage().contentEquals("Missing JSON property: \"keys\"!"));
        // No "json" property
        when(mockNode.toPrettyString()).thenReturn("{\"keys\" : [ \"option\"]}");
        exception = assertThrows(
                JSONException.class,
                () -> tool.generateOutput(mockNode),
                "aha"
        );

        assertTrue(exception.getMessage().contentEquals("Missing JSON property: \"json\"!"));
    }

    @Test
    public void testExtractPropertyNumberException(){
        IJSONTool tool = new JSONToolExtract(new JSONTool());
        // No "keys" property
        when(mockNode.toPrettyString()).thenReturn("{\"json\" : {\"option\": \"option\", \"option2\": 123}}");
        JSONException exception = assertThrows(
                JSONException.class,
                () -> tool.generateOutput(mockNode),
                "aha"
        );

        assertTrue(exception.getMessage().contentEquals("Missing JSON property: \"keys\"!"));
        // No "json" property
        when(mockNode.toPrettyString()).thenReturn("{\"keys\" : [ \"option\"]}");
        exception = assertThrows(
                JSONException.class,
                () -> tool.generateOutput(mockNode),
                "aha"
        );

        assertTrue(exception.getMessage().contentEquals("Missing JSON property: \"json\"!"));
    }

    @Test
    public void testCompareException(){
        IJSONTool tool = new JSONToolCompare(new JSONTool());
        when(mockNode.toPrettyString()).thenReturn("{\"json\" : {\"option\": \"option\", \"option2\": 123}, \"json2\": {\"option\": \"option_changed\"}}");
        JSONException exception = assertThrows(
                JSONException.class,
                () -> tool.generateOutput(mockNode),
                "aha"
        );
        assertTrue(exception.getMessage().contentEquals("Missing JSON property: \"json1\"!"));
    }
}
