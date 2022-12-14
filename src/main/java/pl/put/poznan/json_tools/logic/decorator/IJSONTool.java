package pl.put.poznan.json_tools.logic.decorator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

public interface IJSONTool{
    String generateOutput(JsonNode node) throws JsonProcessingException;
}
