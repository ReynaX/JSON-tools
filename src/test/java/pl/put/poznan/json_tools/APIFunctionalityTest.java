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
    public void testMinify(String json, int expected) throws Exception{
        final String baseUrl = "http://localhost:"+ serverPort + "/json-tools/minify";
        int result = getPostResult(baseUrl, json, mapper, restTemplate);
        assert result == expected;
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/prettify_json_data.csv")
    public void testPrettify(String json, int expected) throws Exception{
        final String baseUrl = "http://localhost:"+ serverPort + "/json-tools/prettify";
        int result = getPostResult(baseUrl, json, mapper, restTemplate);
        assert result == expected;
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/filter_json_data.csv")
    public void testFilter(String json, String expected) throws Exception{
        final String baseUrl = "http://localhost:"+ serverPort + "/json-tools/filter";
        String result = getFiltersResult(baseUrl, json, mapper, restTemplate);
        assert result.equals(expected);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/extract_json_data.csv")
    public void testExtract(String json, String expected) throws Exception{
        final String baseUrl = "http://localhost:"+ serverPort + "/json-tools/extract";
        String result = getFiltersResult(baseUrl, json, mapper, restTemplate);
        assert result.equals(expected);
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
