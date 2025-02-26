package org.md.Estate.Mode;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "name")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Affitto.class, name = "Affitto"),
        @JsonSubTypes.Type(value = Vendita.class, name = "Vendita")
})

public interface Mode {

    String getName();

}
