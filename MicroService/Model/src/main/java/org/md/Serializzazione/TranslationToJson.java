package org.md.Serializzazione;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class TranslationToJson implements Translation {
    public String Translate(){

        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode jsonNode = objectMapper.valueToTree(this);

        //jsonNode.put("type", this.getClass().getSimpleName());

        return jsonNode.toString();
    }
}
