package pl.put.poznan.json_tools.rest;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.json_tools.exceptions.JSONException;
import pl.put.poznan.json_tools.logic.JSONToolsService;

/**
 * Controller responsible for mapping request data to the defined request handler method.
 *
 * @author Przemysław Marcinkowski (ReynaX)
 * @version 0.5, 12/10/2022
 */

@RestController
@RequestMapping(path = "/json-tools")
public class JSONToolsController{
    private static final Logger logger = LoggerFactory.getLogger(JSONToolsController.class);
    private static JSONToolsService service;

    @Autowired
    public JSONToolsController(JSONToolsService service){
        JSONToolsController.service = service;
    }

    /**
     * Returns minified version of JSON by removing unneded characters. JSON to minify has to be valid and
     * be under the property "json" in body of HTTP request.
     *
     * @param payload           body of HTTP request
     * @return                  minified version of given JSON as HTTP response
     * @throws JSONException    if given JSON is invalid
     */
    @RequestMapping(method = RequestMethod.POST, value = "minify", produces = "application/json")
    public ResponseEntity<String> minify(@RequestBody String payload){
        JsonNode jsonNode = service.getJsonNode(payload);
        if(jsonNode.size() != 1)
            throw new JSONException("Payload should contain only one JSON property: \"json\"!", HttpStatus.BAD_REQUEST);
        JsonNode jsonData = service.getJsonProperty(jsonNode, "json");
        return new ResponseEntity<>(service.minify(jsonData), HttpStatus.OK);
    }

    /**
     * Returns pretty version of JSON by adding indentations, spaces and tabs to improve readability. JSON to prettify has to be valid and
     * be under the property "json" in body of HTTP request.
     *
     * @param payload           body of HTTP request that s
     * @return                  pretty version of given JSON as HTTP response
     * @throws JSONException    if given JSON is invalid
     */
    @RequestMapping(method = RequestMethod.POST, value = "prettify", produces = "application/json")
    public ResponseEntity<String> prettify(@RequestBody String payload){
        JsonNode jsonNode = service.getJsonNode(payload);
        if(jsonNode.size() != 1)
            throw new JSONException("Payload should contain only one JSON property: \"json\"!", HttpStatus.BAD_REQUEST);
        JsonNode jsonData = service.getJsonProperty(jsonNode, "json");
        return new ResponseEntity<>(service.prettify(jsonData), HttpStatus.OK);
    }

    /**
     * Returns difference between two JSONs in <a href ="https://jsonpatch.com">JSON Patch</a> format.
     * JSONs to compare have to be valid and be under properties "json1" and "json2" in body of HTTP request.
     *
     * @param payload           body of HTTP request
     * @return                  difference between two JSONs as HTTP response
     * @throws JSONException    if given JSON doesn't have "json1" or "json2" properties or either JSON is invalid
     */
    @RequestMapping(method = RequestMethod.POST, value = "compare", produces = "application/json")
    public ResponseEntity<String> compare(@RequestBody String payload){
        JsonNode jsonNode = service.getJsonNode(payload);
        if(jsonNode.size() != 2)
            throw new JSONException("Payload should contain two JSON properties: \"json1\", \"json2\"!", HttpStatus.BAD_REQUEST);
        JsonNode json1 = service.getJsonProperty(jsonNode, "json1");
        JsonNode json2 = service.getJsonProperty(jsonNode, "json2");
        return new ResponseEntity<>(service.compare(json1, json2), HttpStatus.OK);
    }

    /**
     * Returns JSON with removed properties specified in JSON array.
     * JSON has to have two properties "json" that contains JSON to extract from and
     * property "keys" that is a JSON array of strings.
     *
     * @param payload           body of HTTP request
     * @return                  JSON with no given properties
     * @throws JSONException    if given JSON payload doesn't have "json" and "keys" properties or either JSON is invalid
     */
    @RequestMapping(method = RequestMethod.POST, value = "extract", produces = "application/json")
    public ResponseEntity<String> extract(@RequestBody String payload){
        JsonNode jsonNode = service.getJsonNode(payload);
        if(jsonNode.size() != 2)
            throw new JSONException("Payload should contain two JSON properties: \"json\" and \"keys\"!", HttpStatus.BAD_REQUEST);
        JsonNode jsonData = service.getJsonProperty(jsonNode, "json");
        JsonNode jsonKeys = service.getJsonProperty(jsonNode, "keys");

        return new ResponseEntity<>(service.extract(jsonData, jsonKeys), HttpStatus.OK);
    }

    /**
     * Returns JSON with properties specified in JSON array.
     * JSON has to have two properties "json" that contains JSON to filter in and
     * property "keys" that is a JSON array of strings.
     *
     * @param payload           body of HTTP request
     * @return                  JSON with only given properties
     * @throws JSONException    if given JSON payload doesn't have "json" and "keys" properties or either JSON is invalid
     */
    @RequestMapping(method = RequestMethod.POST, value = "filter", produces = "application/json")
    public ResponseEntity<String> filter(@RequestBody String payload){
        JsonNode jsonNode = service.getJsonNode(payload);
        if(jsonNode.size() != 2)
            throw new JSONException("Payload should contain two JSON properties: \"json\" and \"keys\"!", HttpStatus.BAD_REQUEST);
        JsonNode jsonData = service.getJsonProperty(jsonNode, "json");
        JsonNode jsonKeys = service.getJsonProperty(jsonNode, "keys");

        return new ResponseEntity<>(service.filter(jsonData, jsonKeys), HttpStatus.OK);
    }
}


