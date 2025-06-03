package org.md.Estate.ClasseEnergetica;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "nome")
@JsonSubTypes({
        @JsonSubTypes.Type(value = A.class, name = "A"),
        @JsonSubTypes.Type(value = A2.class, name = "A2"),
        @JsonSubTypes.Type(value = A3.class, name = "A3"),
        @JsonSubTypes.Type(value = A4.class, name = "A4"),
        @JsonSubTypes.Type(value = B.class, name = "B"),
        @JsonSubTypes.Type(value = C.class, name = "C"),
        @JsonSubTypes.Type(value = D.class, name = "D"),
        @JsonSubTypes.Type(value = E.class, name = "E"),
        @JsonSubTypes.Type(value = F.class, name = "F"),
})
@JsonIgnoreProperties(ignoreUnknown = true)
public interface EnergeticClass {

    String getNome();

}

