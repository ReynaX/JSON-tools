package pl.put.poznan.json_tools.logic.decorator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

public class JSONToolPrettify extends JSONToolDecorator{
    public JSONToolPrettify(IJSONTool tool){
        super(tool);
    }

    @Override
    public String generateOutput(JsonNode jsonNode) throws JsonProcessingException{
        String result = super.generateOutput(jsonNode);
        return mapper.readTree(result).toPrettyString();
    }
}
