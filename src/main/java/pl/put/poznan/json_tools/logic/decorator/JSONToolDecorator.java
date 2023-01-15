package pl.put.poznan.json_tools.logic.decorator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import pl.put.poznan.json_tools.exceptions.JSONException;

import java.util.HashSet;

/**
 * Decorator implementation that to wrap objects and allow adding new behaviors to objects by placing them inside.
 *
 * @author Przemysław Marcinkowski (ReynaX)
 * @version 1.0, 13/12/2022
 */
public class JSONToolDecorator implements IJSONTool{
    protected static ObjectMapper mapper = new ObjectMapper();
    protected IJSONTool tool;

    public JSONToolDecorator(IJSONTool tool){
        this.tool = tool;
    }

    /**
     * Collects textual values from an array in given JSON and collects them in hash set.
     *
     * @param jsonKeys  array of string values in JSON format
     * @return          set of string values if given JSON is an array, otherwise empty set
     */
    protected static HashSet<String> getKeys(JsonNode jsonKeys){
        HashSet<String> res = new HashSet<>();
        if(jsonKeys.isArray()){
            for(final JsonNode node : jsonKeys){
                res.add(node.asText());
            }
        }
        return res;
    }

    /**
     * Create JSON from given string.
     * @param json      string to create JSON from
     * @return          created JSON node from given string
     * @throws JSONException if given string is not valid JSON
     */
    public static JsonNode getJsonNode(String json){
        try{
            JsonNode jsonNode = mapper.readTree(json);
            if(jsonNode == null)
                throw new JSONException("Payload is not a valid JSON!", HttpStatus.BAD_REQUEST);
            return jsonNode;
        }catch(JsonProcessingException ex){
            throw new JSONException("Payload is not a valid JSON!", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Get property from given JSON node.
     * @param node      JSON to extract property from
     * @param property  property name to extract
     * @return          value of property as JSON node
     * @throws JSONException if given node doesn't have given property
     */
    public static JsonNode getJsonProperty(JsonNode node, String property){
        JsonNode jsonNode = node.get(property);
        if(jsonNode == null)
            throw new JSONException("Missing JSON property: \"" + property + "\"!", HttpStatus.BAD_REQUEST);
        return jsonNode;
    }

    @Override
    public String generateOutput(JsonNode jsonNode) throws JsonProcessingException{
        return tool.generateOutput(jsonNode);
    }
}
