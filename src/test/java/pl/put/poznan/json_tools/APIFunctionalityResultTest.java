package pl.put.poznan.json_tools;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import pl.put.poznan.json_tools.logic.decorator.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class APIFunctionalityResultTest{
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
    public void testMinifyOK() throws JsonProcessingException{
        JsonNode node = mapper.readTree("aha");
        IJSONTool tool = new JSONToolMinify(new JSONTool());

        when(node.toPrettyString()).thenReturn("{\"json\":{\"option\":\"option\",\"option2\":123},\"keys\":[\"option\"]}");
        assertEquals(tool.generateOutput(node), "{\"json\":{\"option\":\"option\",\"option2\":123},\"keys\":[\"option\"]}");

        when(node.toPrettyString()).thenReturn("{ \"fruit\" : \"Apple\", \"size\" : \"Large\", \"color\" : \"Red\"}");
        assertEquals(tool.generateOutput(node), "{\"fruit\":\"Apple\",\"size\":\"Large\",\"color\":\"Red\"}");

        when(node.toPrettyString()).thenReturn("{\"json\":{\"menu\":{\"id\":\"file\",\"value\":\"File\",\"popup\":{\"menuitem\":[{\"value\":\"New\",\"onclick\":\"CreateNewDoc()\"},{\"value\":\"Open\",\"onclick\":\"OpenDoc()\"},{\"value\":\"Close\",\"onclick\":\"CloseDoc()\"}]}}}}");
        assertEquals(tool.generateOutput(node), "{\"json\":{\"menu\":{\"id\":\"file\",\"value\":\"File\",\"popup\":{\"menuitem\":[{\"value\":\"New\",\"onclick\":\"CreateNewDoc()\"},{\"value\":\"Open\",\"onclick\":\"OpenDoc()\"},{\"value\":\"Close\",\"onclick\":\"CloseDoc()\"}]}}}}");
        verify(node, times(3)).toPrettyString();
    }

    @Test
    public void testPrettifyOK() throws JsonProcessingException{
        JsonNode node = mapper.readTree("aha");
        IJSONTool tool = new JSONToolPrettify(new JSONTool());

        when(node.toPrettyString()).thenReturn("{\"json\":{\"option\":\"option\",\"option2\":123},\"keys\":[\"option\"]}");
        String expected = "{\n" +
                          "  \"json\" : {\n" +
                          "    \"option\" : \"option\",\n" +
                          "    \"option2\" : 123\n" +
                          "  },\n" +
                          "  \"keys\" : [ \"option\" ]\n" +
                          "}";
        String result = tool.generateOutput(node);
        assertTrue(result.replaceAll("\r\n", "\n").equals(expected) || result.equals(expected));

        when(node.toPrettyString()).thenReturn("{ \"fruit\" : \"Apple\", \"size\" : \"Large\", \"color\" : \"Red\"}");
        expected = "{\n" +
                   "  \"fruit\" : \"Apple\",\n" +
                   "  \"size\" : \"Large\",\n" +
                   "  \"color\" : \"Red\"\n" +
                   "}";
        result = tool.generateOutput(node);
        assertTrue(result.replaceAll("\r\n", "\n").equals(expected) || result.equals(expected));

        verify(node, times(2)).toPrettyString();
    }

    @Test
    public void testExtractOK() throws JsonProcessingException{
        JsonNode node = mapper.readTree("aha");
        IJSONTool tool = new JSONToolExtract(new JSONTool());

        when(node.toPrettyString()).thenReturn("{\"json\":{\"option\":\"option\",\"option2\":123},\"keys\":[\"option\"]}");
        String result = tool.generateOutput(node);
        String expected = "{\n" +
                          "  \"option2\" : 123\n" +
                          "}";
        assertTrue(result.replaceAll("\r\n", "\n").equals(expected) || result.equals(expected));


        when(node.toPrettyString()).thenReturn("{ \"json\": { \"fruit\" : \"Apple\", \"size\" : \"Large\", \"color\" : \"Red\"}, \"keys\" : [\"fruit\", \"color\"]}");
        result = tool.generateOutput(node);
        expected = "{\n" +
                   "  \"size\" : \"Large\"\n" +
                   "}";
        assertTrue(result.replaceAll("\r\n", "\n").equals(expected) || result.equals(expected));

        when(node.toPrettyString()).thenReturn("{\"json\":{\"menu\":{\"id\":\"file\",\"value\":\"File\",\"popup\":{\"menuitem\":[{\"value\":\"New\",\"onclick\":\"CreateNewDoc()\"},{\"value\":\"Open\",\"onclick\":\"OpenDoc()\"},{\"value\":\"Close\",\"onclick\":\"CloseDoc()\"}]}}}, \"keys\" : [\"value\", \"onclick\"]}\"");
        result = tool.generateOutput(node);
        expected = "{\n" +
                   "  \"menu\" : {\n" +
                   "    \"id\" : \"file\",\n" +
                   "    \"popup\" : {\n" +
                   "      \"menuitem\" : { }\n" +
                   "    }\n" +
                   "  }\n" +
                   "}";

        assertTrue(result.replaceAll("\r\n", "\n").equals(expected) || result.equals(expected));
        verify(node, times(3)).toPrettyString();
    }

    @Test
    public void testFilterOK() throws JsonProcessingException{
        JsonNode node = mapper.readTree("aha");
        IJSONTool tool = new JSONToolFilter(new JSONTool());

        when(node.toPrettyString()).thenReturn("{\"json\":{\"option\":\"option\",\"option2\":123},\"keys\":[\"option\"]}");
        String result = tool.generateOutput(node);
        String expected = "{\n" +
                          "  \"option\" : \"option\"\n" +
                          "}";
        assertTrue(result.replaceAll("\r\n", "\n").equals(expected) || result.equals(expected));


        when(node.toPrettyString()).thenReturn("{ \"json\": { \"fruit\" : \"Apple\", \"size\" : \"Large\", \"color\" : \"Red\"}, \"keys\" : [\"fruit\", \"color\"]}");
        result = tool.generateOutput(node);
        expected = "{\n" +
                   "  \"fruit\" : \"Apple\",\n" +
                   "  \"color\" : \"Red\"\n" +
                   "}";
        assertTrue(result.replaceAll("\r\n", "\n").equals(expected) || result.equals(expected));

        when(node.toPrettyString()).thenReturn("{\"json\":{\"menu\":{\"id\":\"file\",\"value\":\"File\",\"popup\":{\"menuitem\":[{\"value\":\"New\",\"onclick\":\"CreateNewDoc()\"},{\"value\":\"Open\",\"onclick\":\"OpenDoc()\"},{\"value\":\"Close\",\"onclick\":\"CloseDoc()\"}]}}}, \"keys\" : [\"value\", \"onclick\", \"json\", \"menu\"]}");
        result = tool.generateOutput(node);
        expected = "{\n" +
                   "  \"menu\" : {\n" +
                   "    \"value\" : \"File\"\n" +
                   "  }\n" +
                   "}";

        assertTrue(result.replaceAll("\r\n", "\n").equals(expected) || result.equals(expected));
        verify(node, times(3)).toPrettyString();
    }
}
