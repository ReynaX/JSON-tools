package pl.put.poznan.json_tools.logic.decorator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

public class JSONToolFilter extends JSONToolDecorator{
    public JSONToolFilter(IJSONTool tool){
        super(tool);
    }

    @Override
    public String generateOutput(JsonNode jsonNode) throws JsonProcessingException{
        String result = super.generateOutput(jsonNode);
        return filter(mapper.readTree(result));
    }

    private String filter(JsonNode jsonNode){
        JsonNode jsonData = getJsonProperty(jsonNode, "json");
        JsonNode jsonKeys = getJsonProperty(jsonNode, "keys");

        HashSet<String> keys = getKeys(jsonKeys);
        ObjectNode objectNode = mapper.createObjectNode();
        filterProperties(jsonData, keys, objectNode);
        return objectNode.toPrettyString();
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
            }else result.set(node.getKey(), node.getValue());
        }
    }
}
