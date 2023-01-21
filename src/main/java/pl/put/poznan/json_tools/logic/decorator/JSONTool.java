package pl.put.poznan.json_tools.logic.decorator;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Basic implementation of <code>IJSONTool</code> interface used in decorator design pattern.
 *
 * @author Przemysław Marcinkowski (ReynaX)
 * @version 1.0, 21/01/2023
 */
public class JSONTool implements IJSONTool{

    /**
     * Generates prettified JSON if given<code>JsonNode</code> is not null.
     * Otherwise it creates a basic JSON.
     * @param jsonNode          json to convert to prettified string
     * @return                  prettified JSON if given <code>JsonNode</code> is valid, empty JSON otherwise.
     */
    @Override
    public String generateOutput(JsonNode jsonNode){
        if(jsonNode == null)
            return "{}";
        return jsonNode.toPrettyString();
    }
}
