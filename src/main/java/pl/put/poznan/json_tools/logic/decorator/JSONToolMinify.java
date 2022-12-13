package pl.put.poznan.json_tools.logic.decorator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

public class JSONToolMinify extends JSONToolDecorator{
    public JSONToolMinify(JSONTool jsonTool){
        super(jsonTool);
    }

    @Override
    public String generateOutput(JsonNode jsonNode) throws JsonProcessingException{
        String result = super.generateOutput(jsonNode);
        return mapper.readTree(result).toString();
    }
}
