package pl.put.poznan.json_tools;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.boot.test.web.client.TestRestTemplate;
import pl.put.poznan.json_tools.exceptions.JSONException;
import pl.put.poznan.json_tools.logic.decorator.*;

import java.nio.charset.StandardCharsets;


@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class APIFunctionalityTest{
    @Autowired
    private TestRestTemplate restTemplate;
    @LocalServerPort
    private final int serverPort = 8080;

    private ObjectMapper mapper;

    @BeforeEach
    public void setUp(){
        mapper = new ObjectMapper();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/minify_json_data.csv")
    public void testMinify(String json, String expected) throws Exception{
        try{
            JSONToolDecorator minify = new JSONToolMinify(new JSONTool());
            String result = minify.generateOutput(JSONToolDecorator.getJsonNode(json));
            assert result.equals(expected);
        }catch(JSONException ex){
            assert expected.equals("exception");
        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/prettify_json_data.csv")
    public void testPrettify(String json, String expected) throws Exception{
        try{
            JSONToolDecorator prettify = new JSONToolPrettify(new JSONTool());
            String result = prettify.generateOutput(JSONToolDecorator.getJsonNode(json));
            assert result.equals(expected.replaceAll("\n", "\r\n"));
        }catch(JSONException ex){
            assert expected.equals("exception");
        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/filter_json_data.csv")
    public void testFilter(String json, String expected) throws Exception{
        try{
            JSONToolDecorator filter = new JSONToolFilter(new JSONTool());
            String result = filter.generateOutput(JSONToolDecorator.getJsonNode(json));
            String expect = JSONToolDecorator.getJsonNode(expected).toPrettyString();
            assert result.equals(expect);
        }catch(JSONException ex){
            assert ex.getMessage().equals(expected);
        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/extract_json_data.csv")
    public void testExtract(String json, String expected) throws Exception{
        try{
            JSONToolDecorator extract = new JSONToolExtract(new JSONTool());
            String result = extract.generateOutput(JSONToolDecorator.getJsonNode(json));
            String expect = JSONToolDecorator.getJsonNode(expected).toPrettyString();
            assert result.equals(expect);
        }catch(JSONException ex){
            assert ex.getMessage().equals(expected);
        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/compare_json_data.csv")
    public void testCompare(String json, String expected) throws  Exception{
        try{
            JSONToolDecorator compare = new JSONToolCompare(new JSONTool());
            String result = compare.generateOutput(JSONToolDecorator.getJsonNode(json));
            String expect = JSONToolDecorator.getJsonNode(expected).toPrettyString();
            assert result.equals(expect);
        }catch(JSONException ex){
            assert ex.getMessage().equals(expected);
        }
    }

    static private int getPostResult(String baseUrl, String payload, ObjectMapper mapper, TestRestTemplate restTemplate) throws Exception{
        JsonNode node = mapper.readTree(payload);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(node.toPrettyString(), headers);
        return restTemplate.exchange(baseUrl, HttpMethod.POST, request, byte[].class).getStatusCodeValue();
    }

    static private String getFiltersResult(String baseUrl, String payload, ObjectMapper mapper, TestRestTemplate restTemplate) throws Exception{
        JsonNode node = mapper.readTree(payload);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(node.toPrettyString(), headers);
        ResponseEntity<byte[]> result = restTemplate.exchange(baseUrl, HttpMethod.POST, request, byte[].class);
        if(result.getStatusCodeValue() != 200)
            return new String(result.getBody(), StandardCharsets.UTF_8);
        else{
            String res = new String(result.getBody(), StandardCharsets.UTF_8);
            return mapper.readTree(res).toString();
        }
    }


}