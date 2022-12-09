package pl.put.poznan.json_tools.rest;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.json_tools.logic.JSONToolsService;

@RestController
@RequestMapping(path="/json-tools")
public class JSONToolsController{
    private static final Logger logger = LoggerFactory.getLogger(JSONToolsController.class);
    private static JSONToolsService m_service;

    @Autowired
    public JSONToolsController(JSONToolsService service){
        m_service = service;
    }

    @RequestMapping(method = RequestMethod.POST, value = "minify")
    public ResponseEntity<String> minify(@RequestBody String payload){
        return new ResponseEntity<>("TODO: minify", HttpStatus.ACCEPTED);
    }

    @RequestMapping(method = RequestMethod.POST, value = "minify")
    public ResponseEntity<String> unminify(@RequestBody String payload){
        return new ResponseEntity<>("TODO: unminify", HttpStatus.ACCEPTED);
    }

    @RequestMapping(method = RequestMethod.POST, value = "compare")
    public ResponseEntity<String> compare(@RequestBody String payload){
        return new ResponseEntity<>("TODO: compare", HttpStatus.ACCEPTED);
    }

    @RequestMapping(method = RequestMethod.POST, value = "extract")
    public ResponseEntity<String> extract(@RequestBody String payload){
        return new ResponseEntity<>("TODO: filter", HttpStatus.ACCEPTED);
    }

    @RequestMapping(method = RequestMethod.POST, value = "filter")
    public ResponseEntity<String> filter(@RequestBody String payload){
        return new ResponseEntity<>("TODO: filter", HttpStatus.ACCEPTED);
    }
}


