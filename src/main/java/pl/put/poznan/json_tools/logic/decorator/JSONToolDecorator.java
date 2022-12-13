package pl.put.poznan.json_tools.logic.decorator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import pl.put.poznan.json_tools.exceptions.JSONException;

import java.util.HashSet;

public class JSONToolDecorator implements IJSONTool{
    protected static ObjectMapper mapper = new ObjectMapper();
    protected IJSONTool tool;

    public JSONToolDecorator(IJSONTool tool){
        this.tool = tool;
    }

    protected static HashSet<String> getKeys(JsonNode jsonKeys){
        HashSet<String> res = new HashSet<>();
        if(jsonKeys.isArray()){
            for(final JsonNode node : jsonKeys){
                res.add(node.asText());
            }
        }
        return res;
    }

    public static JsonNode getJsonNode(String json){
        try{
            JsonNode jsonNode = mapper.readTree(json);
            if(jsonNode.isNull())
                throw new JSONException("Payload is not a valid JSON!", HttpStatus.BAD_REQUEST);
            return jsonNode;
        }catch(Exception ex){
            throw new JSONException("Payload is not a valid JSON!", HttpStatus.BAD_REQUEST);
        }
    }

    public static JsonNode getJsonProperty(JsonNode node, String property){
        try{
            JsonNode jsonNode = node.get(property);
            if(jsonNode.isNull())
                throw new JSONException("Missing JSON property: \"" + property + "\"!", HttpStatus.BAD_REQUEST);
            return jsonNode;
        }catch(Exception ex){
            throw new JSONException("Missing JSON property: \"" + property + "\"!", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public String generateOutput(JsonNode jsonNode) throws JsonProcessingException{
        return tool.generateOutput(jsonNode);
    }

}
