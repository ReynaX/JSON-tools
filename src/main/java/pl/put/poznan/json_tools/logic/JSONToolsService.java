package pl.put.poznan.json_tools.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class JSONToolsService{
    private static final ObjectMapper m_mapper = new ObjectMapper();

    public JsonNode getJsonNode(String json){
        try{
            return m_mapper.readTree(json);
        }catch(JsonProcessingException e){
            e.printStackTrace();
        }
        return null;
    }


}
