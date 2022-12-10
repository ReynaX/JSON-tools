package pl.put.poznan.json_tools.logic;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.flipkart.zjsonpatch.JsonDiff;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pl.put.poznan.json_tools.exceptions.JSONException;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

@Service
public class JSONToolsService{
    private static final ObjectMapper mapper = new ObjectMapper();

    public JsonNode getJsonNode(String json){
        try{
            JsonNode jsonNode = mapper.readTree(json);
            if(jsonNode.isNull())
                throw new JSONException("Payload is not a valid JSON!", HttpStatus.BAD_REQUEST);
            return jsonNode;
        }catch(Exception ex){
            throw new JSONException("Payload is not a valid JSON!", HttpStatus.BAD_REQUEST);
        }
    }

    public JsonNode getJsonProperty(JsonNode node, String property){
        try{
            JsonNode jsonNode = node.get(property);
            if(jsonNode.isNull())
                throw new JSONException("Missing JSON property: \"" + property + "\"!", HttpStatus.BAD_REQUEST);
            return jsonNode;
        }catch(Exception ex){
            throw new JSONException("Missing JSON property: \"" + property + "\"!", HttpStatus.BAD_REQUEST);
        }
    }

    public String minify(JsonNode node){
        try{
            String compressed = node.toString();
            if(compressed == null)
                throw new JSONException("Invalid JSON!", HttpStatus.BAD_REQUEST);
            return compressed;
        }catch(Exception ex){
            throw new JSONException("Invalid JSON!", HttpStatus.BAD_REQUEST);
        }
    }

    public String prettify(JsonNode node){
        try{
            String pretty = node.toPrettyString();
            if(pretty == null)
                throw new JSONException("Invalid JSON!", HttpStatus.BAD_REQUEST);
            return pretty;
        }catch(Exception ex){
            throw new JSONException("Invalid JSON!", HttpStatus.BAD_REQUEST);
        }
    }

    public String compare(JsonNode json1, JsonNode json2){
        JsonNode patch = JsonDiff.asJson(json1, json2);
        return patch.toPrettyString();
    }

    public String filter(JsonNode jsonData, JsonNode jsonKeys){
        HashSet<String> keys = getKeys(jsonKeys);
        ObjectNode objectNode = mapper.createObjectNode();
        filterProperties(jsonData, keys, objectNode);
        return objectNode.toPrettyString();
    }

    public String extract(JsonNode jsonData, JsonNode jsonKeys){
        HashSet<String> keys = getKeys(jsonKeys);
        ObjectNode objectNode = mapper.createObjectNode();
        extractProperties(jsonData, keys, objectNode);

        return objectNode.toPrettyString();
    }

    private HashSet<String> getKeys(JsonNode jsonKeys){
        HashSet<String> res = new HashSet<>();
        if(jsonKeys.isArray()){
            for(final JsonNode node : jsonKeys){
                res.add(node.asText());
            }
        }
        return res;
    }

    private void extractProperties(JsonNode rootNode, HashSet<String> keys, ObjectNode result){
        for(Iterator<Map.Entry<String, JsonNode>> it = rootNode.fields(); it.hasNext(); ){
            Map.Entry<String, JsonNode> node = it.next();
            if(keys.contains(node.getKey()))
                continue;
            if(node.getValue().isContainerNode()){
                ObjectNode objectNode = mapper.createObjectNode();
                extractProperties(node.getValue(), keys, objectNode);
                result.set(node.getKey(), objectNode);
            }else result.set(node.getKey(), node.getValue());
        }
    }

    private void filterProperties(JsonNode rootNode, HashSet<String> keys, ObjectNode result){
        for(Iterator<Map.Entry<String, JsonNode>> it = rootNode.fields(); it.hasNext(); ){
            Map.Entry<String, JsonNode> node = it.next();
            if(!keys.contains(node.getKey()))
                continue;
            if(node.getValue().isContainerNode()){
                ObjectNode objectNode = mapper.createObjectNode();
                filterProperties(node.getValue(), keys, objectNode);
                result.set(node.getKey(), objectNode);
            }
            else result.set(node.getKey(), node.getValue());
        }
    }
}
