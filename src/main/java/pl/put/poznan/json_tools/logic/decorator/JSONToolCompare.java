package pl.put.poznan.json_tools.logic.decorator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.flipkart.zjsonpatch.JsonDiff;

public class JSONToolCompare extends JSONToolDecorator{
    public JSONToolCompare(IJSONTool tool){
        super(tool);
    }

    @Override
    public String generateOutput(JsonNode jsonNode) throws JsonProcessingException{
        String result = super.generateOutput(jsonNode);
        JsonNode temp = mapper.readTree(result);
        JsonNode json1 = getJsonProperty(temp, "json1");
        JsonNode json2 = getJsonProperty(temp, "json2");
        return JsonDiff.asJson(json1, json2).toPrettyString();
    }
}
