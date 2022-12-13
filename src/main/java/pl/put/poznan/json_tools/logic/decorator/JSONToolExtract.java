package pl.put.poznan.json_tools.logic.decorator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

public class JSONToolExtract extends JSONToolDecorator{
    public JSONToolExtract(IJSONTool tool){
        super(tool);
    }

    @Override
    public String generateOutput(JsonNode jsonNode) throws JsonProcessingException{
        String result = super.generateOutput(jsonNode);
        return extract(mapper.readTree(result));
    }

    private String extract(JsonNode jsonNode){
        JsonNode jsonData = getJsonProperty(jsonNode, "json");
        JsonNode jsonKeys = getJsonProperty(jsonNode, "keys");

        HashSet<String> keys = getKeys(jsonKeys);
        ObjectNode objectNode = mapper.createObjectNode();
        extractProperties(jsonData, keys, objectNode);
        return objectNode.toPrettyString();
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
}
