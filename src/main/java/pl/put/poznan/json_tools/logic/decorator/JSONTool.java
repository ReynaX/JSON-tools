package pl.put.poznan.json_tools.logic.decorator;

import com.fasterxml.jackson.databind.JsonNode;

public class JSONTool implements IJSONTool{
    @Override
    public String generateOutput(JsonNode jsonNode){
        if(jsonNode == null)
            return "{}";
        return jsonNode.toPrettyString();
    }
}
