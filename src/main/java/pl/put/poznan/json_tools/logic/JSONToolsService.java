package pl.put.poznan.json_tools.logic;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pl.put.poznan.json_tools.exceptions.JSONException;
import pl.put.poznan.json_tools.logic.decorator.*;

@Service
public class JSONToolsService{

    public String minify(String payload){
        JsonNode payloadAsJson = JSONToolMinify.getJsonNode(payload);
        try{
            return new JSONToolMinify(new JSONTool()).generateOutput(payloadAsJson);
        }catch(Exception ex){
            throw new JSONException(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public String prettify(String payload){
        JsonNode payloadAsJson = JSONToolPrettify.getJsonNode(payload);
        try{
            return new JSONToolPrettify(new JSONTool()).generateOutput(payloadAsJson);
        }catch(Exception ex){
            throw new JSONException(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public String compare(String payload){
        JsonNode payloadAsJson = JSONToolCompare.getJsonNode(payload);
        if(payloadAsJson.size() != 2)
            throw new JSONException("Payload should contain two JSON properties: \"json1\", \"json2\"!", HttpStatus.BAD_REQUEST);

        try{
            return new JSONToolCompare(new JSONTool()).generateOutput(payloadAsJson);
        }catch(Exception ex){
            throw new JSONException(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public String filter(String payload){
        JsonNode payloadAsJson = JSONToolFilter.getJsonNode(payload);
        if(payloadAsJson.size() != 2)
            throw new JSONException("Payload should contain two JSON properties: \"json\" and \"keys\"!", HttpStatus.BAD_REQUEST);

        try{
            return new JSONToolFilter(new JSONTool()).generateOutput(payloadAsJson);
        }catch(Exception ex){
            throw new JSONException(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public String extract(String payload){
        JsonNode payloadAsJson = JSONToolExtract.getJsonNode(payload);
        if(payloadAsJson.size() != 2)
            throw new JSONException("Payload should contain two JSON properties: \"json\" and \"keys\"!", HttpStatus.BAD_REQUEST);

        try{
            return new JSONToolExtract(new JSONTool()).generateOutput(payloadAsJson);
        }catch(Exception ex){
            throw new JSONException(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
