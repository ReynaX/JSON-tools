package pl.put.poznan.json_tools.logic;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pl.put.poznan.json_tools.exceptions.JSONException;
import pl.put.poznan.json_tools.logic.decorator.*;


/**
 *  Responsible for managing payloads given through API
 *
 * @author Przemysław Marcinkowski (ReynaX)
 * @version 1.0 21/01/2023
 */

@Service
public class JSONToolsService{

    /**
     *  Constructs a new <code>JsonNode</code> from given payload.
     *  Creates a minified version of JSON if converting payload to <code>JsonNode</code> was successful.
     *
     * @param payload           body of HTTP request that should contain JSON to minify
     * @return                  result minified version of JSON
     * @throws JSONException    if converting paylaod to <code>JsonNode</code> was unsuccessfull or JSON cannot be minified
     */
    public String minify(String payload){
        JsonNode payloadAsJson = JSONToolMinify.getJsonNode(payload);
        try{
            return new JSONToolMinify(new JSONTool()).generateOutput(payloadAsJson);
        }catch(Exception ex){
            throw new JSONException(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     *  Constructs a new <code>JsonNode</code> from given payload.
     *  Creates a prettified version of JSON if converting payload to <code>JsonNode</code> was successful.
     *
     * @param payload           body of HTTP request that should contain JSON to prettify
     * @return                  result prettified version of JSON
     * @throws JSONException    if converting paylaod to <code>JsonNode</code> was unsuccessfull or JSON cannot be prettified
     */
    public String prettify(String payload){
        JsonNode payloadAsJson = JSONToolPrettify.getJsonNode(payload);
        try{
            return new JSONToolPrettify(new JSONTool()).generateOutput(payloadAsJson);
        }catch(Exception ex){
            throw new JSONException(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Constructs a new <code>JsonNode</code> from given payload.
     * Returns difference between two JSONs in <a href ="https://jsonpatch.com">JSON Patch</a> format
     * if converting payload to <code>JsonNode</code> was successful.
     *
     * @param payload           body of HTTP request that should contain JSONs to compare
     * @return                  result of comparision in JSON Patch format
     * @throws JSONException    if converting paylaod to <code>JsonNode</code> was unsuccessfull or JSON doesn't have 2 properties "json1" and "json2".
     */
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

    /**
     * Constructs a new <code>JsonNode</code> from given payload.
     * Returns new JSON with only properties specified in "keys" property.
     *
     * @param payload           body of HTTP request that should contain JSON to filter
     * @return                  JSON with only parameters specified in "keys" property
     * @throws JSONException    if converting paylaod to <code>JsonNode</code> was unsuccessfull or JSON doesn't have 2 properties "json" and "keys".
     */
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

    /**
     * Constructs a new <code>JsonNode</code> from given payload.
     * Returns new JSON with no properties specified in "keys" property.
     *
     * @param payload           body of HTTP request that should contain JSON to filter
     * @return                  JSON with no parameters specified in "keys" property
     * @throws JSONException    if converting paylaod to <code>JsonNode</code> was unsuccessfull or JSON doesn't have 2 properties "json" and "keys".
     */
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
