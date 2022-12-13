package pl.put.poznan.json_tools.logic.decorator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

/**
 *  Responsible for removing JSON properties
 *
 * @author Przemysław Marcinkowski (ReynaX)
 * @version 1.0 13/12/2022
 */

public class JSONToolFilter extends JSONToolDecorator{
    public JSONToolFilter(IJSONTool tool){
        super(tool);
    }

    @Override
    public String generateOutput(JsonNode jsonNode) throws JsonProcessingException{
        String result = super.generateOutput(jsonNode);
        return filter(mapper.readTree(result));
    }

    /**
     * Extract two properties from given JSON: json and keys. JSON should contain data to filter and keys
     * should be an array of textual values that should not be removed from JSON data.
     * @param jsonNode      JSON that contains two JSON properties "json" and "keys"
     * @return              prettified and filtered JSON data
     */
    private String filter(JsonNode jsonNode){
        JsonNode jsonData = getJsonProperty(jsonNode, "json");
        JsonNode jsonKeys = getJsonProperty(jsonNode, "keys");

        HashSet<String> keys = getKeys(jsonKeys);
        ObjectNode objectNode = mapper.createObjectNode();
        filterProperties(jsonData, keys, objectNode);
        return objectNode.toPrettyString();
    }

    /**
     * Traverses through given JSON node and removes properties that are not present in keys set.
     * Method calls itself recursively when it encounters nested JSON whose name property is also
     * present in keys set.
     * @param rootNode      JSON node to traverse through
     * @param keys          set with keys to keep in JSON
     * @param result        JSON object with filtered properties
     */
    private void filterProperties(JsonNode rootNode, HashSet<String> keys, ObjectNode result){
        for(Iterator<Map.Entry<String, JsonNode>> it = rootNode.fields(); it.hasNext(); ){
            Map.Entry<String, JsonNode> node = it.next();
            if(!keys.contains(node.getKey()))
                continue;
            if(node.getValue().isContainerNode()){
                ObjectNode objectNode = mapper.createObjectNode();
                filterProperties(node.getValue(), keys, objectNode);
                result.set(node.getKey(), objectNode);
            }else result.set(node.getKey(), node.getValue());
        }
    }
}
