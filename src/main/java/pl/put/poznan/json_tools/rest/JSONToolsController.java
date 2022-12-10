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

@RestController
@RequestMapping(path = "/json-tools")
public class JSONToolsController{
    private static final Logger logger = LoggerFactory.getLogger(JSONToolsController.class);
    private static JSONToolsService service;

    @Autowired
    public JSONToolsController(JSONToolsService service){
        JSONToolsController.service = service;
    }

    @RequestMapping(method = RequestMethod.POST, value = "minify", produces = "application/json")
    public ResponseEntity<String> minify(@RequestBody String payload){
        JsonNode jsonNode = service.getJsonNode(payload);
        if(jsonNode.size() != 1)
            throw new JSONException("Payload should contain only one JSON property: \"json\"!", HttpStatus.BAD_REQUEST);
        JsonNode jsonData = service.getJsonProperty(jsonNode, "data");
        return new ResponseEntity<>(service.minify(jsonData), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "pretty", produces = "application/json")
    public ResponseEntity<String> pretty(@RequestBody String payload){
        JsonNode jsonNode = service.getJsonNode(payload);
        if(jsonNode.size() != 1)
            throw new JSONException("Payload should contain only one JSON property: \"json\"!", HttpStatus.BAD_REQUEST);
        JsonNode jsonData = service.getJsonProperty(jsonNode, "data");
        return new ResponseEntity<>(service.pretty(jsonData), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "compare")
    public ResponseEntity<String> compare(@RequestBody String payload){
        JsonNode jsonNode = service.getJsonNode(payload);
        if(jsonNode.size() != 2)
            throw new JSONException("Payload should contain two JSON properties: \"json1\", \"json2\"!", HttpStatus.BAD_REQUEST);
        JsonNode json1 = service.getJsonProperty(jsonNode, "json1");
        JsonNode json2 = service.getJsonProperty(jsonNode, "json2");
        return new ResponseEntity<>(service.compare(json1, json2), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "extract")
    public ResponseEntity<String> extract(@RequestBody String payload){
        JsonNode jsonNode = service.getJsonNode(payload);
        if(jsonNode.size() != 2)
            throw new JSONException("Payload should contain two JSON properties: \"json\" and \"keys\"!", HttpStatus.BAD_REQUEST);
        JsonNode jsonData = service.getJsonProperty(jsonNode, "json");
        JsonNode jsonKeys = service.getJsonProperty(jsonNode, "keys");

        return new ResponseEntity<>("TODO", HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "filter")
    public ResponseEntity<String> filter(@RequestBody String payload){
        JsonNode jsonNode = service.getJsonNode(payload);
        if(jsonNode.size() != 2)
            throw new JSONException("Payload should contain two JSON properties: \"json\" and \"keys\"!", HttpStatus.BAD_REQUEST);
        JsonNode jsonData = service.getJsonProperty(jsonNode, "json");
        JsonNode jsonKeys = service.getJsonProperty(jsonNode, "keys");

        return new ResponseEntity<>("TODO", HttpStatus.OK);
    }
}


